document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("profile-form");
    const photoInput = document.getElementById("photoInput");
    const photoPreview = document.getElementById("mainPhotoUrl");
    const btn = document.getElementById("saveProfileBtn");
    let photoData = null;

    form.addEventListener("submit", async (e) => {
        const telegram = document.getElementById("telegramUsername").value;
        const instagram = document.getElementById("instagramUsername").value;
        const formStatus = document.getElementById("formStatus");

        e.preventDefault();

        btn.disabled = true;

        clearErrors();

        const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;

        if (!csrfToken || !csrfHeader) {
            console.error("CSRF were not found");
            btn.disabled = false;
            return;
        }

        if(telegram.trim() === "" && instagram.trim() === "") {
            alert("Fill telegram or instagram field")
            btn.disabled = false;
            return;
        }

        if (photoPreview.src.includes("avatar.jpg")) {
            alert("Select a photo")
            btn.disabled = false;
            return;
        }4

        try {
            if (photoInput.files.length > 0) {
                const photoFormData = new FormData();
                photoFormData.append("file", photoInput.files[0]);

                const photoResponse = await fetch("/api/edit/photo", {
                    method: "POST",
                    headers: {
                        [csrfHeader]: csrfToken
                    },
                    body: photoFormData
                });

                if (!photoResponse.ok) {
                    const error = await photoResponse.text();
                    throw new Error(`Error of downloading photo: ${error}`);
                }

                photoData = await photoResponse.json();
                console.log("Photo is loaded:", photoData);
            }

            const profileData = {
                firstName: document.getElementById("firstName")?.value || "",
                lastName: document.getElementById("lastName")?.value || "",
                birthDate: document.getElementById("birthDate")?.value || null,
                gender: document.getElementById("gender")?.value || "",
                city: document.getElementById("city")?.value || "",
                aboutMe: document.getElementById("aboutMe")?.value || "",
                mainPhotoUrl: photoData?.url || photoPreview.src,
                telegramUsername: document.getElementById("telegramUsername")?.value || "",
                instagramUsername: document.getElementById("instagramUsername")?.value || "",
                formActivation: document.getElementById("formStatusCheck").checked
            };

            const response = await fetch("/api/edit", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    [csrfHeader]: csrfToken,
                    "Accept-Language": navigator.language
                },
                body: JSON.stringify(profileData)
            });

            if (!response.ok) {
                const data = await response.json();

                if (data.errors) {
                    for (const field in data.errors) {
                        showError(field, data.errors[field]);
                    }
                } else {
                    throw new Error(`Server error: ${response.status}`);
                }
                return;
            }

            alert("Profile saved successfully");

            if (photoData?.url) {
                photoPreview.src = photoData.url + "?t=" + Date.now();
            }

        } catch (error) {
            console.error("Error:", error);
            alert("Unable to save a profile: " + error.message); //TODO: delete error.message before the end
        } finally {
            btn.disabled = false;
        }
    });

    photoInput.addEventListener("change", () => {
        const file = photoInput.files[0];
        if (!file) return;

        if (file.size > 5 * 1024 * 1024) {
            alert("File is too large");
            photoInput.value = "";
            return;
        }
        if (!file.type.startsWith("image/")) {
            alert("Please, choose an image");
            photoInput.value = "";
            return;
        }

        const reader = new FileReader();
        reader.onload = e => photoPreview.src = e.target.result;
        reader.readAsDataURL(file);
    });
    function showError(field, message) {
        const errorDiv = document.getElementById(`error-${field}`);
        if (errorDiv) {
            errorDiv.textContent = message;
            errorDiv.style.display = "block";
        }
    }

    function clearErrors() {
        document.querySelectorAll(".error").forEach(div => {
            div.textContent = "";
            div.style.display = "none";
        });
    }
});
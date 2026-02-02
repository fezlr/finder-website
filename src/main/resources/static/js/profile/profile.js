document.addEventListener("DOMContentLoaded", () => {

    const form = document.querySelector(".profile-form");
    const photoInput = document.getElementById("photoInput");
    const photoPreview = document.getElementById("photoPreview");

    photoInput.addEventListener("change", () => {
        const file = photoInput.files[0];
        if (!file) return;

        const reader = new FileReader();
        reader.onload = e => photoPreview.src = e.target.result;
        reader.readAsDataURL(file);
    });

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        clearErrors();

        const response = await fetch("/api/profile", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "X-CSRF-TOKEN": document.getElementById("csrfToken").value,
                "Accept-Language": navigator.language
            },
            body: JSON.stringify({
                firstName: document.getElementById("firstName").value,
                lastName: document.getElementById("lastName").value,
                birthDate: document.getElementById("birthDate").value,
                gender: document.getElementById("gender").value,
                city: document.getElementById("city").value,
                aboutMe: document.getElementById("aboutMe").value
            })
        });

        if (!response.ok) {
            const data = await response.json();

            for (const field in data.errors) {
                showError(field,data.errors[field]);
            }
            return;
        }

        if (photoInput.files.length > 0) {
            const formData = new FormData();
            formData.append("file", photoInput.files[0]);

            const photoResponse = await fetch("/api/profile/photo", {
                method: "POST",
                headers: {
                    "X-CSRF-TOKEN": document.getElementById("csrfToken").value
                },
                body: formData
            });

            if (!photoResponse.ok) {
                alert("Photo upload failed");
                return;
            }
        }

        alert("Profile successfully saved");
    });
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
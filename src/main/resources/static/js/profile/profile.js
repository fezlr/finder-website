async function profile() {
    const response = await fetch("api/profile", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": document.getElementById("csrfToken").value
        },
        body: JSON.stringify({
            firstName: document.getElementById("firstName").value,
            lastName: document.getElementById("lastName").value,
            birthDate: document.getElementById("birthDate").value,
            gender: document.getElementById("gender").value,
            city: document.getElementById("city").value,
            aboutMe: document.getElementById("aboutMe").value,
            photoInput: document.getElementById("photoInput").value
        })
    });
    if (response.ok) {
        alert("Your profile successfully saved");
    } else {
        const error = await response.text();
        alert(error);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".profile-form");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        await profile();
    });
});

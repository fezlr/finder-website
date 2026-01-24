async function register() {
    const response = await fetch("api/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": document.getElementById("csrfToken").value
        },
        body: JSON.stringify({
            firstName: document.getElementById("firstName").value,
            lastName: document.getElementById("lastName").value,
            username: document.getElementById("username").value,
            email: document.getElementById("email").value,
            password: document.getElementById("password").value
        })
    });

    if (response.ok) {
        alert("Check your email to confirm registration");
    } else {
        const error = await response.text();
        alert(error);
    }
}
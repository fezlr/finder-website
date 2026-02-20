async function save() {
    const response = await fetch("api/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": document.getElementById("csrfToken").value
        },
        body: JSON.stringify({
            username: document.getElementById("username").value,
            email: document.getElementById("email").value,
            password: document.getElementById("password").value
        })
    });

    if (!response.ok) {
    const error = await response.text();
            alert(error);
    }
}
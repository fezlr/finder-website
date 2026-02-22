`document.getElementById("like_btn").addEventListener("click", () => sendReaction("LIKE"));
document.getElementById("dislike_btn").addEventListener("click", () => sendReaction("DISLIKE"));

async function sendReaction(reaction) {
    const response = await fetch("api/form/react", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": document.getElementById("csrfToken").value
        },
        body: JSON.stringify({
            reaction: reaction
        })
    });

    if (!response.ok) {
    const error = await response.text();
            alert(error);
    }
}`
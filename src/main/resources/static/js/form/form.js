// ─── Reaction API ────────────────────────────────────────────────────────────

async function sendReaction(reaction) {
    const profileContainer = document.getElementById("form-container");
    const viewedProfileId = profileContainer.dataset.profileId;

    const response = await fetch("/user/api/form/react", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": document.getElementById("csrfToken").value
        },
        body: JSON.stringify({
            reaction: reaction,
            viewedProfileId: viewedProfileId
        })
    });

    if (!response.ok) {
        const error = await response.text();
        alert(error);
    }
    window.location.reload();
}

// ─── Button clicks ───────────────────────────────────────────────────────────

document.getElementById("like_btn").addEventListener("click", () => flyCard("LIKE"));
document.getElementById("dislike_btn").addEventListener("click", () => flyCard("DISLIKE"));

function flyCard(reaction) {
    const card = document.getElementById("swipe-card");
    card.classList.add(reaction === "LIKE" ? "fly-right" : "fly-left");
    card.addEventListener("animationend", () => sendReaction(reaction), { once: true });
}

// ─── Swipe / drag ────────────────────────────────────────────────────────────

(function initSwipe() {
    const card = document.getElementById("swipe-card");

    const SWIPE_THRESHOLD = 90;   // px to trigger a swipe
    const TILT_FACTOR     = 0.08; // rotation degrees per px of drag

    let startX = 0, startY = 0;
    let currentX = 0, currentY = 0;
    let isDragging = false;

    function getClientCoords(e) {
        if (e.touches && e.touches.length) {
            return { x: e.touches[0].clientX, y: e.touches[0].clientY };
        }
        return { x: e.clientX, y: e.clientY };
    }

    function onStart(e) {
        // Ignore button clicks bubbling up
        if (e.target.closest(".btn")) return;

        isDragging = true;
        const { x, y } = getClientCoords(e);
        startX = x;
        startY = y;
        currentX = 0;
        currentY = 0;
        card.classList.add("is-dragging");
    }

    function onMove(e) {
        if (!isDragging) return;
        e.preventDefault();

        const { x, y } = getClientCoords(e);
        currentX = x - startX;
        currentY = y - startY;

        const rotate = currentX * TILT_FACTOR;
        card.style.transform = `translate(${currentX}px, ${currentY}px) rotate(${rotate}deg)`;

        // Show stamps
        const absX = Math.abs(currentX);
        if (absX > 40) {
            card.classList.toggle("hint-like",    currentX > 0);
            card.classList.toggle("hint-dislike", currentX < 0);
            // Fade stamps proportionally
            const opacity = Math.min((absX - 40) / 60, 1);
            card.querySelector(".like-stamp").style.opacity    = currentX > 0 ? opacity : 0;
            card.querySelector(".dislike-stamp").style.opacity = currentX < 0 ? opacity : 0;
        } else {
            card.classList.remove("hint-like", "hint-dislike");
            card.querySelector(".like-stamp").style.opacity    = 0;
            card.querySelector(".dislike-stamp").style.opacity = 0;
        }
    }

    function onEnd() {
        if (!isDragging) return;
        isDragging = false;
        card.classList.remove("is-dragging", "hint-like", "hint-dislike");

        if (currentX > SWIPE_THRESHOLD) {
            flyCard("LIKE");
        } else if (currentX < -SWIPE_THRESHOLD) {
            flyCard("DISLIKE");
        } else {
            // Snap back with spring
            card.style.transition = "transform 0.45s cubic-bezier(0.34, 1.56, 0.64, 1)";
            card.style.transform  = "translate(0, 0) rotate(0deg)";
            card.querySelector(".like-stamp").style.opacity    = 0;
            card.querySelector(".dislike-stamp").style.opacity = 0;
            setTimeout(() => { card.style.transition = ""; }, 460);
        }
    }

    // Mouse
    card.addEventListener("mousedown",  onStart);
    window.addEventListener("mousemove", onMove);
    window.addEventListener("mouseup",   onEnd);

    // Touch
    card.addEventListener("touchstart", onStart, { passive: true });
    card.addEventListener("touchmove",  onMove,  { passive: false });
    card.addEventListener("touchend",   onEnd);
})();
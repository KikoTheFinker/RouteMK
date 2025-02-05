
document.addEventListener("DOMContentLoaded", function () {
    const viewStopsButtons = document.querySelectorAll(".view-stops-btn");
    const stopsModal = document.getElementById("stopsModal");
    const closeStopsModal = document.getElementById("closeStopsModal");
    const stopsFlow = document.getElementById("stopsFlow");

    viewStopsButtons.forEach(button => {
        button.addEventListener("click", function () {
            const tripId = this.getAttribute("data-trip-id");
            const stopElements = this.closest("tr").querySelectorAll(".hidden-stops .stop-location, .hidden-stops .stop-time");

            stopsFlow.innerHTML = "";

            for (let i = 0; i < stopElements.length; i += 2) {
                const location = stopElements[i]?.innerText || "Unknown City";
                const time = stopElements[i + 1]?.innerText || "Unknown Time";

                const stopWrapper = document.createElement("div");
                stopWrapper.className = "stop-wrapper";

                const stopBox = document.createElement("div");
                stopBox.className = "stop-box";
                stopBox.textContent = `${location}: ${time}`;

                stopWrapper.appendChild(stopBox);

                const isLastInRow = (i / 2 + 1) % 4 === 0;
                const isLastStop = i + 2 >= stopElements.length;

                if (!isLastInRow && !isLastStop) {
                    const arrow = document.createElement("div");
                    arrow.className = "arrow-down";
                    arrow.textContent = "↓";
                    stopWrapper.appendChild(arrow);
                }

                stopsFlow.appendChild(stopWrapper);
            }

            stopsModal.classList.remove("hidden");
        });
    });

    closeStopsModal.addEventListener("click", () => stopsModal.classList.add("hidden"));

    window.addEventListener("click", function (event) {
        if (event.target === stopsModal) {
            stopsModal.classList.add("hidden");
        }
    });
});

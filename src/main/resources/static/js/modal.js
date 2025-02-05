document.addEventListener("DOMContentLoaded", function () {
    const allLocations = window.modalData.allLocations;
    const routeSourceId = window.modalData.routeSourceId;
    const routeDestinationId = window.modalData.routeDestinationId;
    const routeId = window.modalData.routeId;

    const dynamicLocations = allLocations.filter(loc => loc.id !== routeSourceId && loc.id !== routeDestinationId);
    let selectedLocationIds = [];

    // Helper Functions
    function updateSelectOptions(selectElement) {
        const currentVal = selectElement.value;
        selectElement.innerHTML = `<option value="">Select location</option>`;

        dynamicLocations.forEach(loc => {
            if (selectedLocationIds.indexOf(String(loc.id)) === -1 || currentVal == loc.id) {
                const option = document.createElement("option");
                option.value = loc.id;
                option.textContent = loc.name;
                selectElement.appendChild(option);
            }
        });

        selectElement.value = currentVal;
    }

    function updateAllSelects(containerId, addButtonId) {
        const selects = document.querySelectorAll(`#${containerId} .location-entry.dynamic select`);
        selectedLocationIds = Array.from(selects)
            .filter(sel => sel.value !== "")
            .map(sel => sel.value);

        selects.forEach(updateSelectOptions);

        const addButton = document.getElementById(addButtonId);
        addButton.disabled = selects.length >= dynamicLocations.length;
    }

    function createLocationEntry(selectedId = "", eta = "", isEdit = false) {
        const div = document.createElement("div");
        div.classList.add("location-entry", "dynamic");

        const select = document.createElement("select");
        select.name = "locations[]";
        select.classList.add("form-control");
        updateSelectOptions(select);
        select.value = selectedId;

        const timeInput = document.createElement("input");
        timeInput.type = "time";
        timeInput.name = "etas[]";
        timeInput.classList.add("form-control");
        timeInput.required = true;
        timeInput.value = eta;

        const removeBtn = document.createElement("button");
        removeBtn.type = "button";
        removeBtn.classList.add("remove-location", "btn-redoutline");
        removeBtn.textContent = "Remove";
        removeBtn.addEventListener("click", () => {
            div.remove();
            updateAllSelects(isEdit ? "editAdditionalLocationsSection" : "additionalLocationsSection", isEdit ? "editAddLocationButton" : "addLocationButton");
        });

        select.addEventListener("change", () => {
            updateAllSelects(isEdit ? "editAdditionalLocationsSection" : "additionalLocationsSection", isEdit ? "editAddLocationButton" : "addLocationButton");
        });

        div.append(select, timeInput, removeBtn);
        return div;
    }

    document.getElementById("addLocationButton").addEventListener("click", () => {
        const container = document.getElementById("additionalLocationsSection");
        container.appendChild(createLocationEntry());
        updateAllSelects("additionalLocationsSection", "addLocationButton");
    });

    document.getElementById("editAddLocationButton").addEventListener("click", () => {
        const container = document.getElementById("editAdditionalLocationsSection");
        container.appendChild(createLocationEntry("", "", true));
        updateAllSelects("editAdditionalLocationsSection", "editAddLocationButton");
    });

    document.getElementById("newTripButton").addEventListener("click", function (e) {
        e.preventDefault();
        document.getElementById("newTripModal").classList.remove("hidden");
    });

    document.getElementById("closeModal").addEventListener("click", () => {
        document.getElementById("newTripModal").classList.add("hidden");
    });

    document.querySelectorAll('.editTripButton').forEach(button => {
        button.addEventListener('click', function () {
            const tripId = this.getAttribute('data-trip-id');
            const tripDate = this.getAttribute('data-trip-date');
            const freeSeats = this.getAttribute('data-free-seats');

            document.getElementById('editTripDate').value = tripDate;
            document.getElementById('editFreeSeats').value = freeSeats;

            const stopsContainer = document.getElementById('editAdditionalLocationsSection');
            stopsContainer.innerHTML = '';

            const hiddenStops = document.querySelectorAll(`div[data-trip-id="${tripId}"] input`);

            for (let i = 0; i < hiddenStops.length; i += 2) {
                const location = hiddenStops[i].value;
                const stopTime = hiddenStops[i + 1].value;

                const stopElement = document.createElement('div');
                stopElement.classList.add('location-entry');

                stopElement.innerHTML = `
                <strong>Stop ${i / 2 + 1}:</strong>
                <input type="text" name="locations[]" value="${location}" readonly />
                <label>ETA:</label>
                <input type="time" name="etas[]" value="${stopTime}" required />
                <button type="button" class="remove-location">Remove</button>
            `;

                stopsContainer.appendChild(stopElement);
            }

            document.getElementById('editTripModal').classList.remove('hidden');
        });
    });

    document.addEventListener('click', function (event) {
        if (event.target.classList.contains('remove-location')) {
            event.target.parentElement.remove();
        }
    });
});
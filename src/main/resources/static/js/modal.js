
document.addEventListener("DOMContentLoaded", function () {
    var allLocations = window.modalData.allLocations;
    var routeSourceId = window.modalData.routeSourceId;
    var routeDestinationId = window.modalData.routeDestinationId;

    console.log("TEST TEST")
    allLocations = allLocations.filter(function (loc) {
        return loc.id !== routeSourceId && loc.id !== routeDestinationId;
    });

    var selectedLocationIds = [];

    function updateSelectOptions(selectElement) {
        var currentVal = selectElement.value;
        selectElement.innerHTML = "";
        var defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.text = "Select location";
        selectElement.appendChild(defaultOption);
        allLocations.forEach(function (loc) {
            if (selectedLocationIds.indexOf(String(loc.id)) === -1 || currentVal == loc.id) {
                var option = document.createElement("option");
                option.value = loc.id;
                option.text = loc.name;
                selectElement.appendChild(option);
            }
        });
        selectElement.value = currentVal;
    }

    function updateAllSelects() {
        var selects = document.querySelectorAll(".location-entry.dynamic select");
        var dynamicCount = selects.length;
        selectedLocationIds = [];
        selects.forEach(function (sel) {
            if (sel.value !== "") {
                selectedLocationIds.push(sel.value);
            }
        });
        selects.forEach(function (sel) {
            updateSelectOptions(sel);
        });
        var addLocationButton = document.getElementById("addLocationButton");
        addLocationButton.disabled = dynamicCount >= allLocations.length;
    }

    function createLocationEntry() {
        var div = document.createElement("div");
        div.classList.add("location-entry", "dynamic");

        var select = document.createElement("select");
        select.name = "locations[]";
        select.classList.add("form-control");
        updateSelectOptions(select);

        var timeInput = document.createElement("input");
        timeInput.type = "time";
        timeInput.name = "etas[]";
        timeInput.classList.add("form-control");
        timeInput.required = true;

        var removeBtn = document.createElement("button");
        removeBtn.type = "button";
        removeBtn.classList.add("remove-location", "btn-redoutline");
        removeBtn.innerText = "Remove";
        removeBtn.addEventListener("click", function () {
            var selectedVal = select.value;
            var index = selectedLocationIds.indexOf(selectedVal);
            if (index !== -1) {
                selectedLocationIds.splice(index, 1);
            }
            div.remove();
            updateAllSelects();
        });

        select.addEventListener("change", function () {
            updateAllSelects();
        });

        div.appendChild(select);
        div.appendChild(timeInput);
        div.appendChild(removeBtn);

        return div;
    }

    document.getElementById("addLocationButton").addEventListener("click", function () {
        var container = document.getElementById("additionalLocationsSection");
        container.appendChild(createLocationEntry());
        updateAllSelects();
    });

    var newTripButton = document.getElementById("newTripButton");
    var newTripModal = document.getElementById("newTripModal");
    var closeModal = document.getElementById("closeModal");

    newTripButton.addEventListener("click", function (event) {
        console.log("here")
        event.preventDefault();
        newTripModal.classList.remove("hidden");
    });

    closeModal.addEventListener("click", function () {
        newTripModal.classList.add("hidden");
    });

    window.addEventListener("click", function (event) {
        if (event.target === newTripModal) {
            newTripModal.classList.add("hidden");
        }
    });
});

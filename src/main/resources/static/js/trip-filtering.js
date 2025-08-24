document.addEventListener("DOMContentLoaded", function () {
    var tbody = document.querySelector('.routes-table tbody');
    var allRows = Array.from(tbody.querySelectorAll('tr'));

    var statusFilter = document.getElementById('statusFilter');
    var beforeDate = document.getElementById('beforeDate');
    var afterDate = document.getElementById('afterDate');
    var companyFilter = document.getElementById('companyFilter');

    function filterTrips() {
        var statusValue = statusFilter.value;
        var beforeDateValue = beforeDate.value;
        var afterDateValue = afterDate.value;
        var companyValue = companyFilter ? companyFilter.value.toLowerCase() : "";

        tbody.innerHTML = "";

        var filteredRows = allRows.filter(function (row) {
            var dateText = row.querySelector('td:nth-child(1)').innerText;
            var date = new Date(dateText);
            var status = row.getAttribute("data-status");
            var company = row.querySelector('td:nth-child(6)').innerText.toLowerCase();

            var matchesStatus = !statusValue || status === statusValue;
            var matchesBeforeDate = !beforeDateValue || date <= new Date(beforeDateValue);
            var matchesAfterDate = !afterDateValue || date >= new Date(afterDateValue);
            var matchesCompany = !companyValue || company.indexOf(companyValue) !== -1;

            return matchesStatus && matchesBeforeDate && matchesAfterDate && matchesCompany;
        });

        filteredRows.forEach(function (row) {
            tbody.appendChild(row);
        });

        var notification = document.getElementById("noTrips");
        if (filteredRows.length === 0) {
            notification.classList.remove("hidden");
        } else {
            notification.classList.add("hidden");
        }
    }

    statusFilter.addEventListener("change", filterTrips);
    beforeDate.addEventListener("change", filterTrips);
    afterDate.addEventListener("change", filterTrips);
    if (companyFilter) {
        companyFilter.addEventListener("input", filterTrips);
    }

    document.getElementById('resetFiltersButton').addEventListener('click', function () {
        statusFilter.value = '';
        beforeDate.value = '';
        afterDate.value = '';
        if (companyFilter) {
            companyFilter.value = '';
        }
        filterTrips();
    });
});

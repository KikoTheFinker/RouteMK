document.addEventListener('DOMContentLoaded', function () {
    const filters = {
        dateFrom: document.getElementById('dateFrom'),
        dateTo: document.getElementById('dateTo'),
        pickup: document.getElementById('pickupFilter'),
        drop: document.getElementById('dropFilter'),
        minPrice: document.getElementById('minPrice'),
        maxPrice: document.getElementById('maxPrice'),
        resetBtn: document.getElementById('resetTicketFilters'),
    };

    const ticketElements = document.querySelectorAll('.ticket-container');
    const noTicketsMessage = document.getElementById('noTickets');

    function applyFilters() {
        let anyVisible = false;

        ticketElements.forEach(ticket => {
            const ticketDate = new Date(ticket.getAttribute('data-date'));
            const price = parseFloat(ticket.getAttribute('data-price'));
            const pickup = ticket.getAttribute('data-pickup').toLowerCase();
            const drop = ticket.getAttribute('data-drop').toLowerCase();

            let show = true;

            const dateFrom = filters.dateFrom.value ? new Date(filters.dateFrom.value) : null;
            const dateTo = filters.dateTo.value ? new Date(filters.dateTo.value) : null;
            if (dateFrom && ticketDate < dateFrom) show = false;
            if (dateTo && ticketDate > dateTo) show = false;

            if (filters.pickup.value && !pickup.includes(filters.pickup.value.toLowerCase())) show = false;
            if (filters.drop.value && !drop.includes(filters.drop.value.toLowerCase())) show = false;

            const minPrice = filters.minPrice.value ? parseFloat(filters.minPrice.value) : null;
            const maxPrice = filters.maxPrice.value ? parseFloat(filters.maxPrice.value) : null;
            if (minPrice !== null && price < minPrice) show = false;
            if (maxPrice !== null && price > maxPrice) show = false;

            ticket.style.display = show ? 'block' : 'none';
            if (show) anyVisible = true;
        });

        noTicketsMessage.classList.toggle('hidden', anyVisible);
    }

    [filters.dateFrom, filters.dateTo, filters.pickup, filters.drop, filters.minPrice, filters.maxPrice].forEach(input => input.addEventListener('input', applyFilters));

    filters.resetBtn.addEventListener('click', () => {
        filters.dateFrom.value = '';
        filters.dateTo.value = '';
        filters.pickup.value = '';
        filters.drop.value = '';
        filters.minPrice.value = '';
        filters.maxPrice.value = '';
        applyFilters();
    });

    applyFilters();
});

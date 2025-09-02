CREATE OR REPLACE VIEW top_selling_routes_view AS
WITH route_stats AS (
    SELECT
        r.route_id,
        r.from_location_id,
        r.to_location_id,
        to_org.company_name AS transport_organizer_name,
        COUNT(tk.ticket_id) AS total_tickets_sold,
        SUM(p.total_price) AS total_revenue,
        AVG(p.total_price) AS avg_ticket_price
    FROM route r
    JOIN transport_organizer to_org
        ON r.transport_organizer_id = to_org.transport_organizer_id
    JOIN trip tr ON r.route_id = tr.route_id
    JOIN ticket tk ON tr.trip_id = tk.trip_id
    JOIN payment p ON tk.payment_id = p.payment_id
    GROUP BY r.route_id, r.from_location_id, r.to_location_id, to_org.company_name
),
max_tickets AS (
    SELECT MAX(total_tickets_sold) AS max_sold
    FROM route_stats
)
SELECT rs.*
FROM route_stats rs, max_tickets mt
-- WHERE rs.total_tickets_sold >= 0.5 * mt.max_sold
ORDER BY rs.total_tickets_sold DESC;

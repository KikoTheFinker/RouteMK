DROP VIEW IF EXISTS top_selling_routes_view;

CREATE VIEW top_selling_routes_view AS
WITH route_stats AS (
    SELECT
        r.route_id,
        from_loc.name AS from_location_name,
        to_loc.name   AS to_location_name,
        to_org.company_name AS transport_organizer_name,
        COUNT(tk.ticket_id) AS total_tickets_sold,
        SUM(p.total_price)::numeric(38,2) AS total_revenue,
        AVG(p.total_price)::numeric(38,2) AS avg_ticket_price
    FROM route r
             JOIN transport_organizer to_org
                  ON r.transport_organizer_id = to_org.transport_organizer_id
             JOIN location from_loc
                  ON r.from_location_id = from_loc.location_id
             JOIN location to_loc
                  ON r.to_location_id = to_loc.location_id
             JOIN trip tr ON r.route_id = tr.route_id
             JOIN ticket tk ON tr.trip_id = tk.trip_id
             JOIN payment p ON tk.payment_id = p.payment_id
    GROUP BY r.route_id, from_loc.name, to_loc.name, to_org.company_name
),
     max_tickets AS (
         SELECT MAX(total_tickets_sold) AS max_sold
         FROM route_stats
     )
SELECT rs.*
FROM route_stats rs, max_tickets mt
ORDER BY rs.total_tickets_sold DESC;
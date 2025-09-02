DROP VIEW IF EXISTS company_performance_view;

CREATE VIEW company_performance_view AS
SELECT
    to_org.company_name,
    COUNT(DISTINCT r.route_id) AS routes_operated,
    COUNT(DISTINCT t.trip_id) AS trips_organized,
    COUNT(tk.ticket_id) AS total_tickets_sold,
    SUM(COALESCE(tk.price, 0))::double precision AS total_revenue,
    AVG(COALESCE(tk.price, 0))::double precision AS avg_ticket_price,
    COUNT(DISTINCT tk.account_id) AS unique_customers,
    ROUND(AVG(rev.rating)::numeric, 2)::double precision AS avg_rating
FROM transport_organizer to_org
         JOIN route r ON to_org.transport_organizer_id = r.transport_organizer_id
         JOIN trip t ON r.route_id = t.route_id
         LEFT JOIN ticket tk ON t.trip_id = tk.trip_id
         LEFT JOIN review rev ON tk.ticket_id = rev.ticket_id
GROUP BY to_org.transport_organizer_id, to_org.company_name
ORDER BY total_revenue DESC;
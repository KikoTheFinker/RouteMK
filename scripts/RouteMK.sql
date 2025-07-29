DROP TABLE IF EXISTS child_ticket CASCADE;
DROP TABLE IF EXISTS ticket_relations CASCADE;
DROP TABLE IF EXISTS ticket CASCADE;
DROP TABLE IF EXISTS payment CASCADE;
DROP TABLE IF EXISTS review CASCADE;
DROP TABLE IF EXISTS student_ticket CASCADE;
DROP TABLE IF EXISTS student CASCADE;
DROP TABLE IF EXISTS trip_stops CASCADE;
DROP TABLE IF EXISTS trip_days_active CASCADE;
DROP TABLE IF EXISTS trip CASCADE;
DROP TABLE IF EXISTS route CASCADE;
DROP TABLE IF EXISTS favorite CASCADE;
DROP TABLE IF EXISTS driver_drives_on_trip CASCADE;
DROP TABLE IF EXISTS driver_vehicle_operation CASCADE;
DROP TABLE IF EXISTS driver CASCADE;
DROP TABLE IF EXISTS transport_organizer CASCADE;
DROP TABLE IF EXISTS admin CASCADE;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS train CASCADE;
DROP TABLE IF EXISTS van CASCADE;
DROP TABLE IF EXISTS bus CASCADE;
DROP TABLE IF EXISTS automobile CASCADE;
DROP TABLE IF EXISTS vehicle CASCADE;

CREATE TABLE account (
    account_id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    password VARCHAR(60) NOT NULL
);

CREATE TABLE admin (
    admin_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    CONSTRAINT fk_admin_account_id FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE
);

CREATE TABLE payment (
    payment_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    date DATE NOT NULL,
    total_price DOUBLE PRECISION NOT NULL CHECK (total_price >= 0),
    n_tickets INT NOT NULL CHECK (n_tickets > 0),
    CONSTRAINT payment_account_id_fkey FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE
);

CREATE TABLE location (
    location_id SERIAL PRIMARY KEY,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE ticket (
    ticket_id SERIAL PRIMARY KEY,
    trip_id INT NOT NULL,
    gets_on_location_id INT NOT NULL,
    gets_off_location_id INT NOT NULL,
    account_id INT NOT NULL,
    date_purchased DATE NOT NULL,
    time_purchased TIME WITHOUT TIME ZONE NOT NULL,
    price DOUBLE PRECISION CHECK (price >= 0),
    seat VARCHAR(10),
    payment_id INT NOT NULL,
    CONSTRAINT gets_on_location_fkey FOREIGN KEY (gets_on_location_id) REFERENCES location(location_id) ON DELETE CASCADE,
    CONSTRAINT gets_off_location_fkey FOREIGN KEY (gets_off_location_id) REFERENCES location(location_id) ON DELETE CASCADE,
    CONSTRAINT ticket_account_id_fkey FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE,
    CONSTRAINT ticket_payment_id_fkey FOREIGN KEY (payment_id) REFERENCES payment(payment_id) ON DELETE CASCADE
);

CREATE TABLE review (
    review_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    ticket_id INT NOT NULL,
    description TEXT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    CONSTRAINT review_account_id_fkey FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE,
    CONSTRAINT review_ticket_id_fkey FOREIGN KEY (ticket_id) REFERENCES ticket(ticket_id) ON DELETE CASCADE
);


CREATE TABLE transport_organizer (
    transport_organizer_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    company_embg VARCHAR(50) NOT NULL,
    CONSTRAINT transport_organizer_account_id_fkey FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE
);

CREATE TABLE vehicle (
    transport_organizer_id INT NOT NULL,
    vehicle_id SERIAL PRIMARY KEY,
    model VARCHAR(30) NOT NULL,
    brand VARCHAR(30) NOT NULL,
    capacity VARCHAR(20) NOT NULL,
    year_manufactured VARCHAR(10),
    CONSTRAINT vehicle_transport_organizer_id_fkey FOREIGN KEY (transport_organizer_id) REFERENCES transport_organizer(transport_organizer_id) ON DELETE CASCADE

);

CREATE TABLE automobile (
    automobile_id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL UNIQUE,
    CONSTRAINT automobile_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

CREATE TABLE bus (
    bus_id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL UNIQUE,
    CONSTRAINT bus_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

CREATE TABLE van (
    van_id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL UNIQUE,
    CONSTRAINT van_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

CREATE TABLE train (
    train_id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL UNIQUE,
    CONSTRAINT train_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

CREATE TABLE route (
    route_id SERIAL PRIMARY KEY,
    transport_organizer_id INT NOT NULL,
    from_location_id INT NOT NULL,
    to_location_id INT NOT NULL,
    CONSTRAINT route_transport_organizer_id_fkey FOREIGN KEY (transport_organizer_id) REFERENCES transport_organizer(transport_organizer_id) ON DELETE CASCADE,
    CONSTRAINT route_from_location_id_fkey FOREIGN KEY (from_location_id) REFERENCES location(location_id),
    CONSTRAINT route_to_location_id_fkey FOREIGN KEY (to_location_id) REFERENCES location(location_id)
);

CREATE TABLE trip (
    trip_id SERIAL PRIMARY KEY,
    transport_organizer_id INT NOT NULL,
    route_id INT NOT NULL,
    free_seats INT CHECK (free_seats >= 0),
    date DATE NOT NULL,
    status VARCHAR(30),
    CONSTRAINT trip_transport_organizer_id_fkey FOREIGN KEY (transport_organizer_id) REFERENCES transport_organizer(transport_organizer_id),
    CONSTRAINT trip_route_id_fkey FOREIGN KEY (route_id) REFERENCES route(route_id) ON DELETE CASCADE
);

CREATE TABLE driver (
    driver_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    years_experience INT NOT NULL,
    transport_organizer_id INT,
    CONSTRAINT driver_account_id_fkey FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE,
    CONSTRAINT driver_transport_organizer_id_fkey FOREIGN KEY (transport_organizer_id) REFERENCES transport_organizer(transport_organizer_id) ON DELETE CASCADE
);

CREATE TABLE driver_vehicle_operation (
    driver_vehicle_operation_id SERIAL PRIMARY KEY,
    driver_id INT NOT NULL,
    vehicle_id INT NOT NULL,
    CONSTRAINT driver_vehicle_operation_driver_id_fkey FOREIGN KEY (driver_id) REFERENCES driver(driver_id) ON DELETE CASCADE,
    CONSTRAINT driver_vehicle_operation_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

CREATE TABLE driver_drives_on_trip (
    driver_drives_on_trip_id SERIAL PRIMARY KEY,
    driver_id INT NOT NULL,
    trip_id INT NOT NULL,
    CONSTRAINT driver_drives_on_trip_driver_id_fkey FOREIGN KEY (driver_id) REFERENCES driver(driver_id) ON DELETE CASCADE,
    CONSTRAINT driver_drives_on_trip_trip_id_fkey FOREIGN KEY (trip_id) REFERENCES trip(trip_id) ON DELETE CASCADE
);

CREATE TABLE favorite (
    favorite_id SERIAL PRIMARY KEY,
    route_id INT NOT NULL,
    account_id INT NOT NULL,
    CONSTRAINT favorite_route_id_fkey FOREIGN KEY (route_id) REFERENCES route(route_id) ON DELETE CASCADE,
    CONSTRAINT favorite_account_id_fkey FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE
);

CREATE TABLE trip_days_active (
    trip_days_active_id SERIAL PRIMARY KEY,
    route_id INT NOT NULL,
    day VARCHAR(20) NOT NULL,
    CONSTRAINT trip_days_active_route_id_fkey FOREIGN KEY (route_id) REFERENCES route(route_id) ON DELETE CASCADE
);

CREATE TABLE trip_stops (
    trip_stop_id SERIAL PRIMARY KEY,
    trip_id INT NOT NULL,
    location_id INT NOT NULL,
    stop_time TIME WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT trip_stops_trip_id_fkey FOREIGN KEY (trip_id) REFERENCES trip(trip_id) ON DELETE CASCADE,
    CONSTRAINT trip_stops_location_id_fkey FOREIGN KEY (location_id) REFERENCES location(location_id) ON DELETE CASCADE
);

CREATE TABLE student (
    student_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    university VARCHAR(100) NOT NULL,
    index_number VARCHAR(20) NOT NULL,
    CONSTRAINT student_account_id_fkey FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE
);

CREATE TABLE student_ticket (
    student_ticket_id SERIAL PRIMARY KEY,
    ticket_id INT NOT NULL,
    discount DOUBLE PRECISION,
    CONSTRAINT student_ticket_ticket_id_fkey FOREIGN KEY (ticket_id) REFERENCES ticket(ticket_id) ON DELETE CASCADE
);

CREATE TABLE child_ticket (
    child_ticket_id SERIAL PRIMARY KEY,
    ticket_id INT NOT NULL,
    discount DOUBLE PRECISION,
    embg VARCHAR(13) NOT NULL,
    parent_embg VARCHAR(13) NOT NULL,
    CONSTRAINT child_ticket_ticket_id_fkey FOREIGN KEY (ticket_id) REFERENCES ticket(ticket_id) ON DELETE CASCADE
);

CREATE TABLE ticket_relations (
    ticket_relation_id SERIAL PRIMARY KEY,
    parent_ticket_id INT NOT NULL,
    child_ticket_id INT NOT NULL,
    CONSTRAINT ticket_relations_parent_ticket_id_fkey FOREIGN KEY (parent_ticket_id) REFERENCES ticket(ticket_id) ON DELETE CASCADE,
    CONSTRAINT ticket_relations_child_ticket_id_fkey FOREIGN KEY (child_ticket_id) REFERENCES ticket(ticket_id) ON DELETE CASCADE
);
INSERT INTO account values(100, 'duko@outlook.com', 'David', 'Davidov', '$2a$12$pr3az9qix0CnAsX84C2clu9cG9JDlfqfK.sMqaFhPYR7D5fiz8BjO'); -- pw: d
INSERT INTO account values(200, 'kiko@outlook.com', 'Kiko', 'Kikoski', '$2a$12$KCpRdwqqm2S0BX8fHjzCBO570ivpoJZ6tuIc1W6gwSpzObvxykZ8y'); -- pw: k
INSERT INTO account values(300, 'jama@outlook.com', 'Jana', 'Janoska', '$2a$12$XO94fugzv1B9T.IjEbFSWu4WyCDFTdMM9Vg4Xli7DWiDH1LGwgj7G'); -- pw: j
INSERT INTO account values(400, 'verche@outlook.com', 'Verche', 'Verchoska', '$2a$12$XO94fugzv1B9T.IjEbFSWu4WyCDFTdMM9Vg4Xli7DWiDH1LGwgj7G'); -- pw: v


INSERT INTO transport_organizer values(100, 100, 'Galeb', '1234512345123');
INSERT INTO transport_organizer values(200, 200, 'Delfina', '1234512345124');
INSERT INTO transport_organizer values(300, 300, 'Classic', '123453245124');


INSERT INTO location values(100, 3.2, 1.3, 'Ohrid');
INSERT INTO location values(200, 3.6, 1.4, 'Bitola');
INSERT INTO location values(300, 1.1, 4.5, 'Skopje');
INSERT INTO location values(400, 1.2, 4.8, 'Veles');
INSERT INTO location values(500, 1.3, 4.2, 'Prilep');
INSERT INTO location values(600, 1.5, 4.5, 'Vevcani');



INSERT INTO route values(100, 100, 100, 300);
INSERT INTO route values(200, 200, 100, 200);
INSERT INTO route values(300, 200, 200, 300);
INSERT INTO route values(400, 200, 300, 200);
INSERT INTO route values(500, 200, 300, 100);
INSERT INTO route values(600, 100, 300, 100);
INSERT INTO route values(700, 300, 300, 100);


INSERT INTO trip values(400, 100, 100, 33, '12-02-2025', 'NOT_STARTED');
INSERT INTO trip values(500, 200, 100, 40, '02-03-2025', 'NOT_STARTED');
INSERT INTO trip values(600, 200, 300, 6, '02-05-2025', 'NOT_STARTED');
INSERT INTO trip values(700, 100, 300, 13, '12-01-2025', 'NOT_STARTED');
INSERT INTO trip values(800, 200, 300, 50, '02-9-2025', 'NOT_STARTED');
INSERT INTO trip values(900, 200, 300, 6, '02-10-2025', 'NOT_STARTED');

INSERT INTO trip_stops values(300, 400, 300, '19:00');
INSERT INTO trip_stops values(400, 400, 100, '20:00');
INSERT INTO trip_stops values(500, 500, 200, '15:30');
INSERT INTO trip_stops values(600, 500, 100, '16:45');
INSERT INTO trip_stops values(700, 600, 600, '09:10');
INSERT INTO trip_stops values(800, 600, 200, '12:30');
INSERT INTO trip_stops values(900, 700, 400, '19:00');
INSERT INTO trip_stops values(1000, 700, 600, '22:30');
INSERT INTO trip_stops values(1100, 800, 500, '11:10');
INSERT INTO trip_stops values(1200, 800, 200, '12:30');
INSERT INTO trip_stops values(1300, 800, 100, '01:10');
INSERT INTO trip_stops values(1499, 800, 200, '02:00');


INSERT INTO payment values(100, 300, '02-02-2025', 20.98, 2);
INSERT INTO payment values(200, 300, '03-02-2025', 9.99, 1);

INSERT INTO ticket values(100, 400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '3', 100);
INSERT INTO ticket values(101, 400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '4', 100);
INSERT INTO ticket values(102, 400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '5', 100);
INSERT INTO ticket values(103, 400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '6', 100);
INSERT INTO ticket values(104, 400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '7', 100);
INSERT INTO ticket values(105, 400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '8', 100);

INSERT INTO ticket values(200, 400, 100, 300, 300, '02-02-2025', '10:04', 10.99, '4', 200);
INSERT INTO ticket values(300, 600, 100, 300, 300, '03-02-2025', '09:32', 9.99, '30', 200);


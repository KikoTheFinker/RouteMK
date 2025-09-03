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

CREATE TABLE account
(
    account_id SERIAL PRIMARY KEY,
    email      VARCHAR(100) NOT NULL UNIQUE,
    name       VARCHAR(50)  NOT NULL,
    surname    VARCHAR(50)  NOT NULL,
    password   VARCHAR(60)  NOT NULL
);

CREATE TABLE admin
(
    admin_id   SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    CONSTRAINT fk_admin_account_id FOREIGN KEY (account_id) REFERENCES account (account_id) ON DELETE CASCADE
);

CREATE TABLE payment
(
    payment_id  SERIAL PRIMARY KEY,
    account_id  INT              NOT NULL,
    date        DATE             NOT NULL,
    total_price DOUBLE PRECISION NOT NULL CHECK (total_price >= 0),
    n_tickets   INT              NOT NULL CHECK (n_tickets > 0),
    CONSTRAINT payment_account_id_fkey FOREIGN KEY (account_id) REFERENCES account (account_id) ON DELETE CASCADE
);

CREATE TABLE location
(
    location_id SERIAL PRIMARY KEY,
    latitude    DOUBLE PRECISION NOT NULL,
    longitude   DOUBLE PRECISION NOT NULL,
    name        VARCHAR(100)     NOT NULL
);

CREATE TABLE transport_organizer
(
    transport_organizer_id SERIAL PRIMARY KEY,
    account_id             INT          NOT NULL,
    company_name           VARCHAR(100) NOT NULL,
    company_embg           VARCHAR(50)  NOT NULL,
    CONSTRAINT transport_organizer_account_id_fkey FOREIGN KEY (account_id) REFERENCES account (account_id) ON DELETE CASCADE
);

CREATE TABLE route
(
    route_id               SERIAL PRIMARY KEY,
    transport_organizer_id INT NOT NULL,
    from_location_id       INT NOT NULL,
    to_location_id         INT NOT NULL,
    CONSTRAINT route_transport_organizer_id_fkey FOREIGN KEY (transport_organizer_id) REFERENCES transport_organizer (transport_organizer_id) ON DELETE CASCADE,
    CONSTRAINT route_from_location_id_fkey FOREIGN KEY (from_location_id) REFERENCES location (location_id),
    CONSTRAINT route_to_location_id_fkey FOREIGN KEY (to_location_id) REFERENCES location (location_id)
);

CREATE TABLE trip
(
    trip_id                SERIAL PRIMARY KEY,
    base_price             DOUBLE PRECISION,
    transport_organizer_id INT  NOT NULL,
    route_id               INT  NOT NULL,
    free_seats             INT CHECK (free_seats >= 0),
    date                   DATE NOT NULL,
    status                 VARCHAR(30),
    CONSTRAINT trip_transport_organizer_id_fkey FOREIGN KEY (transport_organizer_id) REFERENCES transport_organizer (transport_organizer_id),
    CONSTRAINT trip_route_id_fkey FOREIGN KEY (route_id) REFERENCES route (route_id) ON DELETE CASCADE
);

CREATE TABLE ticket
(
    ticket_id            SERIAL PRIMARY KEY,
    trip_id              INT                    NOT NULL,
    gets_on_location_id  INT                    NOT NULL,
    gets_off_location_id INT                    NOT NULL,
    account_id           INT                    NOT NULL,
    date_purchased       DATE                   NOT NULL,
    time_purchased       TIME WITHOUT TIME ZONE NOT NULL,
    price                DOUBLE PRECISION CHECK (price >= 0),
    seat                 VARCHAR(10),
    payment_id           INT                    NOT NULL,
    CONSTRAINT gets_on_location_fkey FOREIGN KEY (gets_on_location_id) REFERENCES location (location_id) ON DELETE CASCADE,
    CONSTRAINT gets_off_location_fkey FOREIGN KEY (gets_off_location_id) REFERENCES location (location_id) ON DELETE CASCADE,
    CONSTRAINT ticket_account_id_fkey FOREIGN KEY (account_id) REFERENCES account (account_id) ON DELETE CASCADE,
    CONSTRAINT ticket_payment_id_fkey FOREIGN KEY (payment_id) REFERENCES payment (payment_id) ON DELETE CASCADE,
    CONSTRAINT trip_id_fkey FOREIGN KEY (trip_id) REFERENCES trip (trip_id) ON DELETE CASCADE
);

CREATE TABLE review
(
    review_id   SERIAL PRIMARY KEY,
    account_id  INT NOT NULL,
    ticket_id   INT NOT NULL,
    description TEXT,
    rating      INT CHECK (rating >= 1 AND rating <= 5),
    CONSTRAINT review_account_id_fkey FOREIGN KEY (account_id) REFERENCES account (account_id) ON DELETE CASCADE,
    CONSTRAINT review_ticket_id_fkey FOREIGN KEY (ticket_id) REFERENCES ticket (ticket_id) ON DELETE CASCADE
);

CREATE TABLE vehicle
(
    transport_organizer_id INT         NOT NULL,
    vehicle_id             SERIAL PRIMARY KEY,
    model                  VARCHAR(30) NOT NULL,
    brand                  VARCHAR(30) NOT NULL,
    capacity               VARCHAR(20) NOT NULL,
    year_manufactured      VARCHAR(10),
    CONSTRAINT vehicle_transport_organizer_id_fkey FOREIGN KEY (transport_organizer_id) REFERENCES transport_organizer (transport_organizer_id) ON DELETE CASCADE

);

CREATE TABLE automobile
(
    automobile_id SERIAL PRIMARY KEY,
    vehicle_id    INT NOT NULL UNIQUE,
    CONSTRAINT automobile_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES vehicle (vehicle_id) ON DELETE CASCADE
);

CREATE TABLE bus
(
    bus_id     SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL UNIQUE,
    CONSTRAINT bus_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES vehicle (vehicle_id) ON DELETE CASCADE
);

CREATE TABLE van
(
    van_id     SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL UNIQUE,
    CONSTRAINT van_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES vehicle (vehicle_id) ON DELETE CASCADE
);

CREATE TABLE train
(
    train_id   SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL UNIQUE,
    CONSTRAINT train_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES vehicle (vehicle_id) ON DELETE CASCADE
);

CREATE TABLE driver
(
    driver_id              SERIAL PRIMARY KEY,
    account_id             INT NOT NULL,
    years_experience       INT NOT NULL,
    transport_organizer_id INT,
    CONSTRAINT driver_account_id_fkey FOREIGN KEY (account_id) REFERENCES account (account_id) ON DELETE CASCADE,
    CONSTRAINT driver_transport_organizer_id_fkey FOREIGN KEY (transport_organizer_id) REFERENCES transport_organizer (transport_organizer_id) ON DELETE CASCADE
);

CREATE TABLE driver_vehicle_operation
(
    driver_vehicle_operation_id SERIAL PRIMARY KEY,
    driver_id                   INT NOT NULL,
    vehicle_id                  INT NOT NULL,
    CONSTRAINT driver_vehicle_operation_driver_id_fkey FOREIGN KEY (driver_id) REFERENCES driver (driver_id) ON DELETE CASCADE,
    CONSTRAINT driver_vehicle_operation_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES vehicle (vehicle_id) ON DELETE CASCADE
);

CREATE TABLE driver_drives_on_trip
(
    driver_drives_on_trip_id SERIAL PRIMARY KEY,
    driver_id                INT NOT NULL,
    trip_id                  INT NOT NULL,
    CONSTRAINT driver_drives_on_trip_driver_id_fkey FOREIGN KEY (driver_id) REFERENCES driver (driver_id) ON DELETE CASCADE,
    CONSTRAINT driver_drives_on_trip_trip_id_fkey FOREIGN KEY (trip_id) REFERENCES trip (trip_id) ON DELETE CASCADE
);

CREATE TABLE favorite
(
    favorite_id SERIAL PRIMARY KEY,
    route_id    INT NOT NULL,
    account_id  INT NOT NULL,
    CONSTRAINT favorite_route_id_fkey FOREIGN KEY (route_id) REFERENCES route (route_id) ON DELETE CASCADE,
    CONSTRAINT favorite_account_id_fkey FOREIGN KEY (account_id) REFERENCES account (account_id) ON DELETE CASCADE
);

CREATE TABLE trip_days_active
(
    trip_days_active_id SERIAL PRIMARY KEY,
    route_id            INT         NOT NULL,
    day                 VARCHAR(20) NOT NULL,
    CONSTRAINT trip_days_active_route_id_fkey FOREIGN KEY (route_id) REFERENCES route (route_id) ON DELETE CASCADE
);

CREATE TABLE trip_stops
(
    trip_stop_id SERIAL PRIMARY KEY,
    trip_id      INT                    NOT NULL,
    location_id  INT                    NOT NULL,
    stop_time    TIME WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT trip_stops_trip_id_fkey FOREIGN KEY (trip_id) REFERENCES trip (trip_id) ON DELETE CASCADE,
    CONSTRAINT trip_stops_location_id_fkey FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE CASCADE
);

CREATE TABLE student
(
    student_id   SERIAL PRIMARY KEY,
    account_id   INT          NOT NULL,
    university   VARCHAR(100) NOT NULL,
    index_number VARCHAR(20)  NOT NULL,
    CONSTRAINT student_account_id_fkey FOREIGN KEY (account_id) REFERENCES account (account_id) ON DELETE CASCADE
);

CREATE TABLE student_ticket
(
    student_ticket_id SERIAL PRIMARY KEY,
    ticket_id         INT NOT NULL,
    discount          DOUBLE PRECISION,
    CONSTRAINT student_ticket_ticket_id_fkey FOREIGN KEY (ticket_id) REFERENCES ticket (ticket_id) ON DELETE CASCADE
);

CREATE TABLE child_ticket
(
    child_ticket_id SERIAL PRIMARY KEY,
    ticket_id       INT         NOT NULL,
    discount        DOUBLE PRECISION,
    embg            VARCHAR(13) NOT NULL,
    parent_embg     VARCHAR(13) NOT NULL,
    CONSTRAINT child_ticket_ticket_id_fkey FOREIGN KEY (ticket_id) REFERENCES ticket (ticket_id) ON DELETE CASCADE
);

CREATE TABLE ticket_relations
(
    ticket_relation_id SERIAL PRIMARY KEY,
    parent_ticket_id   INT NOT NULL,
    child_ticket_id    INT NOT NULL,
    CONSTRAINT ticket_relations_parent_ticket_id_fkey FOREIGN KEY (parent_ticket_id) REFERENCES ticket (ticket_id) ON DELETE CASCADE,
    CONSTRAINT ticket_relations_child_ticket_id_fkey FOREIGN KEY (child_ticket_id) REFERENCES ticket (ticket_id) ON DELETE CASCADE
);
INSERT INTO account
values (100, 'duko@outlook.com', 'David', 'Davidov',
        '$2a$12$pr3az9qix0CnAsX84C2clu9cG9JDlfqfK.sMqaFhPYR7D5fiz8BjO'); -- pw: d
INSERT INTO account
values (200, 'kiko@outlook.com', 'Kiko', 'Kikoski',
        '$2a$12$KCpRdwqqm2S0BX8fHjzCBO570ivpoJZ6tuIc1W6gwSpzObvxykZ8y'); -- pw: k
INSERT INTO account
values (300, 'jama@outlook.com', 'Jana', 'Janoska',
        '$2a$12$XO94fugzv1B9T.IjEbFSWu4WyCDFTdMM9Vg4Xli7DWiDH1LGwgj7G'); -- pw: j
INSERT INTO account
values (400, 'verche@outlook.com', 'Verche', 'Verchoska',
        '$2a$12$XO94fugzv1B9T.IjEbFSWu4WyCDFTdMM9Vg4Xli7DWiDH1LGwgj7G'); -- pw: v


INSERT INTO transport_organizer
values (100, 100, 'Galeb', '1234512345123');
INSERT INTO transport_organizer
values (200, 200, 'Delfina', '1234512345124');
INSERT INTO transport_organizer
VALUES (300, 300, 'MakExpress', '1234512345125');

INSERT INTO admin
values (100, 300);


INSERT INTO location (location_id, latitude, longitude, name)
VALUES (100, 3.2, 1.3, 'Ohrid'),
       (200, 3.6, 1.4, 'Bitola'),
       (300, 1.1, 4.5, 'Skopje'),
       (400, 1.2, 4.8, 'Veles'),
       (500, 1.3, 4.2, 'Prilep'),
       (600, 1.5, 4.5, 'Vevcani'),
       (700, 2.1, 4.7, 'Kumanovo'),
       (800, 2.8, 3.9, 'Tetovo'),
       (900, 3.0, 4.0, 'Struga'),
       (1000, 2.4, 4.2, 'Gostivar'),
       (1100, 2.4, 4.2, 'Kicevo');


INSERT INTO route (route_id, transport_organizer_id, from_location_id, to_location_id)
VALUES (100, 100, 100, 300),
       (200, 200, 100, 200),
       (300, 200, 200, 300),
       (400, 200, 300, 200),
       (500, 200, 300, 100),
       (600, 100, 300, 100),
       (700, 300, 300, 100),
       (800, 100, 200, 400),  -- Galeb: Bitola to Veles
       (801, 100, 400, 700),  -- Galeb: Veles to Kumanovo
       (802, 100, 700, 800),  -- Galeb: Kumanovo to Tetovo
       (803, 200, 800, 900),  -- Delfina: Tetovo to Struga
       (804, 200, 900, 100),  -- Delfina: Struga to Ohrid
       (805, 200, 500, 600),  -- Delfina: Prilep to Vevcani
       (806, 300, 600, 1000), -- MakExpress: Vevcani to Gostivar
       (807, 300, 1000, 300), -- MakExpress: Gostivar to Skopje
       (808, 100, 300, 700),  -- Galeb: Skopje to Kumanovo
       (809, 200, 700, 300); -- Delfina: Kumanovo to Skopje


INSERT INTO trip (trip_id, base_price, route_id, transport_organizer_id, free_seats, date, status)
VALUES (400, 24.99, 100, 100, 33, '2025-02-12', 'NOT_STARTED'),
       (500, 24.99, 100, 100, 40, '2025-03-02', 'NOT_STARTED'),
       (600, 24.99, 100, 300, 6, '2025-05-02', 'NOT_STARTED'),
       (700, 24.99, 100, 300, 13, '2025-01-12', 'NOT_STARTED'),
       (800, 24.99, 100, 300, 50, '2025-09-02', 'NOT_STARTED'),
       (900, 24.99, 100, 300, 6, '2025-10-02', 'NOT_STARTED'),
       (1000, 19.99, 100, 100, 25, '2025-02-15', 'NOT_STARTED'),
       (1100, 19.99, 100, 300, 18, '2025-02-16', 'NOT_STARTED'),
       (1200, 19.99, 100, 200, 40, '2025-02-20', 'NOT_STARTED'),
       (1300, 19.99, 100, 300, 50, '2025-02-25', 'NOT_STARTED'),
       (1400, 19.99, 100, 300, 20, '2025-02-26', 'NOT_STARTED'),
       (1500, 19.99, 200, 100, 35, '2025-02-27', 'NOT_STARTED'),
       (1600, 19.99, 200, 200, 28, '2025-02-28', 'NOT_STARTED'),
       (1700, 19.99, 100, 300, 40, '2025-03-01', 'NOT_STARTED'),
       (1800, 19.99, 100, 100, 18, '2025-03-02', 'NOT_STARTED'),
       (1900, 19.99, 200, 300, 25, '2025-03-03', 'NOT_STARTED'),
       (2000, 19.99, 200, 200, 22, '2025-03-04', 'NOT_STARTED'),
       (2100, 19.99, 100, 100, 30, '2025-03-05', 'NOT_STARTED'),
       (2200, 19.99, 200, 300, 12, '2025-03-06', 'NOT_STARTED'),
       (2300, 19.99, 100, 100, 55, '2025-03-07', 'NOT_STARTED'),
       (2400, 19.99, 100, 100, 45, '2025-01-15', 'COMPLETED'),
       (2401, 19.99, 200, 200, 38, '2025-01-16', 'COMPLETED'),
       (2402, 19.99, 300, 200, 42, '2025-01-17', 'COMPLETED'),
       (2403, 19.99, 400, 200, 35, '2025-01-18', 'COMPLETED'),
       (2404, 19.99, 500, 200, 28, '2025-01-19', 'COMPLETED'),
       (2405, 19.99, 600, 100, 33, '2025-01-20', 'COMPLETED'),
       (2406, 19.99, 700, 300, 40, '2025-01-21', 'COMPLETED'),
       (2407, 19.99, 800, 100, 45, '2025-01-22', 'COMPLETED'),
       (2408, 19.99, 801, 100, 30, '2025-01-23', 'COMPLETED'),
       (2409, 19.99, 802, 100, 25, '2025-01-24', 'COMPLETED'),
       (2410, 19.99, 803, 200, 40, '2025-01-25', 'COMPLETED'),
       (2411, 19.99, 804, 200, 35, '2025-01-26', 'COMPLETED'),
       (2412, 19.99, 805, 200, 30, '2025-01-27', 'COMPLETED'),
       (2413, 19.99, 806, 300, 45, '2025-01-28', 'COMPLETED'),
       (2414, 19.99, 807, 300, 38, '2025-01-29', 'COMPLETED'),
       (2415, 19.99, 808, 100, 42, '2025-01-30', 'COMPLETED'),
       (2416, 19.99, 809, 200, 35, '2025-01-31', 'COMPLETED'),
       (2500, 19.99, 100, 100, 20, '2025-02-01', 'COMPLETED'),
       (2501, 19.99, 200, 200, 15, '2025-02-01', 'COMPLETED'),
       (2502, 19.99, 300, 200, 18, '2025-02-01', 'COMPLETED'),
       (2503, 19.99, 400, 200, 22, '2025-02-01', 'COMPLETED'),
       (2504, 19.99, 500, 200, 25, '2025-02-01', 'COMPLETED'),
       (2505, 19.99, 100, 100, 18, '2025-02-02', 'COMPLETED'),
       (2506, 19.99, 200, 200, 12, '2025-02-02', 'COMPLETED'),
       (2507, 19.99, 300, 200, 20, '2025-02-02', 'COMPLETED'),
       (2508, 19.99, 700, 300, 30, '2025-02-02', 'COMPLETED'),
       (2509, 19.99, 800, 100, 35, '2025-02-02', 'COMPLETED'),
       (2510, 19.99, 100, 100, 25, '2025-02-03', 'COMPLETED'),
       (2511, 19.99, 200, 200, 20, '2025-02-03', 'COMPLETED'),
       (2512, 19.99, 300, 200, 15, '2025-02-03', 'COMPLETED'),
       (2513, 19.99, 801, 100, 28, '2025-02-03', 'COMPLETED'),
       (2514, 19.99, 802, 100, 22, '2025-02-03', 'COMPLETED'),
       (2515, 19.99, 100, 100, 30, '2025-02-04', 'COMPLETED'),
       (2516, 19.99, 200, 200, 25, '2025-02-04', 'COMPLETED'),
       (2517, 19.99, 400, 200, 18, '2025-02-04', 'COMPLETED'),
       (2518, 19.99, 803, 200, 40, '2025-02-04', 'COMPLETED'),
       (2519, 19.99, 804, 200, 35, '2025-02-04', 'COMPLETED'),
       (2520, 19.99, 100, 100, 22, '2025-02-05', 'COMPLETED'),
       (2521, 19.99, 300, 200, 28, '2025-02-05', 'COMPLETED'),
       (2522, 19.99, 500, 200, 32, '2025-02-05', 'COMPLETED'),
       (2523, 19.99, 805, 200, 25, '2025-02-05', 'COMPLETED'),
       (2524, 19.99, 806, 300, 38, '2025-02-05', 'COMPLETED'),
       (2600, 19.99, 100, 100, 35, '2025-03-08', 'NOT_STARTED'),
       (2601, 19.99, 200, 200, 40, '2025-03-08', 'NOT_STARTED'),
       (2602, 19.99, 300, 200, 32, '2025-03-08', 'NOT_STARTED'),
       (2603, 19.99, 700, 300, 45, '2025-03-08', 'NOT_STARTED'),
       (2604, 19.99, 800, 100, 38, '2025-03-08', 'NOT_STARTED'),
       (2605, 19.99, 100, 100, 30, '2025-03-09', 'NOT_STARTED'),
       (2606, 19.99, 400, 200, 35, '2025-03-09', 'NOT_STARTED'),
       (2607, 19.99, 500, 200, 40, '2025-03-09', 'NOT_STARTED'),
       (2608, 19.99, 801, 100, 25, '2025-03-09', 'NOT_STARTED'),
       (2609, 19.99, 802, 100, 28, '2025-03-09', 'NOT_STARTED'),
       (2610, 19.99, 200, 200, 42, '2025-03-10', 'NOT_STARTED'),
       (2611, 19.99, 300, 200, 38, '2025-03-10', 'NOT_STARTED'),
       (2612, 19.99, 803, 200, 45, '2025-03-10', 'NOT_STARTED'),
       (2613, 19.99, 804, 200, 40, '2025-03-10', 'NOT_STARTED'),
       (2614, 19.99, 807, 300, 35, '2025-03-10', 'NOT_STARTED'),
       (2700, 19.99, 100, 100, 40, '2025-04-12', 'NOT_STARTED'),
       (2701, 19.99, 200, 200, 35, '2025-04-12', 'NOT_STARTED'),
       (2702, 19.99, 300, 200, 38, '2025-04-12', 'NOT_STARTED'),
       (2703, 19.99, 400, 200, 42, '2025-04-12', 'NOT_STARTED'),
       (2704, 19.99, 500, 200, 45, '2025-04-12', 'NOT_STARTED'),
       (2705, 19.99, 600, 100, 30, '2025-04-13', 'NOT_STARTED'),
       (2706, 19.99, 700, 300, 35, '2025-04-13', 'NOT_STARTED'),
       (2707, 19.99, 800, 100, 40, '2025-04-13', 'NOT_STARTED'),
       (2708, 19.99, 801, 100, 32, '2025-04-14', 'NOT_STARTED'),
       (2709, 19.99, 802, 100, 28, '2025-04-14', 'NOT_STARTED'),
       (2800, 19.99, 100, 100, 45, '2025-05-15', 'NOT_STARTED'),
       (2801, 19.99, 200, 200, 40, '2025-05-15', 'NOT_STARTED'),
       (2802, 19.99, 300, 200, 35, '2025-05-16', 'NOT_STARTED'),
       (2803, 19.99, 400, 200, 38, '2025-05-16', 'NOT_STARTED'),
       (2804, 19.99, 500, 200, 42, '2025-05-17', 'NOT_STARTED'),
       (2805, 19.99, 700, 300, 40, '2025-05-17', 'NOT_STARTED'),
       (2806, 19.99, 800, 100, 35, '2025-05-18', 'NOT_STARTED'),
       (2807, 19.99, 803, 200, 45, '2025-05-18', 'NOT_STARTED'),
       (2808, 19.99, 804, 200, 38, '2025-05-19', 'NOT_STARTED'),
       (2809, 19.99, 807, 300, 40, '2025-05-19', 'NOT_STARTED');

INSERT INTO trip_stops (trip_stop_id, trip_id, location_id, stop_time)
VALUES (300, 400, 300, '19:00'),
       (400, 400, 100, '20:00'),
       (500, 500, 200, '15:30'),
       (600, 500, 100, '16:45'),
       (700, 600, 600, '09:10'),
       (800, 600, 200, '12:30'),
       (900, 700, 400, '19:00'),
       (1000, 700, 600, '22:30'),
       (1100, 800, 500, '11:10'),
       (1200, 800, 200, '12:30'),
       (1300, 800, 100, '01:10'),
       (1499, 800, 200, '02:00'),
       (1500, 1000, 100, '08:00'),
       (1501, 1000, 900, '09:15'),
       (1502, 1000, 300, '11:00'),
       (1901, 1400, 100, '08:00'),
       (1902, 1400, 200, '09:30'),
       (2001, 1500, 300, '07:00'),
       (2002, 1500, 500, '10:00'),
       (2003, 1500, 100, '12:00'),
       (2101, 1600, 300, '06:15'),
       (2102, 1600, 200, '08:30'),
       (2201, 1700, 300, '09:00'),
       (2202, 1700, 100, '11:30'),
       (2301, 1800, 100, '13:00'),
       (2302, 1800, 300, '16:00'),
       (1600, 1100, 200, '07:30'),
       (1601, 1100, 500, '08:10'),
       (1602, 1100, 400, '09:00'),
       (1603, 1100, 300, '10:00'),
       (1700, 1200, 300, '14:00'),
       (1701, 1200, 800, '15:10'),
       (1702, 1200, 1000, '16:00'),
       (1703, 1200, 200, '17:30'),
       (1800, 1300, 300, '06:00'),
       (1801, 1300, 700, '07:30'),
       (1802, 1300, 100, '09:45'),
       (3000, 2400, 100, '08:00'),
       (3001, 2400, 300, '10:30'),
       (3002, 2401, 100, '09:00'),
       (3003, 2401, 200, '10:15'),
       (3004, 2402, 200, '11:00'),
       (3005, 2402, 300, '12:45'),
       (3006, 2403, 300, '13:30'),
       (3007, 2403, 200, '15:00'),
       (3008, 2404, 300, '16:00'),
       (3009, 2404, 100, '18:30'),
       (3010, 2407, 200, '07:30'),
       (3011, 2407, 400, '09:00'),
       (3012, 2408, 400, '10:15'),
       (3013, 2408, 700, '12:00'),
       (3014, 2409, 700, '13:45'),
       (3015, 2409, 800, '15:30'),
       (3100, 2500, 100, '06:00'),
       (3101, 2500, 300, '08:30'),
       (3102, 2501, 100, '07:00'),
       (3103, 2501, 200, '08:15'),
       (3104, 2502, 200, '09:00'),
       (3105, 2502, 300, '10:45'),
       (3106, 2505, 100, '14:00'),
       (3107, 2505, 300, '16:30'),
       (3108, 2506, 100, '15:00'),
       (3109, 2506, 200, '16:15'),
       (3110, 2510, 100, '18:00'),
       (3111, 2510, 300, '20:30'),
       (3112, 2515, 100, '19:00'),
       (3113, 2515, 300, '21:30'),
       (3114, 2520, 100, '05:30'),
       (3115, 2520, 300, '08:00');


INSERT INTO payment (payment_id, account_id, date, total_price, n_tickets)
VALUES (100, 300, '02-02-2025', 20.98, 2),
       (200, 300, '03-02-2025', 9.99, 1),
       (300, 300, '2025-02-10', 25.99, 2),
       (400, 300, '2025-02-11', 19.50, 1),
       (500, 300, '2025-02-12', 45.75, 3),
       (600, 300, '2025-02-13', 32.00, 2),
       (700, 300, '2025-02-14', 15.25, 1),
       (800, 300, '2025-02-15', 67.80, 4),
       (900, 300, '2025-02-16', 28.90, 2),
       (1000, 300, '2025-02-17', 12.99, 1),
       (1100, 300, '2025-02-18', 38.45, 2),
       (1200, 300, '2025-02-19', 54.30, 3),
       (1300, 100, '2025-01-10', 89.75, 5),
       (1301, 200, '2025-01-10', 45.50, 3),
       (1302, 300, '2025-01-11', 67.25, 4),
       (1303, 400, '2025-01-11', 23.99, 2),
       (1304, 100, '2025-01-12', 156.80, 8),
       (1305, 200, '2025-01-12', 78.45, 5),
       (1306, 300, '2025-01-13', 34.75, 2),
       (1307, 400, '2025-01-13', 92.30, 6),
       (1308, 100, '2025-01-14', 45.99, 3),
       (1309, 200, '2025-01-14', 134.85, 7),
       (1310, 300, '2025-01-15', 58.50, 4),
       (1311, 400, '2025-01-15', 27.75, 2),
       (1312, 100, '2025-01-16', 98.60, 6),
       (1313, 200, '2025-01-16', 65.25, 4),
       (1314, 300, '2025-01-17', 189.99, 9),
       (1315, 400, '2025-01-17', 76.80, 5),
       (1316, 100, '2025-01-18', 43.50, 3),
       (1317, 200, '2025-01-18', 112.75, 7),
       (1318, 300, '2025-01-19', 85.40, 5),
       (1319, 400, '2025-01-19', 36.99, 2),
       (1320, 100, '2025-01-20', 167.25, 8),
       (1400, 100, '2025-02-01', 234.75, 10),
       (1401, 200, '2025-02-01', 145.80, 8),
       (1402, 300, '2025-02-01', 198.50, 9),
       (1403, 400, '2025-02-01', 87.25, 5),
       (1404, 100, '2025-02-02', 298.60, 12),
       (1405, 200, '2025-02-02', 176.40, 9),
       (1406, 300, '2025-02-02', 123.75, 7),
       (1407, 400, '2025-02-02', 254.90, 11),
       (1408, 100, '2025-02-03', 189.99, 9),
       (1409, 200, '2025-02-03', 267.85, 12),
       (1410, 300, '2025-02-03', 145.50, 8),
       (1411, 400, '2025-02-03', 98.75, 6),
       (1412, 100, '2025-02-04', 334.60, 14),
       (1413, 200, '2025-02-04', 215.25, 10),
       (1414, 300, '2025-02-04', 178.99, 9),
       (1415, 400, '2025-02-04', 156.80, 8),
       (1416, 100, '2025-02-05', 87.50, 5),
       (1417, 200, '2025-02-05', 245.75, 11),
       (1418, 300, '2025-02-05', 198.40, 9),
       (1419, 400, '2025-02-05', 123.99, 7);


INSERT INTO ticket (ticket_id, trip_id, gets_on_location_id, gets_off_location_id, account_id, date_purchased,
                    time_purchased, price, seat, payment_id)
VALUES (301, 400, 100, 200, 300, '08-08-2025', '10:45', 12.50, '12A', 200),
       (302, 500, 200, 300, 300, '08-09-2025', '10:50', 12.50, '12B', 200),
       (303, 600, 100, 300, 300, '07-9-2025', '11:05', 9.99, '15', 200),
       (304, 600, 200, 300, 300, '03-02-2025', '11:20', 9.99, '16', 200),
       (305, 700, 300, 200, 300, '04-02-2025', '12:00', 11.00, '20A', 200),
       (306, 800, 300, 100, 300, '04-02-2025', '12:15', 11.00, '20B', 200),
       (307, 900, 100, 300, 300, '05-02-2025', '13:10', 14.00, '25A', 200),
       (308, 1000, 200, 100, 300, '05-02-2025', '13:20', 14.00, '25B', 200),
       (309, 1100, 300, 100, 300, '06-02-2025', '14:40', 9.50, '30A', 200),
       (310, 1200, 200, 300, 300, '06-02-2025', '14:50', 9.50, '30B', 200),
       (311, 1300, 600, 200, 300, '06-02-2025', '15:10', 9.50, '30C', 200),
       (100, 1400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '3', 100),
       (101, 400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '4', 100),
       (102, 400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '5', 100),
       (103, 400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '6', 100),
       (104, 400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '7', 100),
       (105, 400, 100, 100, 300, '02-02-2025', '10:03', 10.99, '8', 100),
       (200, 400, 100, 300, 300, '02-02-2025', '10:04', 10.99, '4', 200),
       (300, 600, 100, 300, 300, '03-02-2025', '09:32', 9.99, '30', 200),
       (1000, 2400, 100, 300, 100, '2025-01-10', '09:15', 18.50, '1A', 1300),
       (1001, 2400, 100, 300, 100, '2025-01-10', '09:15', 18.50, '1B', 1300),
       (1002, 2400, 100, 300, 100, '2025-01-10', '09:15', 18.50, '1C', 1300),
       (1003, 2400, 100, 300, 100, '2025-01-10', '09:15', 17.25, '2A', 1300),
       (1004, 2400, 100, 300, 100, '2025-01-10', '09:15', 17.00, '2B', 1300),
       (1005, 2401, 100, 200, 200, '2025-01-10', '10:30', 15.50, '3A', 1301),
       (1006, 2401, 100, 200, 200, '2025-01-10', '10:30', 15.50, '3B', 1301),
       (1007, 2401, 100, 200, 200, '2025-01-10', '10:30', 14.50, '4A', 1301),
       (1008, 2402, 200, 300, 300, '2025-01-11', '11:45', 16.75, '5A', 1302),
       (1009, 2402, 200, 300, 300, '2025-01-11', '11:45', 16.75, '5B', 1302),
       (1010, 2402, 200, 300, 300, '2025-01-11', '11:45', 16.75, '6A', 1302),
       (1011, 2402, 200, 300, 300, '2025-01-11', '11:45', 17.00, '6B', 1302),
       (1012, 2403, 300, 200, 400, '2025-01-11', '12:30', 12.00, '7A', 1303),
       (1013, 2403, 300, 200, 400, '2025-01-11', '12:30', 11.99, '7B', 1303),
       (1020, 2404, 300, 100, 100, '2025-01-12', '13:15', 19.60, '8A', 1304),
       (1021, 2404, 300, 100, 100, '2025-01-12', '13:15', 19.60, '8B', 1304),
       (1022, 2404, 300, 100, 100, '2025-01-12', '13:15', 19.60, '9A', 1304),
       (1023, 2404, 300, 100, 100, '2025-01-12', '13:15', 19.60, '9B', 1304),
       (1024, 2404, 300, 100, 100, '2025-01-12', '13:15', 19.60, '10A', 1304),
       (1025, 2404, 300, 100, 100, '2025-01-12', '13:15', 19.60, '10B', 1304),
       (1026, 2404, 300, 100, 100, '2025-01-12', '13:15', 19.60, '11A', 1304),
       (1027, 2404, 300, 100, 100, '2025-01-12', '13:15', 19.40, '11B', 1304),
       (1030, 2407, 200, 400, 200, '2025-01-12', '14:00', 15.69, '12A', 1305),
       (1031, 2407, 200, 400, 200, '2025-01-12', '14:00', 15.69, '12B', 1305),
       (1032, 2407, 200, 400, 200, '2025-01-12', '14:00', 15.69, '13A', 1305),
       (1033, 2407, 200, 400, 200, '2025-01-12', '14:00', 15.69, '13B', 1305),
       (1034, 2407, 200, 400, 200, '2025-01-12', '14:00', 15.69, '14A', 1305),
       (1040, 2408, 400, 700, 300, '2025-01-13', '15:30', 17.25, '15A', 1306),
       (1041, 2408, 400, 700, 300, '2025-01-13', '15:30', 17.50, '15B', 1306),
       (1050, 2409, 700, 800, 400, '2025-01-13', '16:15', 15.38, '16A', 1307),
       (1051, 2409, 700, 800, 400, '2025-01-13', '16:15', 15.38, '16B', 1307),
       (1052, 2409, 700, 800, 400, '2025-01-13', '16:15', 15.38, '17A', 1307),
       (1053, 2409, 700, 800, 400, '2025-01-13', '16:15', 15.38, '17B', 1307),
       (1054, 2409, 700, 800, 400, '2025-01-13', '16:15', 15.38, '18A', 1307),
       (1055, 2409, 700, 800, 400, '2025-01-13', '16:15', 15.38, '18B', 1307),
       (1100, 2500, 100, 300, 100, '2025-02-01', '07:30', 24.50, '1A', 1400),
       (1101, 2500, 100, 300, 100, '2025-02-01', '07:30', 24.50, '1B', 1400),
       (1102, 2500, 100, 300, 100, '2025-02-01', '07:30', 24.50, '1C', 1400),
       (1103, 2500, 100, 300, 100, '2025-02-01', '07:30', 24.50, '1D', 1400),
       (1104, 2500, 100, 300, 100, '2025-02-01', '07:30', 24.50, '2A', 1400),
       (1105, 2500, 100, 300, 100, '2025-02-01', '07:30', 24.50, '2B', 1400),
       (1106, 2500, 100, 300, 100, '2025-02-01', '07:30', 24.50, '2C', 1400),
       (1107, 2500, 100, 300, 100, '2025-02-01', '07:30', 24.50, '2D', 1400),
       (1108, 2500, 100, 300, 100, '2025-02-01', '07:30', 22.75, '3A', 1400),
       (1109, 2500, 100, 300, 100, '2025-02-01', '07:30', 22.00, '3B', 1400),
       (1120, 2501, 100, 200, 200, '2025-02-01', '08:45', 18.22, '4A', 1401),
       (1121, 2501, 100, 200, 200, '2025-02-01', '08:45', 18.22, '4B', 1401),
       (1122, 2501, 100, 200, 200, '2025-02-01', '08:45', 18.22, '5A', 1401),
       (1123, 2501, 100, 200, 200, '2025-02-01', '08:45', 18.22, '5B', 1401),
       (1124, 2501, 100, 200, 200, '2025-02-01', '08:45', 18.22, '6A', 1401),
       (1125, 2501, 100, 200, 200, '2025-02-01', '08:45', 18.22, '6B', 1401),
       (1126, 2501, 100, 200, 200, '2025-02-01', '08:45', 18.22, '7A', 1401),
       (1127, 2501, 100, 200, 200, '2025-02-01', '08:45', 18.14, '7B', 1401),
       (1140, 2502, 200, 300, 300, '2025-02-01', '10:00', 22.06, '8A', 1402),
       (1141, 2502, 200, 300, 300, '2025-02-01', '10:00', 22.06, '8B', 1402),
       (1142, 2502, 200, 300, 300, '2025-02-01', '10:00', 22.06, '9A', 1402),
       (1143, 2502, 200, 300, 300, '2025-02-01', '10:00', 22.06, '9B', 1402),
       (1144, 2502, 200, 300, 300, '2025-02-01', '10:00', 22.06, '10A', 1402),
       (1145, 2502, 200, 300, 300, '2025-02-01', '10:00', 22.06, '10B', 1402),
       (1146, 2502, 200, 300, 300, '2025-02-01', '10:00', 22.06, '11A', 1402),
       (1147, 2502, 200, 300, 300, '2025-02-01', '10:00', 22.06, '11B', 1402),
       (1148, 2502, 200, 300, 300, '2025-02-01', '10:00', 22.06, '12A', 1402),
       (1160, 2503, 300, 200, 400, '2025-02-01', '11:15', 17.45, '13A', 1403),
       (1161, 2503, 300, 200, 400, '2025-02-01', '11:15', 17.45, '13B', 1403),
       (1162, 2503, 300, 200, 400, '2025-02-01', '11:15', 17.45, '14A', 1403),
       (1163, 2503, 300, 200, 400, '2025-02-01', '11:15', 17.45, '14B', 1403),
       (1164, 2503, 300, 200, 400, '2025-02-01', '11:15', 17.45, '15A', 1403),
       (1200, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '16A', 1404),
       (1201, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '16B', 1404),
       (1202, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '17A', 1404),
       (1203, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '17B', 1404),
       (1204, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '18A', 1404),
       (1205, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '18B', 1404),
       (1206, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '19A', 1404),
       (1207, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '19B', 1404),
       (1208, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '20A', 1404),
       (1209, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '20B', 1404),
       (1210, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '21A', 1404),
       (1211, 2505, 100, 300, 100, '2025-02-02', '12:30', 24.88, '21B', 1404),
       (1220, 2506, 100, 200, 200, '2025-02-02', '13:45', 19.60, '22A', 1405),
       (1221, 2506, 100, 200, 200, '2025-02-02', '13:45', 19.60, '22B', 1405),
       (1222, 2506, 100, 200, 200, '2025-02-02', '13:45', 19.60, '23A', 1405),
       (1223, 2506, 100, 200, 200, '2025-02-02', '13:45', 19.60, '23B', 1405),
       (1224, 2506, 100, 200, 200, '2025-02-02', '13:45', 19.60, '24A', 1405),
       (1225, 2506, 100, 200, 200, '2025-02-02', '13:45', 19.60, '24B', 1405);

-- Insert new routes for Struga to Skopje and Struga to Veles
INSERT INTO route (route_id, transport_organizer_id, from_location_id, to_location_id)
VALUES (810, 100, 900, 300), -- Struga to Skopje
       (811, 100, 900, 400);
-- Struga to Veles
-- Insert trips
INSERT INTO trip (trip_id, route_id, transport_organizer_id, free_seats, date, status)
VALUES (2900, 100, 100, 40, '2026-03-01', 'NOT_STARTED'), -- Ohrid -> Bitola -> Kicevo -> Skopje
       (2901, 100, 100, 35, '2026-03-02', 'NOT_STARTED'), -- Ohrid -> Kicevo -> Skopje
       (2902, 100, 100, 30, '2026-03-03', 'NOT_STARTED'), -- Ohrid -> Prilep -> Bitola -> Kicevo -> Skopje
       (2903, 811, 100, 45, '2026-03-04', 'NOT_STARTED'), -- Struga -> Ohrid -> Skopje -> Veles
       (2904, 810, 100, 38, '2026-03-05', 'NOT_STARTED');
-- Struga -> Ohrid -> Skopje

-- Insert stops for each trip
INSERT INTO trip_stops (trip_stop_id, trip_id, location_id, stop_time)
VALUES
    -- Trip 2900: Ohrid -> Bitola -> Kicevo -> Skopje
    (3200, 2900, 100, '08:00'),  -- Ohrid
    (3201, 2900, 200, '09:00'),  -- Bitola
    (3202, 2900, 1100, '10:00'), -- Kicevo
    (3203, 2900, 300, '11:00'),  -- Skopje
    -- Trip 2901: Ohrid -> Kicevo -> Skopje
    (3204, 2901, 100, '09:00'),  -- Ohrid
    (3205, 2901, 1100, '10:00'), -- Kicevo
    (3206, 2901, 300, '11:00'),  -- Skopje
    -- Trip 2902: Ohrid -> Prilep -> Bitola -> Kicevo -> Skopje
    (3207, 2902, 100, '07:00'),  -- Ohrid
    (3208, 2902, 500, '08:00'),  -- Prilep
    (3209, 2902, 200, '09:00'),  -- Bitola
    (3210, 2902, 1100, '10:00'), -- Kicevo
    (3211, 2902, 300, '11:00'),  -- Skopje
    -- Trip 2903: Struga -> Ohrid -> Skopje -> Veles
    (3212, 2903, 900, '07:00'),  -- Struga
    (3213, 2903, 100, '08:00'),  -- Ohrid
    (3214, 2903, 300, '10:00'),  -- Skopje
    (3215, 2903, 400, '11:00'),  -- Veles
    -- Trip 2904: Struga -> Ohrid -> Skopje
    (3216, 2904, 900, '08:00'),  -- Struga
    (3217, 2904, 100, '09:00'),  -- Ohrid
    (3218, 2904, 300, '11:00'); -- Skopje

INSERT INTO review (account_id, ticket_id, description, rating)
VALUES (100, 1001, 'Service was quick.', 4),
       (200, 1005, 'Could improve response time.', 3),
       (300, 1010, 'Very helpful staff.', 5),
       (200, 1025, 'Not satisfied.', 2),
       (300, 1032, 'Excellent service.', 5);


INSERT INTO favorite (favorite_id, route_id, account_id)
VALUES (144, 100, 300),
       (244, 200, 300),
       (344, 300, 300),
       (4123, 400, 300),
       (10123, 100, 100),
       (203, 200, 100),
       (302, 300, 100),
       (404, 400, 100),
       (1005, 100, 200),
       (2020, 200, 200),
       (3030, 300, 200),
       (4050, 400, 200);

INSERT INTO account (account_id, email, name, surname, password)
VALUES
    (601, 'marko.driver@example.com', 'Marko', 'Markovski', '$2a$12$pr3az9qix0CnAsX84C2clu9cG9JDlfqfK.sMqaFhPYR7D5fiz8BjO'), -- pw d
    (602, 'stefan.driver@example.com', 'Stefan', 'Stefanovski', '$2a$12$pr3az9qix0CnAsX84C2clu9cG9JDlfqfK.sMqaFhPYR7D5fiz8BjO'),
    (603, 'elena.driver@example.com', 'Elena', 'Elenova', '$2a$12$pr3az9qix0CnAsX84C2clu9cG9JDlfqfK.sMqaFhPYR7D5fiz8BjO'),
    (604, 'bojan.driver@example.com', 'Bojan', 'Bojanovski', '$2a$12$pr3az9qix0CnAsX84C2clu9cG9JDlfqfK.sMqaFhPYR7D5fiz8BjO');


INSERT INTO driver (driver_id, account_id, years_experience, transport_organizer_id)
VALUES
    (2001, 601, 5, 100),
    (2002, 602, 8, 200),
    (2003, 603, 6, 300),
    (2004, 604, 10, 100);


INSERT INTO driver_drives_on_trip (driver_drives_on_trip_id, driver_id, trip_id)
VALUES
    (10, 2001, 400),
    (11, 2002, 500),
    (12, 2003, 600),
    (13, 2004, 700);
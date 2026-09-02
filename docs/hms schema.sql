CREATE DATABASE IF NOT EXISTS hotel_management_system;

USE hotel_management_system;
DROP TABLE IF EXISTS room_has_reservation;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(12),
    address VARCHAR(60),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE room (
    room_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_type VARCHAR(50) NOT NULL,
    occupied TINYINT(1) DEFAULT 0 NOT NULL,
    room_number VARCHAR(10) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE reservation (
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_reservation_user
        FOREIGN KEY (user_id) REFERENCES user (user_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE room_has_reservation (
    room_id BIGINT NOT NULL,
    reservation_id BIGINT NOT NULL,
    PRIMARY KEY (room_id, reservation_id),
    CONSTRAINT fk_rhr_room
        FOREIGN KEY (room_id) REFERENCES room (room_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_rhr_reservation
        FOREIGN KEY (reservation_id) REFERENCES reservation (reservation_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
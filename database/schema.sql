CREATE DATABASE IF NOT EXISTS flottenmanager
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE flottenmanager;

CREATE TABLE IF NOT EXISTS fahrzeuge (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kennzeichen VARCHAR(20) NOT NULL UNIQUE,
    modell VARCHAR(100) NOT NULL,
    ist_elektro BOOLEAN NOT NULL DEFAULT FALSE,
    ist_verfuegbar BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS mitarbeiter (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    abteilung VARCHAR(100) NOT NULL,
    aktive_buchungen INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS buchungen (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mitarbeiter_id INT NOT NULL,
    fahrzeug_id INT NOT NULL,
    start_datum DATETIME NOT NULL,
    end_datum DATETIME NOT NULL,
    aktiv BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_buchung_mitarbeiter
        FOREIGN KEY (mitarbeiter_id) REFERENCES mitarbeiter(id),
    CONSTRAINT fk_buchung_fahrzeug
        FOREIGN KEY (fahrzeug_id) REFERENCES fahrzeuge(id)
);
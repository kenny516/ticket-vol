CREATE DATABASE ticket_vol;
\c ticket_vol;

CREATE TABLE avion
(
    id               SERIAL,
    modele           VARCHAR(50),
    date_fabrication DATE,
    PRIMARY KEY (id)
);
CREATE TABLE ville
(
    id  SERIAL,
    nom VARCHAR(50),
    PRIMARY KEY (id)
);
CREATE TABLE vol
(
    id              SERIAL,
    prix            DOUBLE PRECISION,
    date_depart     TIMESTAMP,
    id_ville_depart INTEGER NOT NULL,
    id_ville_arrive INTEGER NOT NULL,
    id_avion        INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_ville_depart) REFERENCES ville (id),
    FOREIGN KEY (id_ville_arrive) REFERENCES ville (id),
    FOREIGN KEY (id_avion) REFERENCES avion (id)
);
CREATE TABLE utilisateur
(
    id           SERIAL,
    nom          VARCHAR(50),
    prenom       VARCHAR(50),
    role         VARCHAR(50),
    pseudo       VARCHAR(50),
    mot_de_passe VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE type_siege
(
    id          SERIAL,
    designation VARCHAR(50),
    PRIMARY KEY (id)
);
CREATE TABLE reservation
(
    Id             SERIAL,
    prix           DOUBLE PRECISION,
    valider        BOOLEAN default true,
    nombre_places  INTEGER,
    id_type_siege  INTEGER NOT NULL,
    id_utilisateur INTEGER NOT NULL,
    id_vol         INTEGER NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (id_type_siege) REFERENCES type_siege (id),
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id),
    FOREIGN KEY (id_vol) REFERENCES vol (id)
);

CREATE TABLE parametre
(
    Id                         SERIAL,
    heures_minimum_reservation INTEGER,
    heures_minimum_annulation_ INTEGER,
    PRIMARY KEY (Id)
);
CREATE TABLE place
(
    id            SERIAL,
    nombre        INTEGER,
    id_type_siege INTEGER NOT NULL,
    id_avion      INTEGER NOT NULL,
    prix         DOUBLE PRECISION,
    PRIMARY KEY (id),
    FOREIGN KEY (id_type_siege) REFERENCES type_siege (id),
    FOREIGN KEY (id_avion) REFERENCES avion (id)
);
CREATE TABLE promotion
(
    id                     SERIAL,
    pourcentage_reduction_ DOUBLE PRECISION,
    nb_siege               INTEGER,
    id_type_siege          INTEGER NOT NULL,
    id_vol                 INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_type_siege) REFERENCES type_siege (id),
    FOREIGN KEY (id_vol) REFERENCES vol (id)
);
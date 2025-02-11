-- Insert sample data for type_siege
INSERT INTO type_siege (designation)
VALUES ('Business');
INSERT INTO type_siege (designation)
VALUES ('Economique');
-- Insert sample cities
INSERT INTO ville (nom)
VALUES ('Paris');
INSERT INTO ville (nom)
VALUES ('Londres');
INSERT INTO ville (nom)
VALUES ('New York');
INSERT INTO ville (nom)
VALUES ('Tokyo');
-- Insert sample aircraft
INSERT INTO avion (modele, date_fabrication)
VALUES ('Boeing 747', '2020-01-01');
INSERT INTO avion (modele, date_fabrication)
VALUES ('Airbus A320', '2019-06-15');
-- Add seat configurations
INSERT INTO place (nombre, id_type_siege, id_avion)
VALUES (50, 1, 1);
-- Business seats for Boeing
INSERT INTO place (nombre, id_type_siege, id_avion)
VALUES (200, 2, 1);
-- Economy seats for Boeing
INSERT INTO place (nombre, id_type_siege, id_avion)
VALUES (30, 1, 2);
-- Business seats for Airbus
INSERT INTO place (nombre, id_type_siege, id_avion)
VALUES (150, 2, 2);
-- Economy seats for Airbus
-- Insert sample flights
INSERT INTO vol (
        prix,
        date_depart,
        id_ville_depart,
        id_ville_arrive,
        id_avion
    )
VALUES (500.00, '2024-02-15 10:00:00', 1, 2, 1);
VALUES (1020.00, true, 2, 2);
INSERT INTO reservation (prix, valider, id_utilisateur, id_vol)
VALUES (400.00, true, 1, 1);
INSERT INTO vol (
        prix,
        date_depart,
        id_ville_depart,
        id_ville_arrive,
        id_avion
    )
VALUES (1200.00, '2024-02-15 14:00:00', 1, 3, 2);
-- Insert sample users
INSERT INTO utilisateur (nom, prenom)
VALUES ('Dupont', 'Jean');
INSERT INTO utilisateur (nom, prenom)
VALUES ('Martin', 'Sophie');
-- Insert sample promotions
INSERT INTO promotion (
        pourcentage_reduction_,
        nb_siege,
        id_type_siege,
        id_vol
    )
VALUES (20.0, 10, 2, 1);
-- 20% de réduction sur 10 sièges eco du vol 1
INSERT INTO promotion (
        pourcentage_reduction_,
        nb_siege,
        id_type_siege,
        id_vol
    )
VALUES (15.0, 5, 1, 2);
-- 15% de réduction sur 5 sièges business du vol 2
-- Insert system parameters
INSERT INTO parametre (
        heures_minimum_reservation,
        heures_minimum_annulation_
    )
VALUES (24, 48);
-- 24h minimum for reservation, 48h for cancellation
-- Insert sample reservations
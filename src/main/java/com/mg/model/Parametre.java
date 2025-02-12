package com.mg.model;

import javax.persistence.*;

@Entity
@Table(name = "parametre")
public class Parametre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "heures_minimum_reservation")
    private Integer heuresMinimumReservation;

    @Column(name = "heures_minimum_annulation_")
    private Integer heuresMinimumAnnulation;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHeuresMinimumReservation() {
        return heuresMinimumReservation;
    }

    public void setHeuresMinimumReservation(Integer heuresMinimumReservation) {
        this.heuresMinimumReservation = heuresMinimumReservation;
    }

    public Integer getHeuresMinimumAnnulation() {
        return heuresMinimumAnnulation;
    }

    public void setHeuresMinimumAnnulation(Integer heuresMinimumAnnulation) {
        this.heuresMinimumAnnulation = heuresMinimumAnnulation;
    }
}
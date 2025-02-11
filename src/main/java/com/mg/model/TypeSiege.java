package com.mg.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "type_siege")
public class TypeSiege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String designation;

    @OneToMany(mappedBy = "typeSiege")
    private List<Place> places;

    @OneToMany(mappedBy = "typeSiege")
    private List<Promotion> promotions;

    @OneToMany(mappedBy = "typeSiege")
    private List<Reservation> reservations;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
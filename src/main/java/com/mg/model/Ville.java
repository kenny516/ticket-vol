package com.mg.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ville")
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "villeDepart")
    private List<Vol> volsDepart;

    @OneToMany(mappedBy = "villeArrive")
    private List<Vol> volsArrive;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Vol> getVolsDepart() {
        return volsDepart;
    }

    public void setVolsDepart(List<Vol> volsDepart) {
        this.volsDepart = volsDepart;
    }

    public List<Vol> getVolsArrive() {
        return volsArrive;
    }

    public void setVolsArrive(List<Vol> volsArrive) {
        this.volsArrive = volsArrive;
    }
}
package com.mg.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ville")
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    @OneToMany(mappedBy = "villeDepart", fetch = FetchType.LAZY)
    private List<Vol> volsDepart;

    @OneToMany(mappedBy = "villeArrive", fetch = FetchType.LAZY)
    private List<Vol> volsArrive;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
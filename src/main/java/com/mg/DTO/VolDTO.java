package com.mg.DTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public class VolDTO {
    Integer id;
    Double prix;
    Date dateDepart;
    String villeDepart;
    String villeArrive;
    Map<String, Integer> placesDisponibles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getVilleArrive() {
        return villeArrive;
    }

    public void setVilleArrive(String villeArrive) {
        this.villeArrive = villeArrive;
    }

    public Map<String, Integer> getPlacesDisponibles() {
        return placesDisponibles;
    }

    public void setPlacesDisponibles(Map<String, Integer> placesDisponibles) {
        this.placesDisponibles = placesDisponibles;
    }
}

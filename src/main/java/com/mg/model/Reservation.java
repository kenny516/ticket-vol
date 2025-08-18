package com.mg.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double prix;

    @Column(columnDefinition = "boolean default true")
    private Boolean valider;

    @Column(name = "date_reservation", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateReservation;

    @Column(name = "nb_places")
    private Integer nombrePlaces;

    @Column(name = "nb_adulte")
    private Integer nombreAdultes;

    @Column(name = "nb_enfant")
    private Integer nombreEnfants;

    @Column(name = "promotion_detail")
    private String promotion_detail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_place_alea_vol")
    private PlaceAlea placeAlea;

    @Column(name = "payer")
    private Boolean payer;


    @Transient
    private Boolean isCancelable;


    // Getters and Setters
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

    public Boolean getValider() {
        return valider;
    }

    public void setValider(Boolean valider) {
        this.valider = valider;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Integer getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(Integer nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public Integer getNombreAdultes() {
        return nombreAdultes;
    }

    public void setNombreAdultes(Integer nombreAdultes) {
        this.nombreAdultes = nombreAdultes;
    }

    public Integer getNombreEnfants() {
        return nombreEnfants;
    }

    public void setNombreEnfants(Integer nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }

    public String getPromotion() {
        return promotion_detail;
    }

    public void setPromotion(String promotion_detail) {
        this.promotion_detail = promotion_detail;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public PlaceAlea getPlaceAlea() {
        return placeAlea;
    }

    public void setPlaceAlea(PlaceAlea placeAlea) {
        this.placeAlea = placeAlea;
    }

    public Boolean getCancelable() {
        return isCancelable;
    }

    public void setCancelable(Boolean cancelable) {
        isCancelable = cancelable;
    }

    public Reservation() {
    }

    public String getPromotion_detail() {
        return promotion_detail;
    }

    public void setPromotion_detail(String promotion_detail) {
        this.promotion_detail = promotion_detail;
    }

    public Boolean getPayer() {
        return payer;
    }

    public void setPayer(Boolean payer) {
        this.payer = payer;
    }
}
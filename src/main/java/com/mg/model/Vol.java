package com.mg.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vol")
public class Vol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double prix;

    @Column(name = "date_depart")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDepart;

    @ManyToOne
    @JoinColumn(name = "id_ville_depart")
    private Ville villeDepart;

    @ManyToOne
    @JoinColumn(name = "id_ville_arrive")
    private Ville villeArrive;

    @ManyToOne
    @JoinColumn(name = "id_avion")
    private Avion avion;

    @OneToMany(mappedBy = "vol")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "vol")
    private List<Promotion> promotions;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Ville getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(Ville villeDepart) {
        this.villeDepart = villeDepart;
    }

    public Ville getVilleArrive() {
        return villeArrive;
    }

    public void setVilleArrive(Ville villeArrive) {
        this.villeArrive = villeArrive;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}
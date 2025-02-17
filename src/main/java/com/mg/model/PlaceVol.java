package com.mg.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "place_vol")
public class PlaceVol {
    @Id
    private Integer id;

    @Column(name = "prix")
    private Double prix;


    @ManyToOne
    @JoinColumn(name = "id_vol")
    private Vol vol;

    @ManyToOne
    @JoinColumn(name = "id_type_siege")
    private TypeSiege typeSiege;

    @OneToMany(mappedBy = "placeVol")
    private List<Reservation> reservations;



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

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public TypeSiege getTypeSiege() {
        return typeSiege;
    }

    public void setTypeSiege(TypeSiege typeSiege) {
        this.typeSiege = typeSiege;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}

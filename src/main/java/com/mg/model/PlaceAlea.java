package com.mg.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "placeAlea")
public class PlaceAlea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer nombre;

    @Column(name = "restant")
    private Integer restant;

    @Column(name = "reserver")
    private Integer reserver;

    @ManyToOne
    @JoinColumn(name = "id_type_siege")
    private TypeSiege typeSiege;

    @ManyToOne
    @JoinColumn(name = "id_vol")
    private Vol vol;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "dateprom")
    private Date dateprom;


    @OneToMany(mappedBy = "placeAlea", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    public TypeSiege getTypeSiege() {
        return typeSiege;
    }

    public void setTypeSiege(TypeSiege typeSiege) {
        this.typeSiege = typeSiege;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Date getDateprom() {
        return dateprom;
    }

    public void setDateprom(Date dateprom) {
        this.dateprom = dateprom;
    }

    public Integer getRestant() {
        return restant;
    }

    public void setRestant(Integer restant) {
        this.restant = restant;
    }

    public Integer getReserver() {
        return reserver;
    }

    public void setReserver(Integer reserver) {
        this.reserver = reserver;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
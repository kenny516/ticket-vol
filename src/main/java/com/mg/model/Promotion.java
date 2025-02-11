package com.mg.model;

import javax.persistence.*;

@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pourcentage_reduction_")
    private Double pourcentageReduction;

    @Column(name = "nb_siege")
    private Integer nbSiege;

    @ManyToOne
    @JoinColumn(name = "id_type_siege")
    private TypeSiege typeSiege;

    @ManyToOne
    @JoinColumn(name = "id_vol")
    private Vol vol;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPourcentageReduction() {
        return pourcentageReduction;
    }

    public void setPourcentageReduction(Double pourcentageReduction) {
        this.pourcentageReduction = pourcentageReduction;
    }

    public Integer getNbSiege() {
        return nbSiege;
    }

    public void setNbSiege(Integer nbSiege) {
        this.nbSiege = nbSiege;
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
}
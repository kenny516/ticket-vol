package com.mg.service;

import com.mg.dao.PlaceDAO;
import com.mg.dao.AvionDAO;
import com.mg.dao.TypeSiegeDAO;
import com.mg.model.Place;
import com.mg.model.Avion;
import com.mg.model.PlaceVol;
import com.mg.model.TypeSiege;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlaceService extends AbstractService<Place> {
    private final PlaceDAO placeDAO;
    private final AvionDAO avionDAO;
    private final TypeSiegeDAO typeSiegeDAO;
    private final PlaceVolService placeVolService;

    public PlaceService() {
        super(new PlaceDAO());
        this.placeDAO = (PlaceDAO) dao;
        this.avionDAO = new AvionDAO();
        this.typeSiegeDAO = new TypeSiegeDAO();
        this.placeVolService = new PlaceVolService();
    }

    public List<Place> findByAvion(Integer avionId) {
        return placeDAO.findByAvion(avionId);
    }

    public List<Place> findByAvionForVol(Integer avionId,Integer volId) {
        List<Place> places = placeDAO.findByAvion(avionId);
        List<PlaceVol> placeVols = placeVolService.findByVol(volId);


        return places.stream().filter(place -> placeVols.stream().
                noneMatch(placeVol -> Objects.equals(place.getTypeSiege().getId(),placeVol.getPlace().getTypeSiege().getId()))
        ).collect(Collectors.toList());
    }

    public Place findById(Integer id) {
        return placeDAO.findById(Place.class, id);
    }
}
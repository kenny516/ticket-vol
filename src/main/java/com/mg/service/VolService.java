package com.mg.service;

import com.mg.DTO.VolDTO;
import com.mg.dao.VolDAO;
import com.mg.dao.VilleDAO;
import com.mg.dao.AvionDAO;
import com.mg.model.*;

import java.util.*;

public class VolService extends AbstractService<Vol> {
    private final VolDAO volDAO;
    private final VilleDAO villeDAO;
    private final AvionDAO avionDAO;

    public VolService() {
        super(new VolDAO());
        this.volDAO = (VolDAO) dao;
        this.villeDAO = new VilleDAO();
        this.avionDAO = new AvionDAO();
    }

    public List<Vol> searchVolsAdvanced(Ville villeDepart, Ville villeArrive,Date dateDebut, Date dateFin, Double prixMin, Double prixMax) {
        return volDAO.searchVolsAdvanced(villeDepart, villeArrive, dateDebut, dateFin, prixMin, prixMax);
    }

    public List<Vol> getAvailableVols(Date fromDate, Date toDate) {
        return volDAO.getAvailableVols(fromDate, toDate);
    }

    public Vol createVol(Integer villeDepartId, Integer villeArriveId,
                         Integer avionId, Date dateDepart) {
        Vol vol = new Vol();
        vol.setVilleDepart(villeDAO.findById(Ville.class, villeDepartId));
        vol.setVilleArrive(villeDAO.findById(Ville.class, villeArriveId));
        vol.setAvion(avionDAO.findById(Avion.class, avionId));
        vol.setDateDepart(dateDepart);

        volDAO.save(vol);
        return vol;
    }

    public void updateVol(Integer id, Integer villeDepartId, Integer villeArriveId,
                          Integer avionId, Date dateDepart) {
        Vol vol = volDAO.findById(Vol.class, id);
        if (vol != null) {
            vol.setVilleDepart(villeDAO.findById(Ville.class, villeDepartId));
            vol.setVilleArrive(villeDAO.findById(Ville.class, villeArriveId));
            vol.setAvion(avionDAO.findById(Avion.class, avionId));
            vol.setDateDepart(dateDepart);

            volDAO.update(vol);
        }
    }

    public List<Vol> getVolValide(){
        return volDAO.findUpcomingFlights();
    }

    public Vol getVolFullById(Integer id){
        return volDAO.findUpcomingFlightsById(id);
    }

    public VolDTO getVolsDTOById(Vol vol) {
        VolDTO volDTO = new VolDTO();
        volDTO.setId(vol.getId());
        volDTO.setDateDepart(vol.getDateDepart());
        volDTO.setVilleDepart(vol.getVilleDepart().getNom());
        volDTO.setVilleArrive(vol.getVilleArrive().getNom());
        Map<String, Integer> placesDisponibles = getStringIntegerMap(vol);
        volDTO.setPlaceVols(vol.getPlaceVols());
        volDTO.setPlacesDisponibles(placesDisponibles);

        return volDTO;
    }

    private static Map<String, Integer> getStringIntegerMap(Vol vol) {
        Map<String, Integer> placesDisponibles = new HashMap<>();
        PlaceVol placeVol = null;
        // Initialize available seats by type
        for (int j = 0; j < vol.getAvion().getPlaces().size(); j++) {
            placesDisponibles.put(vol.getAvion().getPlaces().get(j).getTypeSiege().getDesignation(),
                    vol.getAvion().getPlaces().get(j).getNombre());
        }
        // Subtract reserved seats
        for (int j = 0; j < vol.getPlaceVols().size(); j++) {
            for (int i = 0; i < vol.getPlaceVols().get(j).getReservations().size(); i++) {
                String typeSiege = vol.getPlaceVols().get(j).getReservations().get(j).getPlaceVol().getTypeSiege().getDesignation();
                Integer nombrePlaces = vol.getPlaceVols().get(j).getReservations().get(j).getNombrePlaces();

                // Skip invalid reservations
                if (nombrePlaces == null || !placesDisponibles.containsKey(typeSiege)) {
                    continue;
                }

                placesDisponibles.put(typeSiege,
                        placesDisponibles.get(typeSiege) - nombrePlaces);
            }
        }

        return placesDisponibles;
    }


    public Double promotionAvailable(Vol vol, Integer idTypeSiege, Integer nbSiege, Double prixInitial) {
        Integer nombrePlacesPromotion = 0;
        Integer nombrePlacesReserver = 0;
        Double promotionVal = 1.0;
        for (Promotion promo : vol.getPromotions()) {
            if (promo.getTypeSiege().getId().equals(idTypeSiege)) {
                nombrePlacesPromotion = promo.getNbSiege();
                promotionVal = promo.getPourcentageReduction();
                break;
            }
        }
        for (int i = 0; i < vol.getPlaceVols().size(); i++) {
            for (Reservation reservation : vol.getPlaceVols().get(i).getReservations()) {
                if (reservation.getPlaceVol().getId().equals(idTypeSiege)) {
                    nombrePlacesReserver += reservation.getNombrePlaces();
                }
            }
        }

        Integer nbProm = nombrePlacesPromotion - nombrePlacesReserver;

        if (nbProm <= 0) {
            return nbSiege * prixInitial;
        }

        if (nbProm >= nbSiege) {
            return nbSiege * (prixInitial - (prixInitial * promotionVal));
        }

        Integer siegeNonProm = nbSiege - nbProm;
        return (nbProm * (prixInitial - (prixInitial * promotionVal))) + (siegeNonProm * prixInitial);
    }


}
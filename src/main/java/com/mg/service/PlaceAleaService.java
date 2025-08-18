package com.mg.service;

import com.mg.dao.PlaceAleaDao;
import com.mg.model.PlaceAlea;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlaceAleaService extends AbstractService<PlaceAlea> {
    private final PlaceAleaDao placeAleaDao;

    public PlaceAleaService() {
        super(new PlaceAleaDao());
        this.placeAleaDao = (PlaceAleaDao) dao;
    }

    public List<PlaceAlea> findByVolAndTypeSiege(Integer idVol, Integer idTypeSiege) {
        return placeAleaDao.findByVolAndTypeSiege(idVol, idTypeSiege);
    }



    // update le nombre restant du dernier butoire par idvol idTypesiege
    public void refreshButoire(Date date) {
        List<PlaceAlea> placeAleas = placeAleaDao.findByDateBefore(date);

        // cree key volId-typeSiegeId
        Map<String, List<PlaceAlea>> grouped = placeAleas.stream()
                .collect(Collectors.groupingBy(
                        pa -> pa.getVol().getId() + "-" + pa.getTypeSiege().getId()
                ));

        System.out.println("Nombre de groupes : " + grouped.size());

        for (Map.Entry<String, List<PlaceAlea>> entry : grouped.entrySet()) {
            List<PlaceAlea> group = entry.getValue();
            
            group.sort(Comparator.comparing(PlaceAlea::getDateprom));
            int cumul = 0;
            // get last et ajouter le cumul des restants
            for (int i = 0; i < group.size() - 1; i++) {
                PlaceAlea pa = group.get(i);
                cumul += pa.getRestant();
                pa.setRestant(0);
                placeAleaDao.update(pa);

            }
            PlaceAlea last = group.get(group.size() - 1);
            last.setRestant(last.getRestant() + cumul);
            placeAleaDao.update(last);
        }
    }

    public List<PlaceAlea> findAll() {
        return placeAleaDao.findAll(PlaceAlea.class);
    }

    public List<PlaceAlea> findByVol(Integer volId) {
        return placeAleaDao.findByVol(volId);
    }

    public List<PlaceAlea> findByTypeSiege(Integer typeSiegeId) {
        return placeAleaDao.findByTypeSiege(typeSiegeId);
    }

    public List<PlaceAlea> findByDateRange(Date dateDebut, Date dateFin) {
        return placeAleaDao.findByDateRange(dateDebut, dateFin);
    }

    public List<PlaceAlea> searchPlacesAlea(Integer volId, Integer typeSiegeId,
                                            Date dateDebut, Date dateFin,
                                            Double prixMin, Double prixMax) {
        return placeAleaDao.searchWithFilters(volId, typeSiegeId, dateDebut, dateFin, prixMin, prixMax);
    }

}

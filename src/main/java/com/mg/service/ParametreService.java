package com.mg.service;

import com.mg.dao.ParametreDAO;
import com.mg.model.Parametre;

public class ParametreService extends AbstractService<Parametre> {
    private final ParametreDAO parametreDAO;

    public ParametreService() {
        super(new ParametreDAO());
        this.parametreDAO = (ParametreDAO) dao;
    }

    public Parametre getParametres() {
        Parametre parametre = parametreDAO.findFirst();
        if (parametre == null) {
            parametre = new Parametre();
            parametre.setHeuresMinimumReservation(24); // Default value
            parametre.setHeuresMinimumAnnulation(48); // Default value
            parametreDAO.save(parametre);
        }
        return parametre;
    }

    public void updateParametres(Integer heuresMinimumReservation, Integer heuresMinimumAnnulation) {
        Parametre parametre = parametreDAO.findFirst();
        if (parametre == null) {
            parametre = new Parametre();
        }

        parametre.setHeuresMinimumReservation(heuresMinimumReservation);
        parametre.setHeuresMinimumAnnulation(heuresMinimumAnnulation);

        if (parametre.getId() == null) {
            parametreDAO.save(parametre);
        } else {
            parametreDAO.update(parametre);
        }
    }
}
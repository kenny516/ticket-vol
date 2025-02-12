package com.mg.service;

import com.mg.dao.TypeSiegeDAO;
import com.mg.model.TypeSiege;
import java.util.List;

public class TypeSiegeService extends AbstractService<TypeSiege> {
    private final TypeSiegeDAO typeSiegeDAO;

    public TypeSiegeService() {
        super(new TypeSiegeDAO());
        this.typeSiegeDAO = (TypeSiegeDAO) dao;
    }

    public List<TypeSiege> findByAvion(Integer avionId) {
        return typeSiegeDAO.findByAvion(avionId);
    }

    public boolean hasAvailableSeats(Integer typeSiegeId, Integer volId, int requiredSeats) {
        return typeSiegeDAO.hasAvailableSeats(typeSiegeId, volId, requiredSeats);
    }
}
package com.mg.dao;

import com.mg.model.PlaceVol;
import com.mg.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class PlaceVolDAO extends BaseDao<PlaceVol> {

    public PlaceVolDAO() {
        super(PlaceVol.class);
    }



    // add function to find placeVol by idVol and place
    public PlaceVol findByIdVolAnIdPlace(Integer idVol, Integer idPlace) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM PlaceVol pv WHERE pv.vol.id = :idVol AND pv.place.id = :idPlace";
            return session.createQuery(hql, PlaceVol.class)
                    .setParameter("idVol", idVol)
                    .setParameter("idPlace", idPlace)
                    .uniqueResult();
        }
    }

    public List<PlaceVol> findByIdVol(Integer idVol) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM PlaceVol pv WHERE pv.vol.id = :idVol";
            return session.createQuery(hql, PlaceVol.class)
                    .setParameter("idVol", idVol).list();
        }
    }

}

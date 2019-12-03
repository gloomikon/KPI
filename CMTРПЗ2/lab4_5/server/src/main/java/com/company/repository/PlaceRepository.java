package com.company.repository;
import com.company.entity.Place;
import java.util.List;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class PlaceRepository {
    @PersistenceContext
    public EntityManager em;

    public List<Place> findByPlaneId(int planeId) {
        TypedQuery<Place> query = em.createQuery(
                "SELECT p FROM Place p WHERE p.planeId = :planeId", Place.class);
        return query.setParameter("planeId", planeId).getResultList();
    }

    public void save(Place place) {
        em.getTransaction().begin();
        em.merge(place);
        em.getTransaction().commit();
    }
}

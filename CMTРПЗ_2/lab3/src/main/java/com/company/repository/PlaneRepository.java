package com.company.repository;
import com.company.entity.Plane;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PlaneRepository {
    @PersistenceContext
    public EntityManager em;

    public Plane add(Plane plane) {
        em.getTransaction().begin();
        Plane planeFromDB = em.merge(plane);
        em.getTransaction().commit();
        return planeFromDB;
    }

    public void delete(int id){
        em.getTransaction().begin();
        em.remove(findById(id));
        em.getTransaction().commit();
    }

    public Plane findById(int id){
        return em.find(Plane.class, id);
    }

    public void update(Plane plane){
        em.getTransaction().begin();
        em.merge(plane);
        em.getTransaction().commit();
    }

    public List<Plane> findAll(){
        return em.createQuery("SELECT p from Plane p", Plane.class)
                .getResultList();
    }
}

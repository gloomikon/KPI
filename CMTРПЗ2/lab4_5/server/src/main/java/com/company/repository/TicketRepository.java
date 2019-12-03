package com.company.repository;

import com.company.entity.TicketUser;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class TicketRepository {
    @PersistenceContext
    public EntityManager em;

    public List<TicketUser> getUserTickets(Long customerId) {
        List<TicketUser> tl = new ArrayList<>();
        List<Object[]> results = em.createQuery(
                "SELECT p.id, pl.name, pl.date, p.name FROM Place p, Plane pl WHERE p.customerId = :customerId and p.planeId = pl.id", Object[].class)
                .setParameter("customerId", customerId).getResultList();
        for (Object[] row : results) {
            TicketUser t = new TicketUser();
            t.setId((Integer) row[0]);
            t.setPlaneName((String)row[1]);
            t.setDate((Date)row[2]);
            t.setPlaceName((String)row[3]);

            tl.add(t);
            System.out.println(t);
        }
        return tl;
    }
}

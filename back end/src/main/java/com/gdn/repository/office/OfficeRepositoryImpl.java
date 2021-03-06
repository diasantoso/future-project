package com.gdn.repository.office;

import com.gdn.model.Office;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Dias on 3/30/2017.
 */
public class OfficeRepositoryImpl implements OfficeRepositoryCustom {
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public Office deleteOffice(String id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Office result = em.find(Office.class, id);

        if(result.getStatus()==1)
            result.setStatus(0);
        else if(result.getStatus()==0)
            result.setStatus(1);

        em.getTransaction().commit();

        return result;
    }

    @Override
    public List<Office> showActiveOffice() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Office> result = em.createNativeQuery(
                "SELECT * FROM office WHERE status=1", Office.class).getResultList();
        return result;
    }

    @Override
    public Office showOneOffice(String id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Office result = (Office) em.createNativeQuery(
                "SELECT * FROM office WHERE status=1 AND id_office = '" + id +"' ", Office.class).getSingleResult();
        //Office result = em.find(Office.class, id);
        return result;
    }
}

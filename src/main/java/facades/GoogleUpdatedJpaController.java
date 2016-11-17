/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.GoogleUpdated;
import facades.exceptions.NonexistentEntityException;
import facades.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author rasmus
 */
public class GoogleUpdatedJpaController implements Serializable {

    public GoogleUpdatedJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GoogleUpdated googleUpdated) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(googleUpdated);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGoogleUpdated(googleUpdated.getGoogleUpdated()) != null) {
                throw new PreexistingEntityException("GoogleUpdated " + googleUpdated + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GoogleUpdated googleUpdated) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            googleUpdated = em.merge(googleUpdated);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Date id = googleUpdated.getGoogleUpdated();
                if (findGoogleUpdated(id) == null) {
                    throw new NonexistentEntityException("The googleUpdated with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Date id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GoogleUpdated googleUpdated;
            try {
                googleUpdated = em.getReference(GoogleUpdated.class, id);
                googleUpdated.getGoogleUpdated();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The googleUpdated with id " + id + " no longer exists.", enfe);
            }
            em.remove(googleUpdated);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GoogleUpdated> findGoogleUpdatedEntities() {
        return findGoogleUpdatedEntities(true, -1, -1);
    }

    public List<GoogleUpdated> findGoogleUpdatedEntities(int maxResults, int firstResult) {
        return findGoogleUpdatedEntities(false, maxResults, firstResult);
    }

    private List<GoogleUpdated> findGoogleUpdatedEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GoogleUpdated.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public GoogleUpdated findGoogleUpdated(Date id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GoogleUpdated.class, id);
        } finally {
            em.close();
        }
    }

    public int getGoogleUpdatedCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GoogleUpdated> rt = cq.from(GoogleUpdated.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

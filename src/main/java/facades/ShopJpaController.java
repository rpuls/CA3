/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Picture;
import entity.Shop;
import entity.User;
import facades.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author TimmosQuadros
 */
public class ShopJpaController implements Serializable {

    public ShopJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Shop create(Shop shop) {
        EntityManager em = getEntityManager();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(shop);
            em.getTransaction().commit();
        } finally {
//            if (em != null) {
            em.close();
//            }
        }
        return shop;
    }

    public void edit(Shop shop) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            shop = em.merge(shop);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = shop.getId();
                if (findShop(id) == null) {
                    throw new NonexistentEntityException("The shop with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Shop shop;
            try {
                shop = em.getReference(Shop.class, id);
                shop.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The shop with id " + id + " no longer exists.", enfe);
            }
            em.remove(shop);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Shop> findShopEntities() {
        return findShopEntities(true, -1, -1);
    }

    public List<Shop> findShopEntities(int maxResults, int firstResult) {
        return findShopEntities(false, maxResults, firstResult);
    }

    private List<Shop> findShopEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Shop.class));
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

    public Shop findShop(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Shop.class, id);
        } finally {
            em.close();
        }
    }

    public List<Shop> findShopByUser(String user) {
        EntityManager em = getEntityManager();
        try {
            String query = "SELECT s FROM Shop s WHERE s.user.userName=:user";
            Query q = em.createQuery(query);
            q.setParameter("user", user);
            return q.getResultList();

        } finally {
            em.close();
        }
    }

    public int getShopCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Shop> rt = cq.from(Shop.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public void addShopWithUser(User user, Shop shop) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(shop);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("ROLLBACK!!!!" + e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void setUserToAShop(User user, int shopId) {
        EntityManager em = getEntityManager();
        try {
            Shop shop = em.find(Shop.class, shopId);

            em.getTransaction().begin();
            shop.setUser(user);
            shop.setUsername(user.getUserName());
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("ROLLBACK!!!!" + e.getMessage());
            em.getTransaction().rollback();

        } finally {
            em.close();
        }
    }

    public void addFiles(int shopId, List<Picture> images) {
        EntityManager em = getEntityManager();
        try {
            if (images == null) {
                System.out.println("THIS IS NULL");
            } else {
                System.out.println("NOT NULL");
            }
            for (Picture image : images) {
                em.getTransaction().begin();
                Shop shop = findShop(shopId);
                image.setShop(shop);
                em.persist(image);
                System.out.println("IMAGE:" + image.getName());
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.println("ROLLBACK!!!!" + e.getMessage() + e.toString());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public List<Picture> getFilesByShop(int shopId) {
        EntityManager em = getEntityManager();
        try {
            String query = "SELECT p FROM Picture p WHERE p.shop.id=:shopId";
            Query q = em.createQuery(query);
            q.setParameter("shopId", shopId);
            return q.getResultList();

        } finally {
            em.close();
        }
    }

}

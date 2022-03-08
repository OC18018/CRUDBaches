/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package occ.ues.edu.sv.crudbaches.control;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import occ.ues.edu.sv.crudbaches.entity.Objeto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import occ.ues.edu.sv.crudbaches.control.exceptions.NonexistentEntityException;
import occ.ues.edu.sv.crudbaches.entity.TipoObjeto;

/**
 *
 * @author armandop444
 */
public class TipoObjetoBean implements Serializable {

    public TipoObjetoBean(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public TipoObjetoBean() {
        this.emf = Persistence.createEntityManagerFactory("baches");;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(TipoObjeto tipoObjeto) {
        if (tipoObjeto.getObjetoList() == null) {
            tipoObjeto.setObjetoList(new ArrayList<Objeto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Objeto> attachedObjetoList = new ArrayList<Objeto>();
            for (Objeto objetoListObjetoToAttach : tipoObjeto.getObjetoList()) {
                objetoListObjetoToAttach = em.getReference(objetoListObjetoToAttach.getClass(), objetoListObjetoToAttach.getIdObjeto());
                attachedObjetoList.add(objetoListObjetoToAttach);
            }
            tipoObjeto.setObjetoList(attachedObjetoList);
            em.persist(tipoObjeto);
            for (Objeto objetoListObjeto : tipoObjeto.getObjetoList()) {
                TipoObjeto oldIdTipoObjetoOfObjetoListObjeto = objetoListObjeto.getIdTipoObjeto();
                objetoListObjeto.setIdTipoObjeto(tipoObjeto);
                objetoListObjeto = em.merge(objetoListObjeto);
                if (oldIdTipoObjetoOfObjetoListObjeto != null) {
                    oldIdTipoObjetoOfObjetoListObjeto.getObjetoList().remove(objetoListObjeto);
                    oldIdTipoObjetoOfObjetoListObjeto = em.merge(oldIdTipoObjetoOfObjetoListObjeto);
                }
            }
            em.getTransaction().commit();
            return true;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoObjeto tipoObjeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoObjeto persistentTipoObjeto = em.find(TipoObjeto.class, tipoObjeto.getIdTipoObjeto());
            List<Objeto> objetoListOld = persistentTipoObjeto.getObjetoList();
            List<Objeto> objetoListNew = tipoObjeto.getObjetoList();
            List<Objeto> attachedObjetoListNew = new ArrayList<Objeto>();
            for (Objeto objetoListNewObjetoToAttach : objetoListNew) {
                objetoListNewObjetoToAttach = em.getReference(objetoListNewObjetoToAttach.getClass(), objetoListNewObjetoToAttach.getIdObjeto());
                attachedObjetoListNew.add(objetoListNewObjetoToAttach);
            }
            objetoListNew = attachedObjetoListNew;
            tipoObjeto.setObjetoList(objetoListNew);
            tipoObjeto = em.merge(tipoObjeto);
            for (Objeto objetoListOldObjeto : objetoListOld) {
                if (!objetoListNew.contains(objetoListOldObjeto)) {
                    objetoListOldObjeto.setIdTipoObjeto(null);
                    objetoListOldObjeto = em.merge(objetoListOldObjeto);
                }
            }
            for (Objeto objetoListNewObjeto : objetoListNew) {
                if (!objetoListOld.contains(objetoListNewObjeto)) {
                    TipoObjeto oldIdTipoObjetoOfObjetoListNewObjeto = objetoListNewObjeto.getIdTipoObjeto();
                    objetoListNewObjeto.setIdTipoObjeto(tipoObjeto);
                    objetoListNewObjeto = em.merge(objetoListNewObjeto);
                    if (oldIdTipoObjetoOfObjetoListNewObjeto != null && !oldIdTipoObjetoOfObjetoListNewObjeto.equals(tipoObjeto)) {
                        oldIdTipoObjetoOfObjetoListNewObjeto.getObjetoList().remove(objetoListNewObjeto);
                        oldIdTipoObjetoOfObjetoListNewObjeto = em.merge(oldIdTipoObjetoOfObjetoListNewObjeto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoObjeto.getIdTipoObjeto();
                if (findTipoObjeto(id) == null) {
                    throw new NonexistentEntityException("The tipoObjeto with id " + id + " no longer exists.");
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
            TipoObjeto tipoObjeto;
            try {
                tipoObjeto = em.getReference(TipoObjeto.class, id);
                tipoObjeto.getIdTipoObjeto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoObjeto with id " + id + " no longer exists.", enfe);
            }
            List<Objeto> objetoList = tipoObjeto.getObjetoList();
            for (Objeto objetoListObjeto : objetoList) {
                objetoListObjeto.setIdTipoObjeto(null);
                objetoListObjeto = em.merge(objetoListObjeto);
            }
            em.remove(tipoObjeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoObjeto> findTipoObjetoEntities() {
        return findTipoObjetoEntities(true, -1, -1);
    }

    public List<TipoObjeto> findTipoObjetoEntities(int maxResults, int firstResult) {
        return findTipoObjetoEntities(false, maxResults, firstResult);
    }

    private List<TipoObjeto> findTipoObjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoObjeto.class));
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

    public TipoObjeto findTipoObjeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoObjeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoObjetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoObjeto> rt = cq.from(TipoObjeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

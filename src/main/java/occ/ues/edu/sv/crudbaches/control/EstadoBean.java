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
import occ.ues.edu.sv.crudbaches.entity.ObjetoEstado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import occ.ues.edu.sv.crudbaches.control.exceptions.NonexistentEntityException;
import occ.ues.edu.sv.crudbaches.entity.Estado;

/**
 *
 * @author armandop444
 */
public class EstadoBean implements Serializable {

    public EstadoBean(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EstadoBean() {
        this.emf = Persistence.createEntityManagerFactory("baches");;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Estado estado) {
        if (estado.getObjetoEstadoList() == null) {
            estado.setObjetoEstadoList(new ArrayList<ObjetoEstado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ObjetoEstado> attachedObjetoEstadoList = new ArrayList<ObjetoEstado>();
            for (ObjetoEstado objetoEstadoListObjetoEstadoToAttach : estado.getObjetoEstadoList()) {
                objetoEstadoListObjetoEstadoToAttach = em.getReference(objetoEstadoListObjetoEstadoToAttach.getClass(), objetoEstadoListObjetoEstadoToAttach.getIdObjetoEstado());
                attachedObjetoEstadoList.add(objetoEstadoListObjetoEstadoToAttach);
            }
            estado.setObjetoEstadoList(attachedObjetoEstadoList);
            em.persist(estado);
            for (ObjetoEstado objetoEstadoListObjetoEstado : estado.getObjetoEstadoList()) {
                Estado oldIdEstadoOfObjetoEstadoListObjetoEstado = objetoEstadoListObjetoEstado.getIdEstado();
                objetoEstadoListObjetoEstado.setIdEstado(estado);
                objetoEstadoListObjetoEstado = em.merge(objetoEstadoListObjetoEstado);
                if (oldIdEstadoOfObjetoEstadoListObjetoEstado != null) {
                    oldIdEstadoOfObjetoEstadoListObjetoEstado.getObjetoEstadoList().remove(objetoEstadoListObjetoEstado);
                    oldIdEstadoOfObjetoEstadoListObjetoEstado = em.merge(oldIdEstadoOfObjetoEstadoListObjetoEstado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
                return true;
            }
        }
        return false;
    }

    public void edit(Estado estado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getIdEstado());
            List<ObjetoEstado> objetoEstadoListOld = persistentEstado.getObjetoEstadoList();
            List<ObjetoEstado> objetoEstadoListNew = estado.getObjetoEstadoList();
            List<ObjetoEstado> attachedObjetoEstadoListNew = new ArrayList<ObjetoEstado>();
            for (ObjetoEstado objetoEstadoListNewObjetoEstadoToAttach : objetoEstadoListNew) {
                objetoEstadoListNewObjetoEstadoToAttach = em.getReference(objetoEstadoListNewObjetoEstadoToAttach.getClass(), objetoEstadoListNewObjetoEstadoToAttach.getIdObjetoEstado());
                attachedObjetoEstadoListNew.add(objetoEstadoListNewObjetoEstadoToAttach);
            }
            objetoEstadoListNew = attachedObjetoEstadoListNew;
            estado.setObjetoEstadoList(objetoEstadoListNew);
            estado = em.merge(estado);
            for (ObjetoEstado objetoEstadoListOldObjetoEstado : objetoEstadoListOld) {
                if (!objetoEstadoListNew.contains(objetoEstadoListOldObjetoEstado)) {
                    objetoEstadoListOldObjetoEstado.setIdEstado(null);
                    objetoEstadoListOldObjetoEstado = em.merge(objetoEstadoListOldObjetoEstado);
                }
            }
            for (ObjetoEstado objetoEstadoListNewObjetoEstado : objetoEstadoListNew) {
                if (!objetoEstadoListOld.contains(objetoEstadoListNewObjetoEstado)) {
                    Estado oldIdEstadoOfObjetoEstadoListNewObjetoEstado = objetoEstadoListNewObjetoEstado.getIdEstado();
                    objetoEstadoListNewObjetoEstado.setIdEstado(estado);
                    objetoEstadoListNewObjetoEstado = em.merge(objetoEstadoListNewObjetoEstado);
                    if (oldIdEstadoOfObjetoEstadoListNewObjetoEstado != null && !oldIdEstadoOfObjetoEstadoListNewObjetoEstado.equals(estado)) {
                        oldIdEstadoOfObjetoEstadoListNewObjetoEstado.getObjetoEstadoList().remove(objetoEstadoListNewObjetoEstado);
                        oldIdEstadoOfObjetoEstadoListNewObjetoEstado = em.merge(oldIdEstadoOfObjetoEstadoListNewObjetoEstado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estado.getIdEstado();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getIdEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            List<ObjetoEstado> objetoEstadoList = estado.getObjetoEstadoList();
            for (ObjetoEstado objetoEstadoListObjetoEstado : objetoEstadoList) {
                objetoEstadoListObjetoEstado.setIdEstado(null);
                objetoEstadoListObjetoEstado = em.merge(objetoEstadoListObjetoEstado);
            }
            em.remove(estado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

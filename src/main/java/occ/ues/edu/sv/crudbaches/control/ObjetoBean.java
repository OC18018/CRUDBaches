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
import occ.ues.edu.sv.crudbaches.entity.TipoObjeto;
import occ.ues.edu.sv.crudbaches.entity.ObjetoEstado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import occ.ues.edu.sv.crudbaches.control.exceptions.NonexistentEntityException;
import occ.ues.edu.sv.crudbaches.entity.Objeto;

/**
 *
 * @author armandop444
 */
public class ObjetoBean implements Serializable {

    public ObjetoBean(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ObjetoBean() {
        this.emf = Persistence.createEntityManagerFactory("baches");;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Objeto objeto) {
        if (objeto.getObjetoEstadoList() == null) {
            objeto.setObjetoEstadoList(new ArrayList<ObjetoEstado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoObjeto idTipoObjeto = objeto.getIdTipoObjeto();
            if (idTipoObjeto != null) {
                idTipoObjeto = em.getReference(idTipoObjeto.getClass(), idTipoObjeto.getIdTipoObjeto());
                objeto.setIdTipoObjeto(idTipoObjeto);
            }
            List<ObjetoEstado> attachedObjetoEstadoList = new ArrayList<ObjetoEstado>();
            for (ObjetoEstado objetoEstadoListObjetoEstadoToAttach : objeto.getObjetoEstadoList()) {
                objetoEstadoListObjetoEstadoToAttach = em.getReference(objetoEstadoListObjetoEstadoToAttach.getClass(), objetoEstadoListObjetoEstadoToAttach.getIdObjetoEstado());
                attachedObjetoEstadoList.add(objetoEstadoListObjetoEstadoToAttach);
            }
            objeto.setObjetoEstadoList(attachedObjetoEstadoList);
            em.persist(objeto);
            if (idTipoObjeto != null) {
                idTipoObjeto.getObjetoList().add(objeto);
                idTipoObjeto = em.merge(idTipoObjeto);
            }
            for (ObjetoEstado objetoEstadoListObjetoEstado : objeto.getObjetoEstadoList()) {
                Objeto oldIdObjetoOfObjetoEstadoListObjetoEstado = objetoEstadoListObjetoEstado.getIdObjeto();
                objetoEstadoListObjetoEstado.setIdObjeto(objeto);
                objetoEstadoListObjetoEstado = em.merge(objetoEstadoListObjetoEstado);
                if (oldIdObjetoOfObjetoEstadoListObjetoEstado != null) {
                    oldIdObjetoOfObjetoEstadoListObjetoEstado.getObjetoEstadoList().remove(objetoEstadoListObjetoEstado);
                    oldIdObjetoOfObjetoEstadoListObjetoEstado = em.merge(oldIdObjetoOfObjetoEstadoListObjetoEstado);
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

    public void edit(Objeto objeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objeto persistentObjeto = em.find(Objeto.class, objeto.getIdObjeto());
            TipoObjeto idTipoObjetoOld = persistentObjeto.getIdTipoObjeto();
            TipoObjeto idTipoObjetoNew = objeto.getIdTipoObjeto();
            List<ObjetoEstado> objetoEstadoListOld = persistentObjeto.getObjetoEstadoList();
            List<ObjetoEstado> objetoEstadoListNew = objeto.getObjetoEstadoList();
            if (idTipoObjetoNew != null) {
                idTipoObjetoNew = em.getReference(idTipoObjetoNew.getClass(), idTipoObjetoNew.getIdTipoObjeto());
                objeto.setIdTipoObjeto(idTipoObjetoNew);
            }
            List<ObjetoEstado> attachedObjetoEstadoListNew = new ArrayList<ObjetoEstado>();
            for (ObjetoEstado objetoEstadoListNewObjetoEstadoToAttach : objetoEstadoListNew) {
                objetoEstadoListNewObjetoEstadoToAttach = em.getReference(objetoEstadoListNewObjetoEstadoToAttach.getClass(), objetoEstadoListNewObjetoEstadoToAttach.getIdObjetoEstado());
                attachedObjetoEstadoListNew.add(objetoEstadoListNewObjetoEstadoToAttach);
            }
            objetoEstadoListNew = attachedObjetoEstadoListNew;
            objeto.setObjetoEstadoList(objetoEstadoListNew);
            objeto = em.merge(objeto);
            if (idTipoObjetoOld != null && !idTipoObjetoOld.equals(idTipoObjetoNew)) {
                idTipoObjetoOld.getObjetoList().remove(objeto);
                idTipoObjetoOld = em.merge(idTipoObjetoOld);
            }
            if (idTipoObjetoNew != null && !idTipoObjetoNew.equals(idTipoObjetoOld)) {
                idTipoObjetoNew.getObjetoList().add(objeto);
                idTipoObjetoNew = em.merge(idTipoObjetoNew);
            }
            for (ObjetoEstado objetoEstadoListOldObjetoEstado : objetoEstadoListOld) {
                if (!objetoEstadoListNew.contains(objetoEstadoListOldObjetoEstado)) {
                    objetoEstadoListOldObjetoEstado.setIdObjeto(null);
                    objetoEstadoListOldObjetoEstado = em.merge(objetoEstadoListOldObjetoEstado);
                }
            }
            for (ObjetoEstado objetoEstadoListNewObjetoEstado : objetoEstadoListNew) {
                if (!objetoEstadoListOld.contains(objetoEstadoListNewObjetoEstado)) {
                    Objeto oldIdObjetoOfObjetoEstadoListNewObjetoEstado = objetoEstadoListNewObjetoEstado.getIdObjeto();
                    objetoEstadoListNewObjetoEstado.setIdObjeto(objeto);
                    objetoEstadoListNewObjetoEstado = em.merge(objetoEstadoListNewObjetoEstado);
                    if (oldIdObjetoOfObjetoEstadoListNewObjetoEstado != null && !oldIdObjetoOfObjetoEstadoListNewObjetoEstado.equals(objeto)) {
                        oldIdObjetoOfObjetoEstadoListNewObjetoEstado.getObjetoEstadoList().remove(objetoEstadoListNewObjetoEstado);
                        oldIdObjetoOfObjetoEstadoListNewObjetoEstado = em.merge(oldIdObjetoOfObjetoEstadoListNewObjetoEstado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = objeto.getIdObjeto();
                if (findObjeto(id) == null) {
                    throw new NonexistentEntityException("The objeto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objeto objeto;
            try {
                objeto = em.getReference(Objeto.class, id);
                objeto.getIdObjeto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The objeto with id " + id + " no longer exists.", enfe);
            }
            TipoObjeto idTipoObjeto = objeto.getIdTipoObjeto();
            if (idTipoObjeto != null) {
                idTipoObjeto.getObjetoList().remove(objeto);
                idTipoObjeto = em.merge(idTipoObjeto);
            }
            List<ObjetoEstado> objetoEstadoList = objeto.getObjetoEstadoList();
            for (ObjetoEstado objetoEstadoListObjetoEstado : objetoEstadoList) {
                objetoEstadoListObjetoEstado.setIdObjeto(null);
                objetoEstadoListObjetoEstado = em.merge(objetoEstadoListObjetoEstado);
            }
            em.remove(objeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Objeto> findObjetoEntities() {
        return findObjetoEntities(true, -1, -1);
    }

    public List<Objeto> findObjetoEntities(int maxResults, int firstResult) {
        return findObjetoEntities(false, maxResults, firstResult);
    }

    private List<Objeto> findObjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Objeto.class));
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

    public Objeto findObjeto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Objeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getObjetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Objeto> rt = cq.from(Objeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

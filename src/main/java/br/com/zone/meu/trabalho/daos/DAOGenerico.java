package br.com.zone.meu.trabalho.daos;

import br.com.duosoftware.esicduo.manager.daos.transactional.Transacional;
import br.com.zone.meu.trabalho.entidades.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

/**
 *
 * @author daniel
 * @param <T>
 */
public class DAOGenerico<T extends BaseEntity> implements Serializable {

    @Inject
    private EntityManager em;

    private final Class<T> entityClass;

    public DAOGenerico(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transacional
    public void salvar(T t) {
        if (t.getId() == null) {
            em.persist(t);
        } else {
            em.merge(t);
        }
    }

    @Transacional
    public void excluir(BaseEntity entity) {
        T entityToBeRemoved = em.find(entityClass, entity.getId());
        em.remove(entityToBeRemoved);
    }

    public T buscarPorId(Object id) {
        T resultado = em.find(entityClass, id);
        return resultado;
    }

    @SuppressWarnings("unchecked")
    protected Serializable buscarUmResultado(String namedQuery, Object... params) {
        Serializable result = null;
        try {
            Query q = em.createNamedQuery(namedQuery);
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i + 1, params[i]);
            }
            result = (Serializable) q.getSingleResult();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "Nenhum resultado retornado pela consulta");
        }
        return result;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> buscarTodos() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.orderBy(cb.desc(root.get("id")));
        cq.select(root);
        List<T> resultado = em.createQuery(cq).getResultList();
        return resultado;
    }

    protected List<T> buscar(String namedQuery, Object... params) {
        Query q = em.createNamedQuery(namedQuery);
        for (int i = 0; i < params.length; i++) {
            q.setParameter(i + 1, params[i]);
        }
        List<T> resultado = q.getResultList();
        return resultado;
    }

    public List<T> buscarComLimite(int paginaAtual, int tamanhoPagina) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.orderBy(cb.desc(root.get("id")));
        cq.select(root);
        TypedQuery typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult(paginaAtual);
        typedQuery.setMaxResults(tamanhoPagina);
        return typedQuery.getResultList();
    }

    public Long count() {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(entityClass)));
        return em.createQuery(cq).getSingleResult();
    }

}

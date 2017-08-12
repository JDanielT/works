package br.com.zone.meu.trabalho.daos;

import br.com.zone.meu.trabalho.entidades.Empresa;
import br.com.zone.meu.trabalho.entidades.EmpresaReputacao;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author daniel
 */
public class EmpresaDAO extends DAOGenerico<Empresa> {

    public EmpresaDAO() {
        super(Empresa.class);
    }
    
    public List<EmpresaReputacao> buscarMelhoresEmpresas(){
        Query q = super.getEntityManager().createNamedQuery(Empresa.BUCAR_MELHORES_EMPRESAS);
        q.setFirstResult(0);
        q.setMaxResults(4);
        return q.getResultList();
    }
    
    public List<EmpresaReputacao> buscarPioresEmpresas(){
        Query q = super.getEntityManager().createNamedQuery(Empresa.BUCAR_PIORES_EMPRESAS);
        q.setFirstResult(0);
        q.setMaxResults(4);
        return q.getResultList();
    }
    
}

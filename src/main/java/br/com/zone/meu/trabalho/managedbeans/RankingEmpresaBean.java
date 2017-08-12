package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.daos.EmpresaDAO;
import br.com.zone.meu.trabalho.entidades.EmpresaReputacao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author daniel
 */

@Named
@ViewScoped
public class RankingEmpresaBean implements Serializable {
    
    @Inject
    private EmpresaDAO empresaDAO;
    
    private List<EmpresaReputacao> melhoresEmpresas;
    private List<EmpresaReputacao> pioresEmpresas;

    @PostConstruct
    public void init() {
        melhoresEmpresas = empresaDAO.buscarMelhoresEmpresas();
        pioresEmpresas = empresaDAO.buscarPioresEmpresas();
    }

    public List<EmpresaReputacao> getMelhoresEmpresas() {
        return melhoresEmpresas;
    }

    public List<EmpresaReputacao> getPioresEmpresas() {
        return pioresEmpresas;
    }
    
}

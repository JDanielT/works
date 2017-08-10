package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.daos.EmpresaDAO;
import br.com.zone.meu.trabalho.daos.VotoDAO;
import br.com.zone.meu.trabalho.entidades.Empresa;
import br.com.zone.meu.trabalho.util.ParameterUtil;
import java.io.Serializable;
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
public class RatingEmpresaBean implements Serializable {

    @Inject
    private VotoDAO votoDAO;
    
    @Inject
    private EmpresaDAO empresaDAO;
    private Empresa empresa;
    
    @PostConstruct
    public void init(){
        long id = 0;
        try{
            id = Long.parseLong(ParameterUtil.getRequestParameter("id"));
        }catch(NumberFormatException ex){}
        empresa = empresaDAO.buscarPorId(id);
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public Double buscarMediaVotos(){
        Double media = 0.0;
        if(empresa != null && empresa.getId() != null){
            media = votoDAO.buscarMediaVotosPorEmpresa(empresa);
        }
        return media;
    }
    
}


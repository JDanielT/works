package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.daos.EmpresaDAO;
import br.com.zone.meu.trabalho.daos.PostagemDAO;
import br.com.zone.meu.trabalho.entidades.Empresa;
import br.com.zone.meu.trabalho.entidades.Postagem;
import br.com.zone.meu.trabalho.entidades.TipoPostagem;
import br.com.zone.meu.trabalho.util.JSFUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author daniel
 */
@Named
@ViewScoped
public class PostagemBean extends AbstractBean<Postagem> {

    private final String PAG_FORM = "pages/postagens/postagem.xhtml";

    @Inject
    private PostagemDAO postagemDAO;

    @Inject
    private EmpresaDAO empresaDAO;

    @Inject
    private AcessoBean acessoBean;
    
    @Inject
    private Empresa empresa;
    
    public PostagemBean() {
        super(Postagem.class);
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    protected PostagemDAO getDAO() {
        return postagemDAO;
    }

    @Override
    public String irParaPaginaCadastroEdicao() {
        return PAG_FORM + "?faces-redirect=true";
    }

    public TipoPostagem[] getTipos() {
        return TipoPostagem.values();
    }

    public List<String> getEmpresas() {
        List<String> empresas = new ArrayList<>();
        empresaDAO.listarTodos().forEach((e) -> {
            empresas.add(e.toAutoCompleteString());
        });
        return empresas;
    }

    public void visualizar(){
        super.preCadastro();
    }
    
    @Override
    public void preCadastro() {
        super.preCadastro();
        if (getEntity().getId() == null) {
            getEntity().setEmpresa(new Empresa());
            getEntity().setDataPostagem(LocalDate.now());
            getEntity().setUsuario(acessoBean.getUsuarioLogado());
        } else {
            if (!getEntity().getUsuario().equals(acessoBean.getUsuarioLogado())) {
                this.redirect(JSFUtil.getExternalContext().getRequestContextPath() + "/403.xhtml");
            }
        }
    }
    
    public void buscar(){
        if(empresa != null && empresa.getId() != null){
            empresa = empresaDAO.buscarPorId(empresa.getId());
            setItens(postagemDAO.buscarPostagensPorEmpresa(empresa));
        }
    }
    
    public void limparBusca(){
        empresa = new Empresa();
        super.setItens(null);
        super.getItens();
    }
    
    @Override
    public String salvar() {
        super.salvar();
        this.redirect(JSFUtil.getExternalContext().getRequestContextPath() + "/home.xhtml");
        return null;
    }

    private void redirect(String pagina){
        try {
            JSFUtil.getExternalContext().redirect(pagina);
        } catch (IOException ex) {
            Logger.getLogger(PostagemBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

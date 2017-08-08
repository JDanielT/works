package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.daos.EmpresaDAO;
import br.com.zone.meu.trabalho.daos.PostagemDAO;
import br.com.zone.meu.trabalho.entidades.Empresa;
import br.com.zone.meu.trabalho.entidades.Postagem;
import br.com.zone.meu.trabalho.entidades.TipoPostagem;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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

    public PostagemBean() {
        super(Postagem.class);
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

    @Override
    public void preCadastro() {
        super.preCadastro();
        getEntity().setEmpresa(new Empresa());
        getEntity().setDataPostagem(LocalDate.now());
        getEntity().setUsuario(acessoBean.getUsuarioLogado());
    }

    @Override
    public String salvar() {
        super.salvar();
        ExternalContext ec = FacesContext.getCurrentInstance()
        .getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath() + "/home.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PostagemBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}

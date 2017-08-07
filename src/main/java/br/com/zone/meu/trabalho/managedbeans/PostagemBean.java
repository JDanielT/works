package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.daos.PostagemDAO;
import br.com.zone.meu.trabalho.entidades.Postagem;
import br.com.zone.meu.trabalho.entidades.TipoPostagem;
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
    
    public TipoPostagem[] getTipos(){
        return TipoPostagem.values();
    }

}

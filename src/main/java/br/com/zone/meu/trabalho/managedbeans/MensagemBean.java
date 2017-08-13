package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.daos.MensagemDAO;
import br.com.zone.meu.trabalho.entidades.Mensagem;
import br.com.zone.meu.trabalho.util.MensagemUtil;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author daniel
 */
@Named
@ViewScoped
public class MensagemBean extends AbstractBean<Mensagem> {
    
    private final String PAG_FORM = "contato.xhtml";

    @Inject
    private MensagemDAO mensagemDAO;

    public MensagemBean() {
        super(Mensagem.class);
    }

    @Override
    protected MensagemDAO getDAO() {
        return mensagemDAO;
    }

    @Override
    public String irParaPaginaCadastroEdicao() {
        return PAG_FORM;
    }

    @Override
    public String salvar() {
        super.salvar();
        MensagemUtil.mensagemInformativa("Mensagem enviada, logo entraremos em contato", null);
        return null;
    }
    
}

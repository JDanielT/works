package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.daos.UsuarioDAO;
import br.com.zone.meu.trabalho.entidades.Usuario;
import br.com.zone.meu.trabalho.mail.ConfirmacaoCadastroService;
import br.com.zone.meu.trabalho.util.MensagemUtil;
import java.io.Serializable;
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
public class AutocadastroBean implements Serializable {

    @Inject
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;

    @Inject
    private ConfirmacaoCadastroService confirmacao;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void preCadastro() {
        usuario = new Usuario();
    }

    public void salvar() {

        try {
            
            usuarioDAO.salvar(usuario);
            
            new Thread(() -> {
                try {
                    confirmacao.enviarEmailDeConfirmacao(usuario);
                } catch (Exception ex) {
                    Logger.getLogger(AutocadastroBean.class.getName()).log(Level.SEVERE, null, ex);
                    MensagemUtil.mensagemDeErro("Ooops!", "Um problema ocorreu ao enviar o e-mail de confirmação");
                }
            }).start();

        } catch (Exception ex) {
            Logger.getLogger(AutocadastroBean.class.getName()).log(Level.SEVERE, null, ex);
            MensagemUtil.mensagemDeErro("Ooops!", "Um problema ocorreu ao persistir os dados");
        }

    }

}

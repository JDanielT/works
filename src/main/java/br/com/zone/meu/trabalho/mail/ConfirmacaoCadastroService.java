package br.com.zone.meu.trabalho.mail;

import br.com.zone.meu.trabalho.daos.ConfiguracaoDAO;
import br.com.zone.meu.trabalho.daos.TokenDAO;
import br.com.zone.meu.trabalho.entidades.Configuracao;
import br.com.zone.meu.trabalho.entidades.Token;
import br.com.zone.meu.trabalho.entidades.Usuario;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;

/**
 * @author daniel
 */
@ViewScoped
public class ConfirmacaoCadastroService implements Serializable {

    @Inject
    private TokenDAO tokenDAO;

    @Inject
    private ConfiguracaoDAO configuracaoDAO;

    public void enviarEmailDeConfirmacao(Usuario usuario) throws Exception {

        Configuracao configuracao = configuracaoDAO.getConfiguracao();

        if (configuracao == null) {
            throw new Exception("dados para envio de e-mail não foram cadastrados, contate o administrador");
        }

        Token token = new Token();
        token.setUsuario(usuario);

        String assunto = "CONFIRMAÇÃO DE CADASTRO - MEU TRABALHO";

        StringBuilder texto = new StringBuilder();
        texto.append("Olá <i>").append(usuario.getNome()).append("</i>, você efetuou seu ");
        texto.append("cadastro na ferramenta meu trabalho. <br/><br/>");
        texto.append("Porém, o seu cadastro está pendente de ativação.");
        texto.append("Para ativar sua conta clique no link abaixo: <br/>");
        texto.append(configuracao.getUrlSistema().concat("/activate?token=" + token.getToken() + "<br/><br/>"));
        texto.append("<br/><br/><center><font style=\"color: red\">e-mail gerado automaticamente, não o responda</font></center>");

        new Thread(() -> {
            try {
                SenderEmailService.send(assunto, texto.toString(), usuario.getEmail(), configuracao);
            } catch (Exception ex) {
                Logger.getLogger(ConfirmacaoCadastroService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

        tokenDAO.salvar(token);

    }

}

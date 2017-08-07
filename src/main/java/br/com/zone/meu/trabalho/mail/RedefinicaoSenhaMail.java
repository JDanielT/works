package br.com.zone.meu.trabalho.mail;

import br.com.zone.meu.trabalho.daos.ConfiguracaoDAO;
import br.com.zone.meu.trabalho.daos.TokenDAO;
import br.com.zone.meu.trabalho.entidades.Configuracao;
import br.com.zone.meu.trabalho.entidades.Token;
import br.com.zone.meu.trabalho.entidades.Usuario;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author daniel
 */
public class RedefinicaoSenhaMail implements Serializable {

    @Inject
    private ConfiguracaoDAO configuracaoDAO;

    @Inject
    private TokenDAO tokenDAO;

    public void enviarEmailDeRedefinicao(Usuario usuario) throws Exception {

        Configuracao configuracao = configuracaoDAO.getConfiguracao();

        if (configuracao == null) {
            throw new Exception("dados para envio de e-mail não foram cadastrados, contate o administrador");
        }

        Token token = new Token();
        token.setUsuario(usuario);

        String assunto = "INSTRUÇÕES PARA REDEFINIÇÃO DE SENHA - MEU TRABALHO";
        String url = configuracao.getUrlSistema();

        StringBuilder texto = new StringBuilder();
        texto.append("Olá <i>").append(usuario.getNome()).append("</i>, você solicitou instruções ");
        texto.append("para redefinir sua senha. <br/>");
        texto.append("Clique <a href=\"").append(url.concat("/redefinir-senha.xhtml?token=" + token.getToken())).append("\" >aqui</a> para configurar uma nova senha.<br/>");
        texto.append("<br/><br/>");
        texto.append("<br/><br/><center><font style=\"color: red\">e-mail gerado automaticamente, não o responda</font></center>");

        SenderEmailService.send(assunto, texto.toString(), usuario.getEmail(), configuracao);

        tokenDAO.salvar(token);

    }

}

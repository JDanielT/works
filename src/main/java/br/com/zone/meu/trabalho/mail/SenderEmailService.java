package br.com.zone.meu.trabalho.mail;

import br.com.zone.meu.trabalho.entidades.Configuracao;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author daniel
 */
public class SenderEmailService {
    
    public static void send(String subject, String text, String destinatario, Configuracao configuracao) throws Exception {
        
        if (configuracao == null) {
            throw new Exception("dados para envio de e-mail não foram cadastrados, contate o administrador");
        }
        
        String email = configuracao.getEmail();
        String senha = configuracao.getSenha();
        String host = configuracao.getServidor();
        String porta = configuracao.getPorta();
        
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", porta);
        props.put("mail.smtp.port", porta);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, senha);
            }
        });

        Message message = new MimeMessage(session);

        try {

            Address[] toUser = InternetAddress.parse(destinatario);

            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(subject);
            message.setContent(text, "text/html");

            Transport.send(message);

        } catch (AddressException ex) {
            Logger.getLogger(SenderEmailService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(SenderEmailService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

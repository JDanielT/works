package br.com.zone.meu.trabalho.auth;

import br.com.zone.meu.trabalho.daos.AuthConnectionFactory;
import br.com.zone.meu.trabalho.entidades.TipoPessoa;
import br.com.zone.meu.trabalho.entidades.TipoUsuario;
import br.com.zone.meu.trabalho.entidades.Usuario;
import br.com.zone.meu.trabalho.util.HashUtil;
import java.beans.PropertyVetoException;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class CustomLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler handler;
    private UserPrincipal userPrincipal;
    private RolePrincipal rolePrincipal;

    private String login;
    private List<String> userGroups;

    @Override
    public void initialize(Subject subject,
            CallbackHandler callbackHandler,
            Map<String, ?> sharedState,
            Map<String, ?> options) {

        handler = callbackHandler;
        this.subject = subject;
    }

    @Override
    public boolean login() throws LoginException {

        boolean resultado = false;

        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", true);

        try {
            handler.handle(callbacks);
        } catch (IOException | UnsupportedCallbackException ex) {
            Logger.getLogger(CustomLoginModule.class.getName()).log(Level.SEVERE, null, ex);
        }
        String name = ((NameCallback) callbacks[0]).getName();
        String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());

        Usuario usuario = this.buscarUsuarioPorEmailSenha(name, HashUtil.hash(password));

        if (usuario != null) {

            login = name;
            userGroups = new ArrayList<>();
            userGroups.add(usuario.getTipoUsuario().toString());

            resultado = true;

        }

        return resultado;

    }

    @Override
    public boolean commit() throws LoginException {

        userPrincipal = new UserPrincipal(login);
        subject.getPrincipals().add(userPrincipal);

        if (userGroups != null && userGroups.size() > 0) {
            for (String groupName : userGroups) {
                rolePrincipal = new RolePrincipal(groupName);
                subject.getPrincipals().add(rolePrincipal);
            }
        }

        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(userPrincipal);
        subject.getPrincipals().remove(rolePrincipal);
        return true;
    }

    private Usuario buscarUsuarioPorEmailSenha(String email, String senha) {

        Usuario usuario = null;

        try (Connection c = AuthConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = c.prepareStatement("SELECT * FROM usuarios u WHERE u.email = ? AND u.senha = ? AND u.ativo = true")) {

            ps.setString(1, email);
            ps.setString(2, senha);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setNome(rs.getString("nome"));
                    usuario.setDocumento(rs.getString("documento"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
                    usuario.setTipoPessoa(TipoPessoa.valueOf(rs.getString("tipo_pessoa")));
                    usuario.setAtivo(rs.getBoolean("ativo"));
                }
            }
        } catch (IOException | PropertyVetoException | SQLException ex) {
            Logger.getLogger(CustomLoginModule.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuario;

    }

}

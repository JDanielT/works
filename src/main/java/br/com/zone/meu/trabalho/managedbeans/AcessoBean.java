package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.auth.PrincipalLogged;
import br.com.zone.meu.trabalho.daos.UsuarioDAO;
import br.com.zone.meu.trabalho.entidades.Usuario;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author daniel
 */
@Named
@SessionScoped
public class AcessoBean implements Serializable {

    @Inject
    private UsuarioDAO usuarioDAO;

    public Usuario getUsuarioLogado() {
        Usuario usuario = null;
        if (PrincipalLogged.getPrincipal() != null) {
            usuario = usuarioDAO.bucarUsuarioPorEmail(PrincipalLogged.getPrincipal().getName());
        }
        return usuario;
    }

    public void logout() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath() + "/home.xhtml?faces-redirect=true");
    }

}

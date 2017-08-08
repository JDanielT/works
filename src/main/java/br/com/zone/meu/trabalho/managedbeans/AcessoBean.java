package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.auth.PrincipalLogged;
import br.com.zone.meu.trabalho.daos.UsuarioDAO;
import br.com.zone.meu.trabalho.entidades.Usuario;
import br.com.zone.meu.trabalho.util.JSFUtil;
import br.com.zone.meu.trabalho.util.MensagemUtil;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author daniel
 */
@Named
@ViewScoped
public class AcessoBean implements Serializable {

    private String email;
    private String senha;

    @Inject
    private UsuarioDAO usuarioDAO;

    private String paginaRestritaRequisitada;
    private String queryRestritaRequisitada;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuarioLogado() {
        Usuario usuario = null;
        if (PrincipalLogged.getPrincipal() != null) {
            usuario = usuarioDAO.bucarUsuarioPorEmail(PrincipalLogged.getPrincipal().getName());
        }
        return usuario;
    }

    /**
     * Verifica se o usuário requisitou alguma página restrita antes do login
     */
    @PostConstruct
    public void init() {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        Map<String, Object> requestMap = externalContext.getRequestMap();
        String pagina = (String) requestMap.get(RequestDispatcher.FORWARD_SERVLET_PATH);
        String query = (String) requestMap.get(RequestDispatcher.FORWARD_QUERY_STRING);

        if (pagina != null) {
            this.paginaRestritaRequisitada = pagina;
            this.queryRestritaRequisitada = query;
        }

    }

    public void login() {

        String pagina;

        ExternalContext externalContext = FacesContext.getCurrentInstance()
                .getExternalContext();

        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            request.login(email, senha);
        } catch (ServletException ex) {
            MensagemUtil.mensagemDeErro("Ooops!:", "Falha ao realizar login, verifique email e senha digitados!");
            Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
            if (paginaRestritaRequisitada != null) {
                Map<String, Object> requestMap = externalContext.getRequestMap();
                requestMap.put(RequestDispatcher.FORWARD_SERVLET_PATH, paginaRestritaRequisitada);
                requestMap.put(RequestDispatcher.FORWARD_QUERY_STRING, queryRestritaRequisitada);
            }
        }

        if (getUsuarioLogado() != null) {
            if (paginaRestritaRequisitada != null) {
                if (queryRestritaRequisitada != null) {
                    paginaRestritaRequisitada += paginaRestritaRequisitada.contains("?") ? "&" + queryRestritaRequisitada : "?" + queryRestritaRequisitada;
                    paginaRestritaRequisitada += "&faces-redirect=true";
                }
                pagina = paginaRestritaRequisitada;
            } else {
                pagina = "/home.xhtml?faces-redirect=true";
            }

            try {
                ExternalContext ec = JSFUtil.getExternalContext();
                ec.redirect(ec.getRequestContextPath() + pagina);
            } catch (IOException ex) {
                Logger.getLogger(AcessoBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void logout() throws IOException {
        ExternalContext ec = JSFUtil.getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath() + "/home.xhtml?faces-redirect=true");
    }

}

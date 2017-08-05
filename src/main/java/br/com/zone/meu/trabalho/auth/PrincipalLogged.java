package br.com.zone.meu.trabalho.auth;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 *
 * @author daniel
 */
public class PrincipalLogged {

    public static Principal getPrincipal(){
        
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return req.getUserPrincipal();
        
    }
    
}

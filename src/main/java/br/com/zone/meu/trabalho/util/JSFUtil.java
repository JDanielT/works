package br.com.zone.meu.trabalho.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author daniel
 */
public class JSFUtil {

    public static ExternalContext getExternalContext(){
        return FacesContext.getCurrentInstance().getExternalContext();
    }
    
}

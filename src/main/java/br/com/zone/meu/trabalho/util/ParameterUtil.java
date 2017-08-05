package br.com.zone.meu.trabalho.util;

import javax.faces.context.FacesContext;

/**
 *
 * @author daniel
 */
public class ParameterUtil {
    
    public static String getRequestParameter(String parametro){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parametro);
    }

}

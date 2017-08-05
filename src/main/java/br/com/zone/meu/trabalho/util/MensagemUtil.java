package br.com.zone.meu.trabalho.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author daniel
 */
public class MensagemUtil {

    public static void mensagemInformativa(String cabecalho, String corpo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, cabecalho, corpo));
    }

    public static void mensagemDeAlerta(String cabecalho, String corpo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, cabecalho, corpo));
    }

    public static void mensagemDeErro(String cabecalho, String corpo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, cabecalho, corpo));
    }

    public static void mensagemDeErroFatal(String cabecalho, String corpo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, cabecalho, corpo));
    }

}

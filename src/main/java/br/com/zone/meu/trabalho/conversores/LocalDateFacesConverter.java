package br.com.zone.meu.trabalho.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author daniel
 */
@FacesConverter(value = "localDateFacesConverter")
public class LocalDateFacesConverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object resultado = null;
        if(value != null && !value.isEmpty()) {
            resultado = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return resultado;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LocalDate dateValue = (LocalDate) value;
        return dateValue.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
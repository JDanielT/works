package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.daos.DAOGenerico;
import br.com.zone.meu.trabalho.entidades.BaseEntity;
import br.com.zone.meu.trabalho.util.MensagemUtil;
import br.com.zone.meu.trabalho.util.ParameterUtil;
import org.hibernate.exception.ConstraintViolationException;

import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 * @param <T>
 */
public abstract class AbstractBean<T extends BaseEntity> implements Serializable {

    private Class<T> itemClass;
    private T entity;
    protected Collection<T> itens;

    public AbstractBean(Class<T> itemClass) {
        this.itemClass = itemClass;
    }

    public Class<T> getItemClass() {
        return itemClass;
    }

    public void setItemClass(Class<T> itemClass) {
        this.itemClass = itemClass;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Collection<T> getItens() {
        if (itens == null) {
            itens = getDAO().buscarTodos();
        }
        return itens;
    }

    public void setItens(Collection<T> itens) {
        this.itens = itens;
    }

    public void preCadastro() {
        String id = ParameterUtil.getRequestParameter("id");
        if (id == null) {
            try {
                entity = itemClass.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(AbstractBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                setEntity(getDAO().buscarPorId(Long.parseLong(id)));
            } catch (Exception ex) {
                MensagemUtil.mensagemDeAlerta("Identificador inválido", "Identificador inválido");
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String salvar() {

        String pagina = null;

        try {

            getDAO().salvar(entity);

            pagina = irParaPaginaListagem();
            
            limparDados();

        }catch (Exception ex) {

            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);

            if(ex.getCause() instanceof ConstraintViolationException){
                MensagemUtil.mensagemDeErro("Ooops, Registro já cadastrado!", null);
            }else{
                MensagemUtil.mensagemDeErro("Um problema ocorreu ao salvar", "Erro: "+ex.getMessage());
            }
        }

        return pagina;

    }

    public String excluir() {

        String pagina = null;

        try {

            getDAO().excluir(entity);

            pagina = irParaPaginaListagem();

        } catch (Exception ex) {
            MensagemUtil.mensagemDeErro("Um problema ocorreu ao excluir", "Esse registro está sendo utilizado em outra tabela");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        limparDados();

        return pagina;

    }

    public void limparDados() {
        itens = null;
        entity = null;
    }

    protected abstract DAOGenerico<T> getDAO();

    public String irParaPaginaListagem(){
        return "index.xhtml?faces-redirect=true";
    }

    public abstract String irParaPaginaCadastroEdicao();

}

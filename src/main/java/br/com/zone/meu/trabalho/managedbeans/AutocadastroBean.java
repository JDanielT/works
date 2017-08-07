package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.daos.EstadoDAO;
import br.com.zone.meu.trabalho.daos.MunicipioDAO;
import br.com.zone.meu.trabalho.daos.UsuarioDAO;
import br.com.zone.meu.trabalho.entidades.Estado;
import br.com.zone.meu.trabalho.entidades.Municipio;
import br.com.zone.meu.trabalho.entidades.Sexo;
import br.com.zone.meu.trabalho.entidades.TipoPessoa;
import br.com.zone.meu.trabalho.entidades.TipoUsuario;
import br.com.zone.meu.trabalho.entidades.Usuario;
import br.com.zone.meu.trabalho.mail.ConfirmacaoCadastroService;
import br.com.zone.meu.trabalho.util.MensagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author daniel
 */
@Named
@ViewScoped
public class AutocadastroBean implements Serializable {

    @Inject
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;

    @Inject
    private ConfirmacaoCadastroService confirmacao;

    @Inject
    private EstadoDAO estadoDAO;
    private List<SelectItem> estados;

    @Inject
    private MunicipioDAO municipioDAO;
    private List<SelectItem> municipios;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sexo[] getSexos() {
        return Sexo.values();
    }

    public List<SelectItem> getEstados() {
        if (estados == null) {
            estados = new ArrayList<>();
            estadoDAO.listarTodos().forEach((e) -> {
                estados.add(new SelectItem(e, e.getSigla()));
            });
        }
        return estados;
    }
    
    public List<SelectItem> getMunicipios() {
        Estado e = usuario.getMunicipio().getEstado();
        if (e != null) {
            municipios = new ArrayList<>();
            municipioDAO.buscarMunicipiosPorEstado(e).forEach((m) -> {
                municipios.add(new SelectItem(m, m.getNome()));
            });
        }
        return municipios;
    }

    public void preCadastro() {
        usuario = new Usuario();
        usuario.setMunicipio(new Municipio());
        usuario.setAtivo(Boolean.FALSE);
        usuario.setTipoPessoa(TipoPessoa.FISICA);
        usuario.setTipoUsuario(TipoUsuario.CONVENCIONAL);
    }

    public void salvar() {

        try {

            usuarioDAO.salvar(usuario);
            confirmacao.enviarEmailDeConfirmacao(usuario);

            MensagemUtil.mensagemInformativa("Cadastro concluído!", "Você receberá um e-mail com instruções de acesso");
            
        } catch (Exception ex) {
            Logger.getLogger(AutocadastroBean.class.getName()).log(Level.SEVERE, null, ex);
            MensagemUtil.mensagemDeErro("Ooops!", "Um problema ocorreu ao persistir os dados");
        }

    }

}

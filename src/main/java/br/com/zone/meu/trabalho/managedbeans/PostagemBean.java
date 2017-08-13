package br.com.zone.meu.trabalho.managedbeans;

import br.com.zone.meu.trabalho.daos.EmpresaDAO;
import br.com.zone.meu.trabalho.daos.PostagemDAO;
import br.com.zone.meu.trabalho.entidades.Empresa;
import br.com.zone.meu.trabalho.entidades.Postagem;
import br.com.zone.meu.trabalho.entidades.TipoPostagem;
import br.com.zone.meu.trabalho.entidades.Voto;
import br.com.zone.meu.trabalho.util.JSFUtil;
import br.com.zone.meu.trabalho.util.MensagemUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author daniel
 */
@Named
@ViewScoped
public class PostagemBean extends AbstractBean<Postagem> {

    private final String PAG_FORM = "pages/postagens/postagem.xhtml";

    @Inject
    private PostagemDAO postagemDAO;

    @Inject
    private EmpresaDAO empresaDAO;

    @Inject
    private AcessoBean acessoBean;

    //busca de postagens por empresa
    @Inject
    private Empresa empresa;

    //lazylist
    private Double pagina = 0.0;
    private Double salto = 0.0;
    private Double totalRegistros;
    private final Double tamanhoPagina = 3.0;

    public PostagemBean() {
        super(Postagem.class);
    }

    @PostConstruct
    public void init() {
        this.buscar();
        this.totalRegistros = postagemDAO.count().doubleValue();
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Double getPagina() {
        return pagina;
    }

    public void setPagina(Double pagina) {
        this.pagina = pagina;
    }

    public Double getSalto() {
        return salto;
    }

    public void setSalto(Double salto) {
        this.salto = salto;
    }

    public Double getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Double totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public Double getTamanhoPagina() {
        return tamanhoPagina;
    }

    @Override
    protected PostagemDAO getDAO() {
        return postagemDAO;
    }

    @Override
    public String irParaPaginaCadastroEdicao() {
        return PAG_FORM + "?faces-redirect=true";
    }

    public TipoPostagem[] getTipos() {
        return TipoPostagem.values();
    }

    public List<String> getEmpresas() {
        List<String> empresas = new ArrayList<>();
        empresaDAO.buscarTodos().forEach((e) -> {
            empresas.add(e.toAutoCompleteString());
        });
        return empresas;
    }

    public void visualizar() {
        super.preCadastro();
    }

    @Override
    public Collection<Postagem> getItens() {
        return super.itens;
    }

    public void buscar() {
        itens = getDAO().buscarComLimite(salto.intValue(), tamanhoPagina.intValue());
    }

    public void proxima() {
        salto = salto + tamanhoPagina;
        pagina += 1;
        this.buscar();
    }
    
    public boolean isProximoAtivo(){
        return pagina < (this.getTotalPaginas() - 1);
    }

    public void anterior() {
        salto = salto - tamanhoPagina;
        pagina -= 1;
        this.buscar();
    }

    public long getTotalPaginas() {
        long total = new Double(Math.ceil(totalRegistros / tamanhoPagina)).longValue();
        if(total == 1){
            total = 0;
        }
        return total;
    }

    @Override
    public void preCadastro() {
        super.preCadastro();
        if (getEntity().getId() == null) {
            getEntity().setEmpresa(new Empresa());
            getEntity().setDataPostagem(LocalDate.now());
            getEntity().setUsuario(acessoBean.getUsuarioLogado());
            getEntity().setVoto(new Voto());
        } else {
            if (!getEntity().getUsuario().equals(acessoBean.getUsuarioLogado())) {
                this.redirect(JSFUtil.getExternalContext().getRequestContextPath() + "/403.xhtml");
            }
        }
    }

    public void buscarPorEmpresa() {
        if (empresa != null && empresa.getId() != null) {
            empresa = empresaDAO.buscarPorId(empresa.getId());
            setItens(postagemDAO.buscarPostagensPorEmpresa(empresa));
        }
    }

    public void limparBusca() {
        empresa = new Empresa();
        super.setItens(null);
        this.buscar();
    }

    @Override
    public String salvar() {

        getEntity().getVoto().setEmpresa(getEntity().getEmpresa());
        getEntity().getVoto().setUsuario(getEntity().getUsuario());

        if (getEntity().getTexto().length() < 100) {
            MensagemUtil.mensagemDeAlerta("Sua mensagem precisa ter pelo menos 100 caracteres", null);
            return null;
        }

        super.salvar();

        this.redirect(JSFUtil.getExternalContext().getRequestContextPath() + "/home.xhtml");
        return null;

    }

    private void redirect(String pagina) {
        try {
            JSFUtil.getExternalContext().redirect(pagina);
        } catch (IOException ex) {
            Logger.getLogger(PostagemBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

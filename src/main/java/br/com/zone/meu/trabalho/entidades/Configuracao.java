package br.com.zone.meu.trabalho.entidades;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "configuracoes")
@NamedQueries({
    @NamedQuery(name=Configuracao.BUSCAR_CONFIGURACAO, query = "SELECT c FROM Configuracao c")
})
public class Configuracao implements BaseEntity {

    public static final String BUSCAR_CONFIGURACAO = "Configuracao.buscarConfiguracao";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(name = "email", length = 60)
    private String email;

    @Column(name = "senha", length = 60)
    private String senha;

    @Column(name = "servidor", length = 60)
    private String servidor;

    @Column(name = "porta", length = 10)
    private String porta;
    
    @Column(name = "url")
    private String urlSistema;
    
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getUrlSistema() {
        return urlSistema;
    }

    public void setUrlSistema(String urlSistema) {
        this.urlSistema = urlSistema;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Configuracao other = (Configuracao) obj;
        return Objects.equals(this.id, other.id);
    }

}

package br.com.zone.meu.trabalho.entidades;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "votos")
@NamedQueries({
    @NamedQuery(name = Voto.BUSCAR_VOTOS_POR_EMPRESA
            , query = "SELECT v FROM Voto v WHERE v.empresa.id = ?1"),
    @NamedQuery(name = Voto.BUSCAR_MEDIA_VOTOS_POR_EMPRESA
            , query = "SELECT AVG(v.valor) FROM Voto v WHERE v.empresa.id = ?1")
})
public class Voto implements BaseEntity {
    
    public static final String BUSCAR_VOTOS_POR_EMPRESA = "Voto.buscarVotoPorEmpresa";
    public static final String BUSCAR_MEDIA_VOTOS_POR_EMPRESA = "Voto.buscarMediaVotosPorEmpresa";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int valor;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Voto other = (Voto) obj;
        return Objects.equals(this.id, other.id);
    }
    
}

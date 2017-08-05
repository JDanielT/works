package br.com.zone.meu.trabalho.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author daniel
 * 
 */
@Entity
@Table(name = "municipios")
@NamedQueries({
    @NamedQuery(name = Municipio.BUSCAR_MUNICIPIOS_POR_ESTADO,
                query = "SELECT m FROM Municipio m WHERE m.estado.id = ?1"),
    @NamedQuery(name = Municipio.BUSCAR_MUNICIPIO_POR_NOME,
                query = "SELECT m FROM Municipio m WHERE UPPER(m.nome) = ?1")
})
public class Municipio implements BaseEntity {
    
    public final static String BUSCAR_MUNICIPIOS_POR_ESTADO = "Municipio.bucarMunicipiosPorEstado";
    public final static String BUSCAR_MUNICIPIO_POR_NOME = "Municipio.bucarMunicipioPorNome";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @OneToOne
    private Estado estado;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome != null ? nome.toUpperCase() : null;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Municipio other = (Municipio) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}

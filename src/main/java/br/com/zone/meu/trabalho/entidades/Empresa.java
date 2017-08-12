package br.com.zone.meu.trabalho.entidades;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "empresas")
@NamedNativeQueries({
    @NamedNativeQuery(name = Empresa.BUCAR_MELHORES_EMPRESAS, 
                      query = "SELECT e.id, e.nome as empresa, AVG(v.valor) as reputacao "
                            + "FROM empresas e " 
                            + "JOIN votos v ON v.empresa_id = e.id "
                            + "GROUP BY e.id, empresa "
                            + "HAVING AVG(v.valor) >= 3 "
                            + "ORDER BY reputacao DESC", resultClass = EmpresaReputacao.class),
    
    @NamedNativeQuery(name = Empresa.BUCAR_PIORES_EMPRESAS, 
                      query = "SELECT e.id, e.nome as empresa, AVG(v.valor) as reputacao "
                            + "FROM empresas e " 
                            + "JOIN votos v ON v.empresa_id = e.id "
                            + "GROUP BY e.id, empresa "
                            + "HAVING AVG(v.valor) < 3 "
                            + "ORDER BY reputacao DESC", resultClass = EmpresaReputacao.class)
})
public class Empresa implements BaseEntity {

    public static final String BUCAR_MELHORES_EMPRESAS = "Empresa.buscarMelhoresEmpresas";
    public static final String BUCAR_PIORES_EMPRESAS = "Empresa.buscarPioresEmpresas";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    @NotEmpty
    private String nome;

    @Column(length = 100)
    @NotEmpty
    private String cnpj;

    private String endereco;

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
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final Empresa other = (Empresa) obj;
        return Objects.equals(this.id, other.id);
    }

    public String toAutoCompleteString() {
        return "{label: '" + this.nome + "', value: " + this.id + "}";
    }

}

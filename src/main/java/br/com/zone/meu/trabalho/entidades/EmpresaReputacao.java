package br.com.zone.meu.trabalho.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author daniel
 */

@Entity
public class EmpresaReputacao implements Serializable {
    
    @Id
    private Long id;
    private String empresa;
    private int reputacao;

    public EmpresaReputacao() {
    }

    public EmpresaReputacao(Long id, String empresa, int reputacao) {
        this.id = id;
        this.empresa = empresa;
        this.reputacao = reputacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public int getReputacao() {
        return reputacao;
    }

    public void setReputacao(int reputacao) {
        this.reputacao = reputacao;
    }
    
}

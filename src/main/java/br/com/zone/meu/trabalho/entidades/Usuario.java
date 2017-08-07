package br.com.zone.meu.trabalho.entidades;

import br.com.zone.meu.trabalho.conversores.LocalDatePersistenceConverter;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "usuarios")
@NamedQueries({
    @NamedQuery(name = Usuario.BUSCAR_USUARIOS_POR_TIPO,
            query = "SELECT u FROM Usuario u WHERE u.tipoUsuario = ?1"),
    @NamedQuery(name = Usuario.BUSCAR_USUARIO_POR_EMAIL_SENHA,
            query = "SELECT u FROM Usuario u WHERE u.email = ?1 AND u.senha = ?2"),
    @NamedQuery(name = Usuario.BUSCAR_USUARIO_POR_EMAIL,
            query = "SELECT u FROM Usuario u WHERE u.email = ?1"),
    @NamedQuery(name = Usuario.BUSCAR_USUARIO_POR_EMAIL_DOCUMENTO,
            query = "SELECT u FROM Usuario u WHERE u.email = ?1 AND u.documento = ?2")
})
public class Usuario implements BaseEntity {

    public static final String BUSCAR_USUARIO_POR_EMAIL_SENHA = "Usuario.buscarUsuarioPorEmailSenha";
    public static final String BUSCAR_USUARIOS_POR_TIPO = "Usuario.buscarUsuariosPorTipo";
    public static final String BUSCAR_RESPONSAVEIS_POR_UNIDADE = "Usuario.buscarResponsaveisPorUnidade";
    public static final String BUSCAR_USUARIO_POR_EMAIL = "Usuario.buscarUsuarioPorEmail";
    public static final String BUSCAR_USUARIO_POR_EMAIL_DOCUMENTO = "Usuario.buscarUsuarioPorEmailDocumento";
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60)
    @Size(min = 3, max = 60)
    private String nome;

    @Column(length = 18, unique = true)
    private String documento;

    @Column(length = 15) 
    private String telefone;
    
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate nascimento;
    
    @Column(length = 10) 
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
   
    @Column(length = 10)
    private String cep;
    
    @Column(length = 100)
    private String endereco;
    
    @Column(length = 5) 
    private String numero;
    
    @Column(length = 65)
    private String bairro;
    
    @ManyToOne
    @JoinColumn(name = "municipio_id", nullable = true, referencedColumnName = "id")
    private Municipio municipio;

    @Column(length = 60, unique = true)
    @Email
    private String email;

    private String senha;

    @Column(name = "tipo_usuario")
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Column(name = "tipo_pessoa")
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    private boolean ativo;

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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Usuario other = (Usuario) obj;
        return Objects.equals(this.id, other.id);
    }

}

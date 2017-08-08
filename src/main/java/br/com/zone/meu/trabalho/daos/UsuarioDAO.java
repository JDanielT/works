package br.com.zone.meu.trabalho.daos;

import br.com.zone.meu.trabalho.entidades.Usuario;

/**
 *
 * @author daniel
 */
public class UsuarioDAO extends DAOGenerico<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }
    
    public Usuario bucarUsuarioPorEmail(String email) {
        return (Usuario) buscarUmResultado(Usuario.BUSCAR_USUARIO_POR_EMAIL, email);
    }

}

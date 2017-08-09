package br.com.zone.meu.trabalho.daos;

import br.com.zone.meu.trabalho.entidades.Empresa;
import br.com.zone.meu.trabalho.entidades.Postagem;
import java.util.List;

/**
 *
 * @author daniel
 */
public class PostagemDAO extends DAOGenerico<Postagem> {

    public PostagemDAO() {
        super(Postagem.class);
    }
    
    public List<Postagem> buscarPostagensPorEmpresa(Empresa empresa){
        return buscar(Postagem.BUSCAR_POSTAGENS_POR_EMPRESA, empresa.getId());
    }

}

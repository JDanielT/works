package br.com.zone.meu.trabalho.daos;

import br.com.zone.meu.trabalho.entidades.Empresa;
import br.com.zone.meu.trabalho.entidades.Voto;
import java.util.List;

/**
 *
 * @author daniel
 */
public class VotoDAO extends DAOGenerico<Voto> {

    public VotoDAO() {
        super(Voto.class);
    }
    
    public List<Voto> buscarVotosPorEmpresa(Empresa empresa){
        return buscar(Voto.BUSCAR_VOTOS_POR_EMPRESA, empresa.getId());
    }
    
    public Double buscarMediaVotosPorEmpresa(Empresa empresa){
        return (Double) buscarUmResultado(Voto.BUSCAR_MEDIA_VOTOS_POR_EMPRESA, empresa.getId());
    }
    
}

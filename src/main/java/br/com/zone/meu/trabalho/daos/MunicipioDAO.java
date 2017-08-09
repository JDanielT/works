package br.com.zone.meu.trabalho.daos;

import br.com.zone.meu.trabalho.entidades.Estado;
import br.com.zone.meu.trabalho.entidades.Municipio;
import java.util.List;

/**
 *
 * @author daniel
 */
public class MunicipioDAO extends DAOGenerico<Municipio> {

    public MunicipioDAO() {
        super(Municipio.class);
    }

    public List<Municipio> buscarMunicipiosPorEstado(Estado estado){
        return buscar(Municipio.BUSCAR_MUNICIPIOS_POR_ESTADO, estado.getId());
    }
    
}

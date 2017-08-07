package br.com.zone.meu.trabalho.daos;

import br.com.zone.meu.trabalho.entidades.Configuracao;

/**
 *
 * @author daniel
 */
public class ConfiguracaoDAO extends DAOGenerico<Configuracao>{

    public ConfiguracaoDAO() {
        super(Configuracao.class);
    }
    
    public Configuracao getConfiguracao(){
        return (Configuracao) buscarUmResultado(Configuracao.BUSCAR_CONFIGURACAO);
    }
    
}

package br.com.zone.meu.trabalho.daos;

import br.com.zone.meu.trabalho.entidades.Token;

/**
 *
 * @author daniel
 */
public class TokenDAO extends DAOGenerico<Token> {

    public TokenDAO() {
        super(Token.class);
    }
    
    public Token buscarTokenPorHash(String hash) {
        return (Token) buscarUmResultado(Token.BUSCAR_TOKEN_POR_HASH, hash);
    }
    
}

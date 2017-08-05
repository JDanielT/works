package br.com.zone.meu.trabalho.auth;

import java.security.Principal;

/**
 *
 * @author daniel
 */
public class UserPrincipal implements Principal {

    private String name;

    public UserPrincipal(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}

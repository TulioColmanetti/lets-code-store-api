package br.com.tulio.letscodestore.config.service;

import br.com.tulio.letscodestore.domain.User;

public interface SecurityService {

    String authenticate(final String username, final String password);

    void authenticate(final String token);

    User getCurrentUser();

}

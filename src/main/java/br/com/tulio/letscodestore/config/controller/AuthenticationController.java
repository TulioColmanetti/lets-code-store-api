package br.com.tulio.letscodestore.config.controller;

import br.com.tulio.letscodestore.config.resources.AuthenticationResponse;
import br.com.tulio.letscodestore.config.resources.CredentialsDTO;
import br.com.tulio.letscodestore.config.service.SecurityService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final SecurityService securityService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody @Valid CredentialsDTO credentialsDTO) {
        final String username = credentialsDTO.getUsername();
        final String password = credentialsDTO.getPassword();

        // Authenticating...
        final String token = securityService.authenticate(username, password);

        logger.debug("User '{}' authenticated successfully -> Token: '{}'", username, token);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }


}

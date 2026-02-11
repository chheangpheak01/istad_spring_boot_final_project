package com.sopheak.istadfinalems.keycloack;
import org.springframework.beans.factory.annotation.Value;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KeycloakConfigure {
    @Value("${spring.keycloak.server-url}")
    private String keycloakSeverUrl;
    @Value("${spring.keycloak.main-realm}")
    private String realm;
    @Value("${spring.keycloak.client-id}")
    private String clientId;
    @Value("${spring.keycloak.client-secrete}")
    private String clientSecrete;
    @Bean
    public Keycloak keycloakInstance(){
        return KeycloakBuilder
                .builder()
                .serverUrl(keycloakSeverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecrete)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}


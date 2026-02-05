package com.sopheak.istadfinalems.keycloack;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KeycloakConfigure {
    @Value("${spring.file.keycloak.server-url}")
    private String keycloakSeverUrl;
    @Value("${spring.file.keycloak.main-realm}")
    private String realm;
    @Value("${spring.file.keycloak.client-id}")
    private String clientId;
    @Value("${spring.file.keycloak.client-secret}")
    private String clientSecret;
    @Bean
    public Keycloak keycloakInstance() {
        return KeycloakBuilder
                .builder()
                .serverUrl(keycloakSeverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }
}

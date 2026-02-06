package com.henriq.libraryapi.security;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import com.henriq.libraryapi.service.ClientService;

@Component
public class CustomRegisteredClientRepository implements RegisteredClientRepository{

    private final ClientService clientService;
    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettings;

    public CustomRegisteredClientRepository(
        ClientService clientService, 
        TokenSettings tokenSettings, 
        ClientSettings clientSettings
    ){
        this.clientService = clientService;
        this.tokenSettings = tokenSettings;
        this.clientSettings = clientSettings;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var client = clientService.getByClientId(clientId);

        if(client == null) return null;

        return RegisteredClient.withId(client.getId().toString())
            .clientId(client.getClientId())
            .clientSecret(client.getClientSecret())
            .redirectUri(client.getRedirectUri())
            .scope(client.getScope())
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantTypes(grantTypes -> {
                grantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                grantTypes.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
            })
            .tokenSettings(tokenSettings)
            .clientSettings(clientSettings)
            .build();
    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public void save(RegisteredClient registeredClient) {}
    
}

package com.hackathon.twofaceauth.config;

import com.cpaassdk.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwoFactorAuthentication {

    public TwoFactorAuthentication() {
    }

    @Bean
    public Client client(@Value("${clientId}") String clientId, @Value("${clientSecret}") String clientSecret,
                         @Value("${baseUrl}") String baseUrl) {
        return new Client(clientId, clientSecret, baseUrl);
    }

}


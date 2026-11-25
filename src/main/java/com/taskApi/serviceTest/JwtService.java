package com.taskApi.serviceTest;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    private final JwtEncoder jwtencoder;

    public JwtService(JwtEncoder jwtencoder) {
        this.jwtencoder = jwtencoder;
    }

    public String gerarToken(String email, String role) {
        Instant agora = Instant.now();
        Long tempoExpiração = 3600L;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("taskApi")
                .subject(email)
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(tempoExpiração))
                .claim("scope", role)
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtencoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }

    public String gerarTokenAdmin(String cpf, String role) {
        Instant agora = Instant.now();
        Long tempoExpiracao = 3600L;

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("taskApi")
                .subject(cpf)
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(tempoExpiracao))
                .claim("scope", role)
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtencoder.encode(JwtEncoderParameters.from(header, claimsSet)).getTokenValue();
    }
}

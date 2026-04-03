package com.example.userjson.client;

import com.example.users.ValidateTokenRequest;
import com.example.users.ValidateTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
@RequiredArgsConstructor
public class SoapAuthClient {

    private final WebServiceTemplate webServiceTemplate;

    public boolean validateToken(String token) {
        ValidateTokenRequest request = new ValidateTokenRequest();
        request.setToken(token);

        try {
            ValidateTokenResponse response = (ValidateTokenResponse) webServiceTemplate
                .marshalSendAndReceive("http://localhost:8081/ws", request);
            return response.isValid();
        } catch (Exception e) {
            return false;
        }
    }
}
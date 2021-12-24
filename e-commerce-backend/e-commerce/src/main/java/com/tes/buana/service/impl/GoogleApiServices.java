package com.tes.buana.service.impl;

import com.google.gson.Gson;
import com.tes.buana.dto.user.UserGoogleDto;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GoogleApiServices {
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public UserGoogleDto decodeToken(String tokenId) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token="+tokenId))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

        return new Gson().fromJson(response.body(), UserGoogleDto.class);
    }
}

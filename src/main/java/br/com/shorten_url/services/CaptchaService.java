package br.com.shorten_url.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CaptchaService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${google.recaptcha.verify-url}")
    private String verifyUrl;

    public boolean verifyRecaptcha(String recaptchaToken) {

        String url = verifyUrl;

        JsonNode response = RestClient.create().post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("secret=" + recaptchaSecret + "&response=" + recaptchaToken)
                .retrieve()
                .body(JsonNode.class);

        return response.path("success")
                .asBoolean(false);
    }
}

package com.kt.cloud.mqmgmt.tokenservice;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    private RestTemplate restTemplate = new RestTemplate();

    public String getToken(String userId, String userPw) {

        JsonForm jsonForm = new JsonForm(userId, userPw);

        HttpHeaders headers = new HttpHeaders();
     //   headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(jsonForm.getJsonStr(), headers);

        ResponseEntity<Object> restEntity = restTemplate.exchange("https://api.ucloudbiz.olleh.com/d1/identity/auth/tokens", HttpMethod.POST, request, Object.class);

        return restEntity.getHeaders().getFirst("X-Subject-Token");

    }
}

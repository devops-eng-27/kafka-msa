package com.kt.cloud.mqmgmt.vmmgmtservice.request;

import com.kt.cloud.mqmgmt.vmmgmtservice.form.KeyPairJsonForm;
import com.kt.cloud.mqmgmt.vmmgmtservice.form.SvrCreateJsonForm;
import com.kt.cloud.mqmgmt.vmmgmtservice.form.SvrPauseJsonForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service("ApiRequest")
@Slf4j
public class ApiRequestImpl implements ApiRequest {

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    // 키 페어 요청
    public String ApiRequestKeyPair(String token, String keyName) {

        KeyPairJsonForm jsonForm = new KeyPairJsonForm(keyName);
        headers.add("X-Auth-Token", token);

        HttpEntity<?> request = new HttpEntity<>(jsonForm.getJsonStr(), headers);

        ResponseEntity<String> restEntity = restTemplate.exchange("https://api.ucloudbiz.olleh.com/d1/server/os-keypairs", HttpMethod.POST, request, String.class);

        log.info(restEntity.getBody());

        return restEntity.getBody();

    }

    // VM 생성 요청
    public String ApiRequestCreateSvr(String token, String keyName, String svrName, String flavorRef, String availabilityZone) {

        SvrCreateJsonForm jsonForm = new SvrCreateJsonForm(svrName,keyName,flavorRef,availabilityZone);

        headers.add("X-Auth-Token", token);
        HttpEntity<?> request = new HttpEntity<>(jsonForm.getJsonStr(), headers);

        ResponseEntity<String> restEntity = restTemplate.exchange("https://api.ucloudbiz.olleh.com/d1/server/servers", HttpMethod.POST, request, String.class);

        log.info(restEntity.getBody());

        return restEntity.getBody();

    }

    // Body 없이 api 요청
    public String ApiRequestWithoutBody(String token, String URI) {
        headers.add("X-Auth-Token", token);
        HttpEntity<?> request = new HttpEntity<>("", headers);

        log.info(URI);

        ResponseEntity<String> restEntity = restTemplate.exchange(URI, HttpMethod.GET, request, String.class);
        log.info(restEntity.getBody());

        return restEntity.getBody();

    }

    // VM 정지 요청
    public String ApiPauseServer(String token, String URI) {

        SvrPauseJsonForm jsonForm = new SvrPauseJsonForm();

        headers.add("X-Auth-Token", token);
        HttpEntity<?> request = new HttpEntity<>(jsonForm.getJsonStr(), headers);

        try {
            restTemplate.exchange(URI, HttpMethod.POST, request, String.class);
        }
        catch (final HttpClientErrorException e) {
            log.info(String.valueOf(e.getStatusCode()));
            log.info(e.getResponseBodyAsString());
        }

        return "";

    }

    // VM 정지 요청
    public String ApiDeleteServer(String token, String URI) {

        headers.add("X-Auth-Token", token);
        HttpEntity<?> request = new HttpEntity<>("", headers);

        log.info(URI);

        try {
            restTemplate.exchange(URI, HttpMethod.DELETE, request, String.class);
        }
        catch (final HttpClientErrorException e) {
            log.info(String.valueOf(e.getStatusCode()));
            log.info(e.getResponseBodyAsString());
        }

        return "";

    }

    // VM 삭제 요청
    public String ApiDeleteKeyPair(String token, String URI) {

        headers.add("X-Auth-Token", token);
        HttpEntity<?> request = new HttpEntity<>("", headers);

        log.info(URI);

        try {
            restTemplate.exchange(URI, HttpMethod.DELETE, request, String.class);
        }
        catch (final HttpClientErrorException e) {
            log.info(String.valueOf(e.getStatusCode()));
            log.info(e.getResponseBodyAsString());
        }

        return "";

    }
}

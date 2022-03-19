package com.kt.cloud.mqmgmt.networkservice.request;

import com.kt.cloud.mqmgmt.networkservice.form.FireWallJsonForm;
import com.kt.cloud.mqmgmt.networkservice.form.PortForwardingJsonForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("ApiRequest")
@Slf4j
public class ApiRequestImpl implements ApiRequest {

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    // 포트 포워딩 설정
    public String ApiRequestPortForwarding(String token, String vmGuestIp, String entPublicIpId, String privatePort, String publicPort) {

        PortForwardingJsonForm jsonForm = new PortForwardingJsonForm(vmGuestIp, entPublicIpId, privatePort, publicPort);
        headers.add("X-Auth-Token", token);

        HttpEntity<?> request = new HttpEntity<>(jsonForm.getJsonStr(), headers);

        ResponseEntity<String> restEntity = restTemplate.exchange("https://api.ucloudbiz.olleh.com/d1/nc/Portforwarding", HttpMethod.POST, request, String.class);

        log.info(restEntity.getBody());

        return restEntity.getBody();

    }

    // 방화벽 설정
    public String ApiRequestFireWall(String token, String portId, String publicPort) {

        FireWallJsonForm jsonForm = new FireWallJsonForm(portId,publicPort);

        headers.add("X-Auth-Token", token);
        HttpEntity<?> request = new HttpEntity<>(jsonForm.getJsonStr(), headers);

        ResponseEntity<String> restEntity = restTemplate.exchange("https://api.ucloudbiz.olleh.com/d1/nc/Firewall", HttpMethod.POST, request, String.class);

        log.info(restEntity.getBody());

        return restEntity.getBody();

    }

    // 방화벽 삭제
    public String ApiDeleteFireWall(String token, String URI) {

        headers.add("X-Auth-Token", token);
        HttpEntity<?> request = new HttpEntity<>("", headers);

        log.info(URI);

        ResponseEntity<String> restEntity = restTemplate.exchange(URI, HttpMethod.DELETE, request, String.class);

        log.info(restEntity.getBody());

        return restEntity.getBody();

    }

    // body 없이 api 전달
    public String ApiRequestWithoutBody(String token, String URI) {
        headers.add("X-Auth-Token", token);
        HttpEntity<?> request = new HttpEntity<>("", headers);

        log.info(URI);

        ResponseEntity<String> restEntity = restTemplate.exchange(URI, HttpMethod.GET, request, String.class);

        log.info(restEntity.getBody());

        return restEntity.getBody();

    }
}

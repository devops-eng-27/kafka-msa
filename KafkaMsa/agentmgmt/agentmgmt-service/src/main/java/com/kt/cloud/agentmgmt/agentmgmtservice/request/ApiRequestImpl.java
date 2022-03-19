package com.kt.cloud.agentmgmt.agentmgmtservice.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service("ApiRequest")
@Slf4j
public class ApiRequestImpl implements ApiRequest {

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    public void containerApi(ArrayList<String> service, String control) {
        /* 선택한 service(kafka, zookeeper, kafdrop 중 1개 이상)를 start/stop/restart 진행 */
        for (String serviceName : service) {
            // 선택한 service가 1개 이상일 때 아래 진행
            if (serviceName != "") {

                log.info("control :" + control + "/ serviceName :" + serviceName);

                // url로 container 조절
                String url = "http://211.184.188.39:4243/containers/" + serviceName + "/" + control;
                HttpEntity<?> request = new HttpEntity<>(headers);
                ResponseEntity<String> restEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

                log.info("status code : {}", restEntity.getStatusCode());
                log.info("body : {}", restEntity.getBody());
            }
        }

    }

    public void containerList() {

        // 적용 후 list 출력
        String url = "http://211.184.188.39:4243/containers/json";
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<String> restEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        log.info(restEntity.getBody().toString());

    }
}

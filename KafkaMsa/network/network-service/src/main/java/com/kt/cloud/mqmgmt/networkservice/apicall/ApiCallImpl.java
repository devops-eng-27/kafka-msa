package com.kt.cloud.mqmgmt.networkservice.apicall;

import com.kt.cloud.mqmgmt.networkservice.request.ApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service("ApiCall")
@Slf4j
public class ApiCallImpl implements ApiCall {

    @Autowired
    private ApiRequest apiRequest;

    RestTemplate restTemplate = new RestTemplate();

    // token 요청
    public String getTokenApiCall(String userId, String userPw) {
        // 파라미터 추가
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userId", userId);
        params.add("userPw", userPw);

        String URI = "http://localhost:8000/token/get";
        ResponseEntity<String> token = restTemplate.postForEntity(URI, params, String.class);

        log.info(token.getBody());

        return token.getBody();
    }

    // 포트포워딩 설정 요청
    public String setPortFowardingApiCall(String token, String vmGuestIp, String entPublicIpId, String privatePort, String publicPort) throws JSONException {

        String restEntity = apiRequest.ApiRequestPortForwarding(token, vmGuestIp, entPublicIpId, privatePort, publicPort);

        JSONObject jsonObject = new JSONObject(restEntity);
        JSONObject ncObject = jsonObject.getJSONObject("nc_createportforwardingruleresponse");
        String jobId = ncObject.getString("job_id");

        log.info("jobId :" + jobId);
        return jobId;

    }

    // 방화벽 설정 요청
    public String setFireWall(String token, String portId, String publicPort) throws JSONException {

        String restEntity = apiRequest.ApiRequestFireWall(token, portId, publicPort);

        JSONObject jsonObject = new JSONObject(restEntity);
        JSONObject ncObject = jsonObject.getJSONObject("nc_createfirewallruleresponse");
        String jobId = ncObject.getString("job_id");

        log.info("jobId :" + jobId);

        return jobId;
    }
}

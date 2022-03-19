package com.kt.cloud.mqmgmt.networkservice.deletecall;

import com.kt.cloud.mqmgmt.networkservice.apicall.ApiCall;
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

@Service("DeleteCall")
@Slf4j
public class DeleteCallImpl implements DeleteCall {

    @Autowired
    private ApiRequest apiRequest;

    RestTemplate restTemplate = new RestTemplate();

    // 포트 포워딩 삭제
    public String deletePortFowardingApiCall(String token, String virtualIpId) throws JSONException {

        String URI = "https://api.ucloudbiz.olleh.com/d1/nc/Portforwarding/" + virtualIpId;
        String restEntity = apiRequest.ApiDeleteFireWall(token, URI);

        JSONObject jsonObject = new JSONObject(restEntity);
        JSONObject ncObject = jsonObject.getJSONObject("nc_deleteportforwardingruleresponse");
        String jobId = ncObject.getString("job_id");

        log.info("jobId :" + jobId);
        return jobId;

    }

    // 방화벽 삭제
    public String deleteFireWall(String token, String fireWallId) throws JSONException {

        String URI = "https://api.ucloudbiz.olleh.com/d1/nc/Firewall/" + fireWallId;
        String restEntity = apiRequest.ApiDeleteFireWall(token, URI);

        JSONObject jsonObject = new JSONObject(restEntity);
        JSONObject ncObject = jsonObject.getJSONObject("nc_deletefirewallruleresponse");
        String jobId = ncObject.getString("job_id");

        log.info("jobId :" + jobId);

        return jobId;
    }

    // VM 삭제 요청 (network service --> vmmgmt service)
    public String deleteIpApiCall(String token) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        String URI = "http://localhost:8000/mqsvr/delete";
        ResponseEntity<String> restEntity = restTemplate.postForEntity(URI, params, String.class);

        log.info("status code : {}", restEntity.getStatusCode());
        log.info("body : {}", restEntity.getBody());

        return "";
    }
}

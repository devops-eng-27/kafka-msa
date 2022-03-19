package com.kt.cloud.mqmgmt.vmmgmtservice.apicall;

import com.kt.cloud.mqmgmt.vmmgmtservice.request.ApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service("ApiCall")
@Slf4j
public class ApiCallImpl implements ApiCall {

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private ApiRequest apiRequest;

    // token 생성 요청 (vmmgmt service --> token service)
    public String getTokenApiCall(String userId, String userPw) {
        // userId, userPw 파라미터 추가
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userId", userId);
        params.add("userPw", userPw);

        String URI = "http://localhost:8000/token/get";
        ResponseEntity<String> token = restTemplate.postForEntity(URI, params, String.class);

        log.info(token.getBody());

        // token 값 return
        return token.getBody();
    }

    // 키 페어 생성 요청
    public String getKeyPairApiCall(String token, String keyName) {

        String restEntity = apiRequest.ApiRequestKeyPair(token, keyName);
        log.info(restEntity);

        // keyname 값 return
        return keyName;
    }

    // VM 생성 요청
    public String createSvrApiCall(String token, String keyName, String svrName, String flavorRef, String availabilityZone) throws JSONException {
        String restEntity = apiRequest.ApiRequestCreateSvr(token, keyName, svrName, flavorRef, availabilityZone);

        JSONObject jsonObject = new JSONObject(restEntity);
        JSONObject serverObject = jsonObject.getJSONObject("server");
        String svrId = serverObject.getString("id");

        log.info("id :" + svrId);

        return svrId;
    }

    // 생성된 VM 조회
    public String getServerIpApiCall(String token, String svrId) throws JSONException {

        String vmGuestIp = null;
        String URI = "https://api.ucloudbiz.olleh.com/d1/server/servers/" + svrId;

        String restEntity = apiRequest.ApiRequestWithoutBody(token, URI);

        log.info(restEntity);

        JSONObject jsonObject = new JSONObject(restEntity);
        JSONObject serverObject = jsonObject.getJSONObject("server");
        JSONObject addressObject = serverObject.getJSONObject("addresses");

        // addresses 안에 빈 칸이면, vmGuestIp는 null
        if (Objects.equals(String.valueOf(addressObject),"{}")) {
            vmGuestIp = null;
        } 
        else {
            JSONArray dmzObject = addressObject.getJSONArray("DMZ");
            for(int i=0; i<dmzObject.length(); i++) {
                JSONObject obj = dmzObject.getJSONObject(i);

                // vmGuestIp는 addr의 value 값
                vmGuestIp = obj.getString("addr");
            }
        }

//        log.info("IP :" + vmGuestIp);

        return vmGuestIp;
    }

    // 네트워크 설정 요청 (vmmgmt service --> network service)
    public String setIpApiCall(String token, String entPublicIpId, String vmGuestIp) {
        // token, public ip, private ip를 파라미터에 추가
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);
        params.add("entPublicIpId", entPublicIpId);
        params.add("vmGuestIp", vmGuestIp);

        String URI = "http://localhost:8000/net/setip";
        ResponseEntity<String> restEntity = restTemplate.postForEntity(URI, params, String.class);

        log.info("status code : {}", restEntity.getStatusCode());
        log.info("body : {}", restEntity.getBody());

        return "";
    }


}

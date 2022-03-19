package com.kt.cloud.mqmgmt.vmmgmtservice.deletecall;

import com.kt.cloud.mqmgmt.vmmgmtservice.apicall.ApiCall;
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

@Service("DeleteCall")
@Slf4j
public class DeleteCallImpl implements DeleteCall {

    @Autowired
    private ApiRequest apiRequest;

    public String deleteServerApiCall(String token, String serverId) {

        // VM 정지
        String URI = "https://api.ucloudbiz.olleh.com/d1/server/servers/" + serverId + "/action";
        apiRequest.ApiPauseServer(token, URI);

        // VM 삭제
        URI = "https://api.ucloudbiz.olleh.com/d1/server/servers/" + serverId;
        apiRequest.ApiDeleteServer(token, URI);

        return "";

    }

    public String deleteKeyApiCall(String token, String keyName) {

        // VM Key 삭제
        String URI = "https://api.ucloudbiz.olleh.com/d1/server/os-keypairs/" + keyName;
        apiRequest.ApiDeleteKeyPair(token, URI);

        return "";

    }

}

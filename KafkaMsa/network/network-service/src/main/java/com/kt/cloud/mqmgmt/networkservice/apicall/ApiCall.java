package com.kt.cloud.mqmgmt.networkservice.apicall;

import org.json.JSONException;
import org.springframework.util.MultiValueMap;

public interface ApiCall {
    public String getTokenApiCall(String userId, String userPw);
    public String setPortFowardingApiCall(String token, String vmGuestIp, String entPublicIpId, String privatePort, String publicPort) throws JSONException;
    public String setFireWall(String token, String portid, String publicPort) throws JSONException;

}

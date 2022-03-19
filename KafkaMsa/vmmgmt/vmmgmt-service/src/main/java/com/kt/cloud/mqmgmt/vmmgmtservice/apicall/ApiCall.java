package com.kt.cloud.mqmgmt.vmmgmtservice.apicall;

import org.json.JSONException;

import java.util.List;

public interface ApiCall {
    public String getTokenApiCall(String userId, String userPw);
    public String getKeyPairApiCall(String token, String keyName);
    public String createSvrApiCall(String token, String keyName, String svrName, String cpu, String location) throws JSONException;
    public String getServerIpApiCall(String token, String svr_id) throws JSONException;
    public String setIpApiCall(String token, String pubip, String prvip);
}

package com.kt.cloud.mqmgmt.vmmgmtservice.request;

import org.json.JSONException;

public interface ApiRequest {
    public String ApiRequestKeyPair(String token, String keyName);
    public String ApiRequestCreateSvr(String token, String keyName, String svrName, String flavorRef, String availabilityZone);
    public String ApiRequestWithoutBody(String token, String URI);
    public String ApiPauseServer(String token, String URI);
    public String ApiDeleteServer(String token, String URI);
    public String ApiDeleteKeyPair(String token, String URI);
}

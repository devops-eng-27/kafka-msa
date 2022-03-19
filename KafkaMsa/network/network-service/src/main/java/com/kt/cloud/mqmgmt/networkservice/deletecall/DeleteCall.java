package com.kt.cloud.mqmgmt.networkservice.deletecall;

import org.json.JSONException;

public interface DeleteCall {
    public String deletePortFowardingApiCall(String token, String virtualIpId) throws JSONException;
    public String deleteFireWall(String token, String fireWallId) throws JSONException;
    public String deleteIpApiCall(String token);

}

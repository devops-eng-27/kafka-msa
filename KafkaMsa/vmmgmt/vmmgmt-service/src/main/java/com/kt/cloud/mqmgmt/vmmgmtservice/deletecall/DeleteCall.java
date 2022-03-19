package com.kt.cloud.mqmgmt.vmmgmtservice.deletecall;

import org.json.JSONException;

public interface DeleteCall {
    public String deleteServerApiCall(String token, String serverId);
    public String deleteKeyApiCall(String token, String keyName);
}

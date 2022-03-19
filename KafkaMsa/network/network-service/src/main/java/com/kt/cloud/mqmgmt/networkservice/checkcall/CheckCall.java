package com.kt.cloud.mqmgmt.networkservice.checkcall;

import org.json.JSONException;
import org.springframework.util.MultiValueMap;

public interface CheckCall {
    public String checkNetworkApiCall(String token, String job_id) throws JSONException;
    public MultiValueMap<String, String> getPortFowardingApiCall(String token, String prvip) throws JSONException;
    public MultiValueMap<String, String> getFireWallApiCall(String token) throws JSONException;
    public void checkJobId(String token, String jobId) throws InterruptedException, JSONException;
}

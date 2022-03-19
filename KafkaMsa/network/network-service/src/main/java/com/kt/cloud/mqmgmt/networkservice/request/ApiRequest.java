package com.kt.cloud.mqmgmt.networkservice.request;

public interface ApiRequest {
    public String ApiRequestPortForwarding(String token, String vmGuestIp, String entPublicIpId, String privatePort, String publicPort);
    public String ApiRequestFireWall(String token, String portId, String publicPort);
    public String ApiRequestWithoutBody(String token, String URI);
    public String ApiDeleteFireWall(String token, String URI);
}

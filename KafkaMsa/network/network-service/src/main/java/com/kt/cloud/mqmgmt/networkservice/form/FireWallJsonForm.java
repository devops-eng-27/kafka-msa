package com.kt.cloud.mqmgmt.networkservice.form;

import lombok.Data;

@Data
public class FireWallJsonForm {
    private String virtualIpId;
    private String publicPort;
    private String jsonStr;

    public FireWallJsonForm(String virtualIpId, String publicPort) {
        this.virtualIpId = virtualIpId;
        this.publicPort = publicPort;

        jsonStr = "{" +
                "\"virtualipid\": " + "\"" + virtualIpId + "\"," +
                "\"action\":  \"allow\"," +
                "\"srcnetworkid\":  \"1064f9d6-c8b5-40e5-90c2-afa35b05e34a\"," +
                "\"srcip\":  \"all\"," +
                "\"dstnetworkid\":  \"50fc763f-4569-4e40-ba1a-e2c03bd108d4\"," +
                "\"protocol\":  \"TCP\"," +
                "\"dstip\":  \"211.184.188.39/32\"," +
                "\"startport\": " + "\"" + publicPort + "\"," +
                "\"endport\": " + "\"" + publicPort + "\"," +
                "}";
    }
}

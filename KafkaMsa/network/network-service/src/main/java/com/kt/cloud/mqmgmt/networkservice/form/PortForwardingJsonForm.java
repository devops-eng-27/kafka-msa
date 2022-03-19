package com.kt.cloud.mqmgmt.networkservice.form;

import lombok.Data;

@Data
public class PortForwardingJsonForm {
    private String vmGuestIp;
    private String entPublicIpId;
    private String privatePort;
    private String publicPort;
    private String jsonStr;


    public PortForwardingJsonForm(String vmGuestIp, String entPublicIpId, String privatePort, String publicPort ) {
        this.vmGuestIp = vmGuestIp;
        this.entPublicIpId = entPublicIpId;
        this.privatePort = privatePort;
        this.publicPort = publicPort;

        jsonStr = "{" +
                "\"vmguestip\": " + "\"" + vmGuestIp + "\"," +
                "\"entpublicipid\": " + "\"" + entPublicIpId + "\"," +
                "\"privateport\": " + "\"" + privatePort + "\"," +
                "\"publicport\": " + "\"" + publicPort + "\"," +
                "\"protocol\": \"TCP\"," +
                "}";
    }
}

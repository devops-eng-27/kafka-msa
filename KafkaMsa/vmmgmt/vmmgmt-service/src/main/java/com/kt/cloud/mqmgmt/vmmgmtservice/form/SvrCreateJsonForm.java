package com.kt.cloud.mqmgmt.vmmgmtservice.form;

import lombok.Data;

@Data
public class SvrCreateJsonForm {
    private String svrName;
    private String jsonStr;

    public SvrCreateJsonForm(String svrName, String keyName, String flavorRef, String availabilityZone) {
        this.svrName = svrName;
        this.jsonStr = "{" +
                          "\"server\": {" +
                          "\"name\": " + "\"" + svrName + "\"" + "," +
                          "\"key_name\": " + "\"" + keyName + "\"" + "," +
                          "\"flavorRef\": " + "\"" + flavorRef + "\"" + "," +
                          "\"availability_zone\": " + "\"" + availabilityZone + "\"" + "," +
                          "\"networks\": [" +
                            "{" +
                                "\"uuid\": \"50fc763f-4569-4e40-ba1a-e2c03bd108d4\"" +
                            "}" +
                            "]," +
                         "\"block_device_mapping_v2\": [" +
                         "{" +
                            "\"destination_type\": \"volume\"," +
                            "\"boot_index\": \"0\"," +
                            "\"source_type\": \"image\"," +
                            "\"volume_size\": 50," +
                            "\"uuid\": \"caf1b9ce-9383-4870-9f27-268f1655f34e\"" +
                         "}" +
		                "]" +
                    "}" +
                "}";
    }
}

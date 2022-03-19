package com.kt.cloud.mqmgmt.vmmgmtservice.form;

import lombok.Data;

@Data
public class KeyPairJsonForm {
    private String keyPairName;
    private String jsonStr;

    public KeyPairJsonForm(String keyPairName) {

        this.keyPairName = keyPairName;
        this.jsonStr = "{\n" +
                "\t\"keypair\": {\n" +
                "\t\t\t\t\t\"name\": \"" + keyPairName + "\"\n" +
                "\t}\n" +
                "}";
    }
}

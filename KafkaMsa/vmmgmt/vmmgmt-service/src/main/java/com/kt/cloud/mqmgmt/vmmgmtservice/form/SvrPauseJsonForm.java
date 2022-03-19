package com.kt.cloud.mqmgmt.vmmgmtservice.form;

import lombok.Data;

@Data
public class SvrPauseJsonForm {
    private String jsonStr;

    public SvrPauseJsonForm() {
        this.jsonStr = "{" +
                "\"shelve\": \"null\"" +
                "}";
    }
}

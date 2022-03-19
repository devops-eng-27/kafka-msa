package com.kt.cloud.mqmgmt.tokenservice;

import lombok.Data;

@Data
public class JsonForm {
    private String userId;
    private String userPw;
    private String jsonStr;

    public JsonForm(String userId, String userPw) {

        this.userId = userId;
        this.userPw = userPw;

        this.jsonStr = "{\n" +
                "\t\"auth\": {\n" +
                "\t\t\"identity\": {\n" +
                "\t\t\t\"methods\": [\n" +
                "\t\t\t\t\"password\"\n" +
                "\t\t\t],\n" +
                "\t\t\t\"password\": {\n" +
                "\t\t\t\t\"user\": {\n" +
                "\t\t\t\t\t\"domain\": {\n" +
                "\t\t\t\t\t\t\"id\": \"default\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"name\": \"" + userId + "\",\n" +
                "\t\t\t\t\t\"password\": \"" + userPw + "\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"scope\": {\n" +
                "\t\t\t\"project\": {\n" +
                "\t\t\t\t\"domain\": {\n" +
                "\t\t\t\t\t\"id\": \"default\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"name\": \"" + userId + "\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
    }
}

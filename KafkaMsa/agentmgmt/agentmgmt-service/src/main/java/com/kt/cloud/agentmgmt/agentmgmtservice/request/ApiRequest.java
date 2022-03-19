package com.kt.cloud.agentmgmt.agentmgmtservice.request;

import java.util.ArrayList;

public interface ApiRequest {
    public void containerApi(ArrayList<String> service, String control);
    public void containerList();
}

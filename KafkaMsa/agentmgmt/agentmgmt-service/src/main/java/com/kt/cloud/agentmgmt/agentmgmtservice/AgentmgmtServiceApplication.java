package com.kt.cloud.agentmgmt.agentmgmtservice;

import com.kt.cloud.agentmgmt.agentmgmtservice.request.ApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Slf4j
public class AgentmgmtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentmgmtServiceApplication.class, args);
    }

    @Autowired
    private ApiRequest apiRequest;

    @PostMapping("/agentmgmt/control")
    public void containerRequest(@RequestBody MultiValueMap<String, String> params) {

        // 전달받은 변수를 control 변수에 추가
        String control = params.getFirst("control");

        // service parameter에 전달 받은 서비스 추가
        ArrayList<String> service = new ArrayList<String>();
        service.add(params.getFirst("kafka"));
        service.add(params.getFirst("zookeeper"));
        service.add(params.getFirst("kafdrop"));

        // container 조절 api 전달
        apiRequest.containerApi(service, control);
        apiRequest.containerList();

    }

}

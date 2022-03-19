package com.kt.cloud.mqmgmt.networkservice.checkcall;

import com.kt.cloud.mqmgmt.networkservice.request.ApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Service("CheckCall")
@Slf4j
public class CheckCallImpl implements CheckCall {

    @Autowired
    private ApiRequest apiRequest;

    @Autowired
    private CheckCall checkCall;

    // 네트워크 설정 진행 중 job id 확인
    public String checkNetworkApiCall(String token, String jobId) throws JSONException {

        String URI = "https://api.ucloudbiz.olleh.com/d1/nc/Etc?command=queryAsyncJob&jobid=" + jobId;

        String restEntity = apiRequest.ApiRequestWithoutBody(token, URI);

        JSONObject jsonObject = new JSONObject(restEntity);
        JSONObject ncObject = jsonObject.getJSONObject("nc_queryasyncjobresultresponse");
        String state = ncObject.getString("state");

        log.info("state :" + state);
        return state;

    }

    // 포트 포워딩 조회
    public MultiValueMap<String, String> getPortFowardingApiCall(String token, String vmGuestIp) throws JSONException {
        String privateIp;
        String publicPort;
        String virtualIpId;
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        int cnt =0;

        String URI = "https://api.ucloudbiz.olleh.com/d1/nc/Portforwarding";

        String restEntity = apiRequest.ApiRequestWithoutBody(token, URI);

        JSONObject jsonObject = new JSONObject(restEntity);
        JSONObject ncObject = jsonObject.getJSONObject("nc_listportforwardingrulesresponse");
        JSONArray portObject = ncObject.getJSONArray("portforwardingrule");

        for (int i=0; i<portObject.length(); i++) {
            JSONObject obj = portObject.getJSONObject(i);
            privateIp = obj.getString("vmguestip");

            // privateIp = vmGuestIp 라면
            if (Objects.equals(privateIp, vmGuestIp)) {

                publicPort = obj.getString("publicendport");
                virtualIpId = obj.getString("id"); // Portforwarding ID

                param.add("publicPort"+cnt, publicPort);
                param.add("virtualIpId"+cnt, virtualIpId);

                cnt++;
            }
        }

        log.info("IP :" + param);

        return param;
    }

    // 방화벽 조회
    public MultiValueMap<String, String> getFireWallApiCall(String token) throws JSONException {

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("ipport1","PF_211.184.188.39_10002_TCP");
        param.add("ipport2","PF_211.184.188.39_9000_TCP");
        param.add("ipport3","PF_211.184.188.39_9092_TCP");
        param.add("ipport4","PF_211.184.188.39_4243_TCP");

        MultiValueMap<String, String> fireWallId = new LinkedMultiValueMap<>();
        String Id;
        String dstIpPort;
        int cnt =0;

        String URI = "https://api.ucloudbiz.olleh.com/d1/nc/Firewall";

        String restEntity = apiRequest.ApiRequestWithoutBody(token, URI);

        JSONObject jsonObject = new JSONObject(restEntity);
        JSONObject ncObject = jsonObject.getJSONObject("nc_listfirewallrulesresponse");
        JSONArray wallObject = ncObject.getJSONArray("firewallrules");
        JSONObject fireObject = wallObject.getJSONObject(0);
        JSONArray aclsObject = fireObject.getJSONArray("acls");

        for (int i=0; i<aclsObject.length(); i++) {
            JSONObject obj = aclsObject.getJSONObject(i);
            JSONArray dstObject = obj.getJSONArray("dstaddrs");
            JSONObject ipObject = dstObject.getJSONObject(0);
            dstIpPort = ipObject.getString("ip");

            // 방화벽을 기본 4가지 설정하므로
            for (int j=1; j<4; j++) {

                if (Objects.equals(param.getFirst("ipport"+j), dstIpPort)) {
                    log.info(dstIpPort);
                    Id = obj.getString("id"); // Firewall ID

                    fireWallId.add("fireWallId"+cnt, Id);

                    cnt++;
                }
            }

        }

        log.info("IP :" + fireWallId);

        return fireWallId;
    }

    // job id가 state가 1(success)인지 확인
    public void checkJobId(String token, String jobId) throws InterruptedException, JSONException {

        String state="0";

        while (Objects.equals(state,"0") || Objects.equals(state,"2")) {
            state = checkCall.checkNetworkApiCall(token, jobId);
            log.info(state);
            // 10초 sleep (부하 줄이기 위해)
            Thread.sleep(1000);

            // state=1이면 param (virtualIpId, publicPort) 가지고 오기
            if (Objects.equals(state, "1")) {
                break;
            }
        }
    }

}

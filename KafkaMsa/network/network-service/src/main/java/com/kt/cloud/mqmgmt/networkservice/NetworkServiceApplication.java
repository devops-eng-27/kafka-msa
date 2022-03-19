package com.kt.cloud.mqmgmt.networkservice;

import com.kt.cloud.mqmgmt.networkservice.apicall.ApiCall;
import com.kt.cloud.mqmgmt.networkservice.checkcall.CheckCall;
import com.kt.cloud.mqmgmt.networkservice.deletecall.DeleteCall;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Slf4j
public class NetworkServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkServiceApplication.class, args);
	}

	@Autowired
	private ApiCall apiCall;

	@Autowired
	private CheckCall checkCall;

	@Autowired
	private DeleteCall deleteCall;

	MultiValueMap<String, String> param = null;

	// 네트워크 설정 (port forwarding, 방화벽)
	@PostMapping("/net/setip")
	public void setPortForwarding(@RequestBody MultiValueMap<String, String> params) throws JSONException, InterruptedException {
		String token = params.getFirst("token");
		String entPublicIpId = params.getFirst("entPublicIpId"); // Public IP ID
		String vmGuestIp = params.getFirst("vmGuestIp"); // Private IP

		String jobId;
		String state="0";

		log.info(String.valueOf(params));

		// Portforwarding 설정
		apiCall.setPortFowardingApiCall( token, vmGuestIp, entPublicIpId, "22", "10002");
		apiCall.setPortFowardingApiCall( token, vmGuestIp, entPublicIpId, "9000", "9000");
		apiCall.setPortFowardingApiCall( token, vmGuestIp, entPublicIpId, "9092", "9092");
		jobId = apiCall.setPortFowardingApiCall( token, vmGuestIp, entPublicIpId, "4243", "4243");

		// Portforwarding 설정 후 state 확인 (state=1이면 break)
		checkCall.checkJobId(token, jobId);

		// param (virtualIpId, publicPort) 가지고 오기
		param = checkCall.getPortFowardingApiCall(token, vmGuestIp);

		// jobId 초기화
		jobId = null;

		// Firewall 설정
		if (Objects.nonNull(param)) {
			for (int i=0; i < 4; i++) {
				jobId = apiCall.setFireWall(token, param.getFirst("virtualIpId"+i), param.getFirst("publicPort"+i));
			}
		} else {
			log.info("Param is NULL");
		}

		// Portforwarding 설정 후 state 확인 (state=1이면 break)
		checkCall.checkJobId(token, jobId);

	}

	// 네트워크 해제 
	@PostMapping("/net/deleteip")
	public void DeletePortForwarding(@RequestBody MultiValueMap<String, String> params) throws JSONException, InterruptedException {
		String token;
		String userId = params.getFirst("userId");
		String userPw = params.getFirst("userPw");
		String vmGuestIp = params.getFirst("vmGuestIp");

		MultiValueMap<String, String> fireWallId = null;
		String jobId = null;

		// token 불러오기
		token = apiCall.getTokenApiCall(userId, userPw);

		log.info(token);

		// Firewall 조회 후 ID 찾기
		fireWallId = checkCall.getFireWallApiCall(token);

		// Firewall 삭제
		if (Objects.nonNull(fireWallId)) {
			for (int i = 0; i < 4; i++) {
				jobId = deleteCall.deleteFireWall(token, fireWallId.getFirst("fireWallId"+i));
			}
		} else {
			log.info("fireWallId is NULL");
		}

		// Firewall 삭제 후 state 확인 (state=1이면 break)
		checkCall.checkJobId(token, jobId);

		// param (virtualIpId, publicPort) 가지고 오기
		param = checkCall.getPortFowardingApiCall(token, vmGuestIp);

		// jobId 초기화
		jobId = null;

		// Portforwarding 삭제
		if (Objects.nonNull(param)) {
			for (int i = 0; i < 4; i++) {
				jobId = deleteCall.deletePortFowardingApiCall(token, param.getFirst("virtualIpId"+i));
			}
		} else {
			log.info("fireWallId is NULL");
		}

		// Portforwarding 삭제 후 state 확인 (state=1이면 break)
		checkCall.checkJobId(token, jobId);

		// 서버 삭제를 위해 vmmgmt로 api 전달
		deleteCall.deleteIpApiCall(token);

	}
}

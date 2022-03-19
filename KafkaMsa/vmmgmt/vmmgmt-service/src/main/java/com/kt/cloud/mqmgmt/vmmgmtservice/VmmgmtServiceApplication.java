package com.kt.cloud.mqmgmt.vmmgmtservice;

import com.kt.cloud.mqmgmt.vmmgmtservice.apicall.ApiCall;
import com.kt.cloud.mqmgmt.vmmgmtservice.deletecall.DeleteCall;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Slf4j
public class VmmgmtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VmmgmtServiceApplication.class, args);
	}

	@Autowired
	private ApiCall apiCall;

	@Autowired
	private DeleteCall deleteCall;

	// 글로벌 변수
	String serverId;
	String keyName;

	// VM 생성
	@PostMapping("/mqsvr/create")
	public String mqSvrCreate(@RequestBody MultiValueMap<String, String> params) throws JSONException, InterruptedException {
        String token;

		String vmGuestIp = null; // Private IP
		keyName = params.getFirst("name");
		String svrName = params.getFirst("name");
		String userId = params.getFirst("userId");
		String userPw = params.getFirst("userPw");
		String flavorRef = params.getFirst("cpu"); //cpu core
		String availabilityZone = params.getFirst("location"); //zone
		String entPublicIpId = params.getFirst("ip"); // Public IP ID
		//userid, userpw 변수 선언및 param 인자값 할당

		log.info(String.valueOf(params));

		// token 불러오기
		token = apiCall.getTokenApiCall(userId, userPw);

		// keypair 가지고 오기
		apiCall.getKeyPairApiCall(token, keyName);

		// server 생성 후 ID 변수 받기
		serverId = apiCall.createSvrApiCall(token, keyName, svrName, flavorRef, availabilityZone);

		while (Objects.equals(vmGuestIp,null)) {
			// GuestIP (PrivateIP) 생성될 때까지 대기
			vmGuestIp = apiCall.getServerIpApiCall(token, serverId);
//			log.info(vmGuestIp);
			Thread.sleep(5000);

			if (Objects.nonNull(vmGuestIp)) {
				apiCall.setIpApiCall(token, entPublicIpId, vmGuestIp);
				break;
			}
		}

		return vmGuestIp;
	}

	// VM 삭제
	@PostMapping("/mqsvr/delete")
	public void mqSvrDelete(@RequestBody MultiValueMap<String, String> params) {
		String token = params.getFirst("token");

		log.info(token);

		// 서버 삭제
		deleteCall.deleteServerApiCall(token, serverId);

		// 키 삭제
		deleteCall.deleteKeyApiCall(token, keyName);
	}
}
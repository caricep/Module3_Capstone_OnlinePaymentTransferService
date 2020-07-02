package com.techelevator.tenmo.services;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.models.Transfer;

public class ApiTransferService implements TransferDAO {
	
	private static String authToken = "";
	private String baseUrl;
	private RestTemplate restTemplate;
	
	public ApiTransferService(String baseUrl) {
		this.baseUrl = baseUrl;
		restTemplate = new RestTemplate();
	}
	

	@Override
	public Transfer createTransfer(Transfer transfer) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		HttpEntity entity = new HttpEntity<>(headers);
		
		return restTemplate.exchange(baseUrl + "/transfers", HttpMethod.POST, entity, Transfer.class).getBody();
	}
		

	@Override
	public List<Transfer> getListOfTransfers() {
		// TODO Auto-generated method stub
		return null;
	}

}

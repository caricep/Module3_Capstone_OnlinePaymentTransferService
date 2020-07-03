package com.techelevator.tenmo.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.models.Account;
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
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(authToken);
		HttpEntity<Transfer> entity = new HttpEntity<Transfer>(transfer, headers);
		
		return restTemplate.exchange(baseUrl + "/transfers", HttpMethod.POST, entity, Transfer.class).getBody();
	}
		

	@Override
	public List<Transfer> getListOfTransfersByAccountId(int accountId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		HttpEntity entity = new HttpEntity<>(headers);
		
		Transfer[] transfersArray = restTemplate.exchange(baseUrl + "accounts/" + accountId + "/transfers", HttpMethod.GET, entity, Transfer[].class).getBody();
		List<Transfer> listOfTransfers = Arrays.asList(transfersArray);
		return listOfTransfers;
	}

}

package com.techelevator.tenmo.api;

import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.dao.AccountDAO;

public class ApiAccountDAO implements AccountDAO {

	private String baseUrl;
	private RestTemplate restTemplate;
	
	public ApiAccountDAO(String baseUrl) {
		this.baseUrl = baseUrl;
		restTemplate = new RestTemplate();
	}
	
	@Override
	public double getAccountBalanceByUserId(Integer userId) {
		return restTemplate.getForObject(baseUrl + "/users" + userId + "/accounts", double.class);
	}

}

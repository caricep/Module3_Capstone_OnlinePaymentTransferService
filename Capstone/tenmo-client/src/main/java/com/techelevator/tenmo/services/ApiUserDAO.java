package com.techelevator.tenmo.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.models.User;

public class ApiUserDAO implements UserDAO {

	private static String authToken = "";
	private String baseUrl;
	private RestTemplate restTemplate;
	
	public ApiUserDAO(String baseUrl) {
		this.baseUrl = baseUrl;
		restTemplate = new RestTemplate();
	}

	@Override
	public List<User> findAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		HttpEntity entity = new HttpEntity<>(headers);
		
		User[] userArray = restTemplate.exchange(baseUrl + "users", HttpMethod.GET, entity, User[].class).getBody();
		List<User> listOfUsers = Arrays.asList(userArray);
		return listOfUsers;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findIdByUsername(String username) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean create(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}

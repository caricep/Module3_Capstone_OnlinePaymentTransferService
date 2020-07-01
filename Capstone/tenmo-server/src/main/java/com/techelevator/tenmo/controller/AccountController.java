package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

	@Autowired
	private AccountDAO accountDAO;
	private UserDAO userDAO;
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "accounts/{id}", method = RequestMethod.GET)
	public double getAccountBalance(@PathVariable("id") Long userId){
		return accountDAO.getAccountBalanceByUserId(userId);
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "users", method = RequestMethod.GET)
	public List<User> listOfUsers() {
		List<User> users = userDAO.findAll();
		for (User user : users) {
			users.add(user);
		
		}return users;
	}
}

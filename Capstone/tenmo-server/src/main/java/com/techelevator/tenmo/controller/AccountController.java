package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.Account;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

	@Autowired
	private AccountDAO accountDAO;
	
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/users/{id}/accounts", method = RequestMethod.GET)
	public double getAccountBalance(@PathVariable("id") Long userId){
		return accountDAO.getAccountBalanceByUserId(userId);
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/accounts/{id}", method = RequestMethod.GET)
	public int getAccountId(@PathVariable("id") Long userId){
		return accountDAO.getAccountIdByUserId(userId);
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/accounts", method = RequestMethod.GET)
	public List<Account> listOfUserAccounts() {
		return accountDAO.getListOfUserAccounts();	
	}
	
	
	
}

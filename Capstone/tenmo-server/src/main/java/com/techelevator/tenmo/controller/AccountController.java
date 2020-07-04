package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	
	@RequestMapping(path = "/users/{id}/accounts", method = RequestMethod.GET)
	public double getAccountBalance(@PathVariable("id") Long userId){
		return accountDAO.getAccountBalanceByUserId(userId);
	}
	
	@RequestMapping(path = "/accounts/{id}", method = RequestMethod.GET)
	public int getAccountId(@PathVariable("id") Long userId){
		return accountDAO.getAccountIdByUserId(userId);
	}
	
	@RequestMapping(path = "/accounts", method = RequestMethod.GET)
	public List<Account> listOfUserAccounts() {
		return accountDAO.getListOfUserAccounts();	
	}
	
	@RequestMapping(path = "/accounts", method = RequestMethod.PUT)
	public Account withdrawMoneyForTransfer(@RequestBody Account account) {
		return accountDAO.withdrawMoneyForTransfer(account);
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/accounts/{id}", method = RequestMethod.PUT)
	public Account depositMoneyForTransfer(@RequestBody Account account, @PathVariable("id") int accountToId) {
		return accountDAO.depositMoneyForTransfer(account, accountToId);
	}
	
}

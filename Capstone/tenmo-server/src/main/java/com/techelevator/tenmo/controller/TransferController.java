package com.techelevator.tenmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

	@Autowired
	private AccountDAO accountDAO;
	
	@RequestMapping(path = "users/{id}/accounts", method = RequestMethod.GET)
	public double getAccountBalance(@PathVariable Long userId){
		return accountDAO.getAccountBalanceByUserId(userId);
	}

}

package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {

	public double getAccountBalanceByUserId(Long userId);
	public int getAccountIdByUserId(Long userId);
	public List<Account> getListOfUserAccounts();
	
}

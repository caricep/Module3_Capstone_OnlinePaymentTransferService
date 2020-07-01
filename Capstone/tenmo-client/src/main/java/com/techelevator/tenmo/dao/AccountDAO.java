package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.models.Account;

public interface AccountDAO {

	public double getAccountBalanceByUserId(Integer userId);
	public List<Account> getListOfUserAccounts();
}

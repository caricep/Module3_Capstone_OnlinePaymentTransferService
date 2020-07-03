package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.models.Account;

public interface AccountDAO {

	public double getAccountBalanceByUserId(int userId);
	public int getAccountIdByUserId(int userIdSender);
	public List<Account> getListOfUserAccounts();
	public Account withdrawMoneyForTransfer(Account account);
}

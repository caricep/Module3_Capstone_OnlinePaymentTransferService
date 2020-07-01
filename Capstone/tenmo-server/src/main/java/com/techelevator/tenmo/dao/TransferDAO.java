package com.techelevator.tenmo.dao;

public interface TransferDAO {

	public void makeTransfer(int userIdSender, int userIdRecipient, int accountFrom, int accountTo, double transferAmount);
	
}

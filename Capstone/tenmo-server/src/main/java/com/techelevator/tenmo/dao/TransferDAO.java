package com.techelevator.tenmo.dao;

public interface TransferDAO {

	public void makeTransfer(int accountFrom, int accountTo, double transferAmount);
	
}

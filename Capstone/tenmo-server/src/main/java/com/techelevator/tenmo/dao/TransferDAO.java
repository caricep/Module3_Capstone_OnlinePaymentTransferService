package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	public Transfer createTransfer(int accountFrom, int accountTo, double transferAmount);
	public List<Transfer> getListOfTransfers();
}

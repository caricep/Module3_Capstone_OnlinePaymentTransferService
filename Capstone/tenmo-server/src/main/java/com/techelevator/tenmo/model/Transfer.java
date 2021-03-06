package com.techelevator.tenmo.model;

public class Transfer {

	private int transferId;
	private int transferTypeId;
	private int transferStatusId;
	private int userIdSender;
	private int userIdRecipient;
	private String usernameSender;
	private String usernameRecipient;
	private int accountFrom;
	private int accountTo;
	private double transferAmount;
	
	public int getTransferId() {
		return transferId;
	}
	
	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}
	
	public int getTransferTypeId() {
		return transferTypeId;
	}
	
	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}
	
	public int getTransferStatusId() {
		return transferStatusId;
	}
	
	public void setTransferStatusId(int transferStatusId) {
		this.transferStatusId = transferStatusId;
	}
	
	public int getAccountFrom() {
		return accountFrom;
	}
	
	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}
	
	public int getAccountTo() {
		return accountTo;
	}
	
	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}
	
	public double getTransferAmount() {
		return transferAmount;
	}
	
	public void setTransferAmount(double transferAmount) {
		this.transferAmount = transferAmount;
	}

	public int getUserIdSender() {
		return userIdSender;
	}

	public void setUserIdSender(int userIdSender) {
		this.userIdSender = userIdSender;
	}

	public int getUserIdRecipient() {
		return userIdRecipient;
	}

	public void setUserIdRecipient(int userIdRecipient) {
		this.userIdRecipient = userIdRecipient;
	}

	public String getUsernameSender() {
		return usernameSender;
	}

	public void setUsernameSender(String usernameSender) {
		this.usernameSender = usernameSender;
	}

	public String getUsernameRecipient() {
		return usernameRecipient;
	}

	public void setUsernameRecipient(String usernameRecipient) {
		this.usernameRecipient = usernameRecipient;
	}
	
	
	
}

package com.techelevator.tenmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

	@Autowired
	private TransferDAO transferDAO;
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/transfers", method = RequestMethod.PUT)
	public Transfer createTransfer() {
		return transferDAO.createTransfer(accountFrom, accountTo, transferAmount);
	}
}

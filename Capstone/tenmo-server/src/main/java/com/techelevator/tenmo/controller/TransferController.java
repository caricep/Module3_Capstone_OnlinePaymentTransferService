package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;

//@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

	@Autowired
	private TransferDAO transferDAO;
	
	@RequestMapping(path = "/transfers", method = RequestMethod.GET)
	public List<Transfer> listTransfers() {
		return transferDAO.getListOfTransfers();
	}
	
	//@PreAuthorize("permitAll")
	@RequestMapping(path = "/transfers", method = RequestMethod.POST)
	public Transfer createTransfer(@RequestParam int accountFrom, int accountTo, double transferAmount) {
		return transferDAO.createTransfer(accountFrom, accountTo, transferAmount);
	}
}

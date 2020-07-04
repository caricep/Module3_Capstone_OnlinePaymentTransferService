package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@RequestMapping(path = "accounts/{id}/transfers", method = RequestMethod.GET)
	public List<Transfer> listTransfersByAccountId(@PathVariable("id") int accountId) {
		return transferDAO.getListOfTransfersByAccountId(accountId);
	}
	
	@RequestMapping(path = "/transfers", method = RequestMethod.POST)
	public Transfer createTransfer(@RequestBody Transfer transfer) {
		return transferDAO.createTransfer(transfer);
	}
}

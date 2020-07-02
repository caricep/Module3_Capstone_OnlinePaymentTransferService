package com.techelevator.tenmo.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

@Component
public class JDBCTransferDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

    public JDBCTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	@Override
	public Transfer createTransfer(int accountFrom, int accountTo, double transferAmount) {
		String insertTransferSql = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) "
				+ "VALUES (DEFAULT, 2, 2, ?, ?, ?) RETURNING transfer_id";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(insertTransferSql, accountFrom, accountTo, transferAmount);
		rows.next();
		int transferId = rows.getInt("transfer_id");
		
		String selectSql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount "
				+ "FROM transfers JOIN accounts ON accounts.account_id = transfers.account_from JOIN users ON users.user_id = accounts.user_id";
		SqlRowSet selectedRows = jdbcTemplate.queryForRowSet(selectSql, transferId);
		Transfer transfer = new Transfer();
		while(selectedRows.next()) {
			transfer.setTransferId(transferId);
			transfer.setTransferTypeId(selectedRows.getInt("transfer_type_id"));
			transfer.setTransferStatusId(selectedRows.getInt("transfer_status_id"));
			transfer.setAccountFrom(selectedRows.getInt("account_from"));
			transfer.setAccountTo(selectedRows.getInt("account_to"));
			transfer.setTransferAmount(selectedRows.getDouble("amount"));
			
		}
		return transfer;
	}
	

}

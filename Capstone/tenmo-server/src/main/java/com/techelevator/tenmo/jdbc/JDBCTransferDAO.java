package com.techelevator.tenmo.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;

public class JDBCTransferDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

    public JDBCTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	@Override
	public void makeTransfer(int accountFrom, int accountTo, double transferAmount) {
		String selectSql = "SELECT account_id, user_id, balance - ? FROM accounts WHERE account_id = ? AND balance > 0";
		
		SqlRowSet rows = jdbcTemplate.queryForRowSet(selectSql, transferAmount, accountFrom);
		while(rows.next()) {
			Transfer transfer = new Transfer();
			transfer.setAccountFrom(rows.getInt("account_from"));
			transfer.setAccountTo(rows.getInt("account_to"));
			transfer.setTransferAmount(rows.getDouble("amount"));
		}
		
		String updateSql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
		jdbcTemplate.update(updateSql, transferAmount, accountTo);
	}

	
}

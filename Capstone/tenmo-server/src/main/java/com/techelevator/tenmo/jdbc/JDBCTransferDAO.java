package com.techelevator.tenmo.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;

@Component
public class JDBCTransferDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCTransferDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Transfer> getListOfTransfersByAccountId(int accountId) {
		List<Transfer> listOfTransfers = new ArrayList<Transfer>();

		String selectSql = "\n" + 
				"SELECT transfer_id, transfer_type_id, transfer_status_id, transfers.account_from, user_sender.user_id AS sender_user_id, user_sender.username "
				+ "AS sender_username, transfers.account_to, user_recipient.user_id AS recipient_user_id, user_recipient.username AS recipient_username, amount "
				+ "FROM transfers JOIN accounts AS sender ON transfers.account_from = sender.account_id JOIN accounts AS recipient "
				+ "ON transfers.account_to = recipient.account_id JOIN users AS user_sender ON sender.user_id = user_sender.user_id JOIN users AS user_recipient "
				+ "ON recipient.user_id = user_recipient.user_id WHERE transfers.account_from = ? ORDER BY transfer_id";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(selectSql, accountId);

		while (rows.next()) {
			Transfer transfer = makeTransferFromRow(rows);
			listOfTransfers.add(transfer);
		}

		return listOfTransfers;
	}

	@Override
	public Transfer createTransfer(Transfer transfer) {
		String insertTransferSql = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) "
				+ "VALUES (DEFAULT, 2, 2, ?, ?, ?) RETURNING transfer_id";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(insertTransferSql, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getTransferAmount());

		rows.next();
		int transferId = rows.getInt("transfer_id");
		transfer.setTransferId(transferId);

		return transfer;
	}

	private Transfer makeTransferFromRow(SqlRowSet rows) {
		Transfer transfer = new Transfer();

		transfer.setTransferId(rows.getInt("transfer_id"));
		transfer.setTransferTypeId(rows.getInt("transfer_type_id"));
		transfer.setTransferStatusId(rows.getInt("transfer_status_id"));
		transfer.setAccountFrom(rows.getInt("account_from"));
		transfer.setUserIdSender(rows.getInt(5));
		transfer.setUsernameSender(rows.getString(6));
		transfer.setAccountTo(rows.getInt("account_to"));
		transfer.setUserIdRecipient(rows.getInt(8));
		transfer.setUsernameRecipient(rows.getString(9));
		transfer.setTransferAmount(rows.getDouble("amount"));

		return transfer;
	}

}

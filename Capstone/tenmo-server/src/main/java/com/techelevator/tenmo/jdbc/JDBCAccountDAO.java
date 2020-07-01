package com.techelevator.tenmo.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.Account;

@Component
public class JDBCAccountDAO implements AccountDAO {

	private JdbcTemplate jdbcTemplate;

    public JDBCAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	@Override
	public double getAccountBalanceByUserId(Long userId) {
		double accountBalance = jdbcTemplate.queryForObject("SELECT balance FROM accounts WHERE user_id = ?", double.class, userId);
		return accountBalance;
	}

	@Override
	public List<Account> getListOfUserAccounts() {
		List<Account> listOfUserAccounts = new ArrayList<Account>();
		
		String selectSql = "SELECT account_id, accounts.user_id, users.username, balance FROM accounts JOIN users ON accounts.user_id = users.user_id";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(selectSql);
		
		while (rows.next()) {
			Account account = makeAccountFromRow(rows);
			listOfUserAccounts.add(account);
		}
		return listOfUserAccounts;
	}
	
	private Account makeAccountFromRow(SqlRowSet rows) {
		Account account = new Account();
		
		account.setAccountId(rows.getInt("account_id"));
		account.setUserId(rows.getInt("user_id"));
		account.setUserName(rows.getString("username"));
		account.setAccountBalance(rows.getDouble("balance"));
		
		return account;
	}

}

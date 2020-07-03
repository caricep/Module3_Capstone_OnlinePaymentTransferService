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
	public int getAccountIdByUserId(Long userId) {
		int accountId = jdbcTemplate.queryForObject("SELECT account_id FROM accounts WHERE user_id = ?", int.class, userId);
		return accountId;
	}
	
	@Override
	public List<Account> getListOfUserAccounts() {
		List<Account> listOfUserAccounts = new ArrayList<Account>();
		
		String selectSql = "SELECT account_id, accounts.user_id, users.username, balance FROM accounts JOIN users ON accounts.user_id = users.user_id ORDER BY user_id";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(selectSql);
		
		while (rows.next()) {
			Account account = makeAccountFromRow(rows);
			listOfUserAccounts.add(account);
		}
		return listOfUserAccounts;
	}
	
	@Override
	public Account withdrawMoneyForTransfer(Account account) {
		String updateSql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
		jdbcTemplate.update(updateSql, account.getWithdrawalAmount(), account.getAccountId());
		
		return account;
	}
	
	@Override
	public Account depositMoneyForTransfer(Account account, int accountToId) {
		String updateSql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
		jdbcTemplate.update(updateSql, account.getDepositAmount(), account.getAccountId());
		
		return account;
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

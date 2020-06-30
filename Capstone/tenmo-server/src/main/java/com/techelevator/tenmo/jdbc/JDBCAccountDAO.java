package com.techelevator.tenmo.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.dao.AccountDAO;

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

}

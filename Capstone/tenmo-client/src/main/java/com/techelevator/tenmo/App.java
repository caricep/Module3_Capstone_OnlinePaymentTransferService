package com.techelevator.tenmo;

import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.ApiAccountService;
import com.techelevator.tenmo.services.ApiTransferService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN,
			MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS,
			MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS,
			MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;

	private AccountDAO accountDAO;
	private TransferDAO transferDAO;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
		
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		accountDAO = new ApiAccountService(API_BASE_URL, currentUser.getToken());
		transferDAO = new ApiTransferService(API_BASE_URL, currentUser.getToken());
		mainMenu();
	}

	private void mainMenu() {
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		System.out.println("Your current account balance is:" + " $"
				+ String.format("%.2f", accountDAO.getAccountBalanceByUserId(currentUser.getUser().getId())));
	}

	private void viewTransferHistory() {
		List<Transfer> transfers = new ArrayList<Transfer>();
		transfers.addAll(transferDAO.getListOfTransfersByAccountId(currentUser.getUser().getId()));

		System.out.println("-----------------------------------------------------");
		System.out.println("Transfers");
		System.out.println(String.format("%-10s%-16s%s", "ID", "From/To", "Amount"));
		System.out.println("-----------------------------------------------------");

		for (Transfer transfer : transfers) {
			
			if (transfer.getAccountFrom() != transfer.getAccountTo() && transfer.getAccountTo() == currentUser.getUser().getId()) {
				int transferTypeId = 1;
				transfer.setTransferTypeId(transferTypeId);
				
				String userSender = accountIdToUsernameConversion(transfer.getAccountFrom());
				
				int accountFromDifferentSender = transfer.getAccountFrom();
				transfer.setAccountFrom(accountFromDifferentSender);

				System.out.printf("%-10s%-6s%-10s%s", transfer.getTransferId(), transferTypeConversion(transfer.getTransferTypeId()), 
						userSender, "$" + String.format("%.2f", transfer.getTransferAmount()) + "\n");
			}
			
			if (transfer.getAccountFrom() != transfer.getAccountTo()
					&& transfer.getAccountTo() != currentUser.getUser().getId()) {
				int transferTypeId = 2;
				transfer.setTransferTypeId(transferTypeId);

				String userReceiver = accountIdToUsernameConversion(transfer.getAccountTo());

				System.out.printf("%-10s%-6s%-10s%s", transfer.getTransferId(), transferTypeConversion(transfer.getTransferTypeId()), 
						userReceiver, "$" + String.format("%.2f", transfer.getTransferAmount()) + "\n");
			}
		}
			

		System.out.println();
		System.out.print("Please enter transfer ID to view details (0 to cancel): ");

		int transferIdChoice = console.getTransferIdChoice();
		if (transferIdChoice == 0) {
			mainMenu();
		} else if (transferIdChoice != 0) {			
			viewTransferHistoryDetails(transferIdChoice);
		}
	}

	private void viewTransferHistoryDetails(int transferId) {
		List<Transfer> transfers = new ArrayList<Transfer>();
		transfers.addAll(transferDAO.getListOfTransfersByAccountId(currentUser.getUser().getId()));

		System.out.println();
		System.out.println("-----------------------------------------------------");
		System.out.println("Transfer Details");
		System.out.println("-----------------------------------------------------");

		for (Transfer transfer : transfers) {
			
			if (transfer.getTransferId() == transferId) {
			System.out.println("Id: " + transfer.getTransferId());
			
			if (transfer.getTransferTypeId() == 1) {
				System.out.println("From: " + accountIdToUsernameConversion(transfer.getAccountFrom()));
				System.out.println("To: " + currentUser.getUser().getUsername());
			}
			
			System.out.println("From: " + accountIdToUsernameConversion(transfer.getAccountFrom()));
			System.out.println("To: " + accountIdToUsernameConversion(transfer.getAccountTo()));
			System.out.println("Type: " + transferTypeToWordsConversion(transfer.getTransferTypeId()));
			System.out.println("Status: " + transferStatusConversion(transfer.getTransferStatusId()));
			System.out.println("Amount: $" + String.format("%.2f", transfer.getTransferAmount()));
			System.out.println();
			
			}
		}
	}

	private String transferTypeConversion(int transferTypeId) {

		if (transferTypeId == 1) {
			return "From: ";
		}
		if (transferTypeId == 2) {
			return "To: ";
		}
		return "";
	}

	private String transferTypeToWordsConversion(int transferTypeId) {

		if (transferTypeId == 1) {
			return "Request";
		}
		if (transferTypeId == 2) {
			return "Send";
		}
		return "";
	}

	private String accountIdToUsernameConversion(int userId) {

		if (userId == 1) {
			return "user";
		}
		if (userId == 2) {
			return "admin";
		}
		if (userId == 3) {
			return "test1";
		}
		return "";
	}

	private String transferStatusConversion(int transferStatusId) {

		if (transferStatusId == 1) {
			return "Pending";
		}
		if (transferStatusId == 2) {
			return "Approved";
		}
		if (transferStatusId == 3) {
			return "Rejected";
		}
		return "";
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub

	}

	private void sendBucks() {
		System.out.println("-----------------------------------------------------");

		List<Account> accounts = new ArrayList<Account>();
		accounts.addAll(accountDAO.getListOfUserAccounts());

		System.out.println("Users");
		System.out.println("ID" + "         " + "UserName");
		System.out.println("-----------------------------------------------------");

		for (Account account : accounts) {
			System.out.println(account.getUserId() + "          " + account.getUserName());
		}

		System.out.println();
		System.out.print("Enter ID of user you are sending to (0 to cancel): ");

		int userIdChoice = console.getUserIdChoice();
		if (userIdChoice == 0) {
			mainMenu();
		} else if (userIdChoice != 0) {			
			createTransfer(userIdChoice);
		}
		
	}

	private void createTransfer(int userIdChoice) {
		Transfer transfer = new Transfer();

		int userIdRecipient = userIdChoice;
		transfer.setUserIdRecipient(userIdRecipient);
		int userIdSender = currentUser.getUser().getId();
		transfer.setUserIdSender(userIdSender);

		int accountFrom = accountDAO.getAccountIdByUserId(userIdSender);
		transfer.setAccountFrom(accountFrom);
		int accountTo = accountDAO.getAccountIdByUserId(userIdRecipient);
		transfer.setAccountTo(accountTo);

		System.out.print("Enter amount: ");
		double transferAmount = console.getAmountChoice();
		transfer.setTransferAmount(transferAmount);

		transferDAO.createTransfer(transfer);
		
		
		Account withdrawFromAccount = new Account();
		
		double withdrawalAmount = transferAmount;
		withdrawFromAccount.setWithdrawalAmount(withdrawalAmount);
		int accountToWithdrawFrom = accountFrom;
		withdrawFromAccount.setAccountId(accountToWithdrawFrom);
		
		accountDAO.withdrawMoneyForTransfer(withdrawFromAccount);
		
		
		Account deposiToAccount = new Account();
		
		double depositAmount = transferAmount;
		deposiToAccount.setDepositAmount(depositAmount);
		int accountToDepositTo = accountTo;
		deposiToAccount.setAccountId(accountToDepositTo);
		
		accountDAO.depositMoneyForTransfer(deposiToAccount, accountToDepositTo);
		
		System.out.println();
		System.out.println("Your transfer is complete. What would you like to do next?");

	}

	private void requestBucks() {
		// TODO Auto-generated method stub

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) // will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) // will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}

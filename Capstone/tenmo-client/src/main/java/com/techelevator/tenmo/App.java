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
		accountDAO = new ApiAccountService(API_BASE_URL);
		transferDAO = new ApiTransferService(API_BASE_URL);
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
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
		transfers.addAll(transferDAO.getListOfTransfers());
		
		System.out.println("Transfers");
		System.out.println(String.format("%-10s%-10s%s", "ID", "From/To", "Amount"));
		System.out.println("-----------------------------------------------------");

		for (Transfer transfer : transfers) {
			System.out.printf("%-10s%-10s%s", transfer.getTransferId(), transferTypeConversion(transfer.getTransferTypeId()), "$" + String.format("%.2f", transfer.getTransferAmount()) + "\n");
		}

	}

	private String transferTypeConversion(int transferTypeId) {

		if (transferTypeId == 1) {
			return "From";
		}
		if (transferTypeId == 2) {
			return "To";
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
		System.out.println("Enter ID of user you are sending to (0 to cancel): ");
		
		Transfer transfer = new Transfer();
		
		int userIdRecipient = console.getUserIdChoice();
		transfer.setUserIdRecipient(userIdRecipient);
		int userIdSender = currentUser.getUser().getId();
		transfer.setUserIdSender(userIdSender);
		
		int accountFrom = accountDAO.getAccountIdByUserId(userIdSender);
		transfer.setAccountFrom(accountFrom);
		int accountTo = accountDAO.getAccountIdByUserId(userIdRecipient);
		transfer.setAccountTo(accountTo);
		
		System.out.println("Enter amount: ");
		double transferAmount = console.getAmountChoice();
		transfer.setTransferAmount(transferAmount);
		
		Transfer newTransfer = transferDAO.createTransfer(transfer);
		System.out.println(newTransfer.getTransferId() + newTransfer.getUserIdRecipient());
		System.out.println();
		
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

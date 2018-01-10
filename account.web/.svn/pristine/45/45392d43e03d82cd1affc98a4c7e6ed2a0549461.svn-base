package com.bliss.account.dev;

import com.bliss.account.manager.AccountManager;
import com.bliss.account.model.Account;
import com.bliss.account.model.Account.Role;
import com.bliss.account.model.Account.Status;

public class AccountDev {

	public static void main(String[] args) {
		try {
			Account account = new Account();
			account.setUserName("tuyen.pham");
			account.setPassWord("1234567");
			account.setRole(Role.USER);
			account.setStatus(Status.ACTIVED);
			
			AccountManager.getInstance().save(account);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

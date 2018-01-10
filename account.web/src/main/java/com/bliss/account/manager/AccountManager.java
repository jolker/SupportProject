package com.bliss.account.manager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bliss.account.jdbc.AccountDAO;
import com.bliss.account.jdbc.AccountDAOImpl;
import com.bliss.account.jdbc.DAFactory;
import com.bliss.account.model.Account;

public class AccountManager {
	private static AccountManager instance = null;
	public static AccountManager getInstance() {
		if (instance == null) {
			synchronized(AccountManager.class) {
				instance = new AccountManager();
			}
		}
		return instance;
	}

	AccountDAO accountDAO;

	public AccountManager() {
		accountDAO = AccountDAOImpl.getInstance();
	}

	public Account save(Account account) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = DAFactory.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();

			Account saved = getAccount(session, account.getUserName());
			if (saved != null)
				throw new Exception("userName exists in our database");

			// encodeBase64 password
			String passEncoded = Base64.encodeBase64String(account.getPassWord().getBytes());
			account.setPassWord(passEncoded);
			accountDAO.save(session, account);

			transaction.commit();

			return account;

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	private Account getAccount(Session session, String userName) {
		try {
			return accountDAO.find(session, userName);	
		} catch (Exception e) {
			return null;
		}
	}

	public Account update(Account account) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = DAFactory.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();

			Account locked = accountDAO.find(session, account.getId(), true);			
			// copy fields changed
			copyFieldsChange(account, locked);

			accountDAO.update(session, locked);

			transaction.commit();

			return locked;

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	private void copyFieldsChange(Account src, Account dest) {
		if (src.getExpiredAt() != null)
			dest.setExpiredAt(src.getExpiredAt());
		if (!StringUtils.equals(src.getRole().name(), dest.getRole().name()))
			dest.setRole(src.getRole());
	}

	public Account find(int accountId) throws Exception {
		Session session = null;
		try {
			session = DAFactory.getSessionFactory().openSession();
			return accountDAO.find(session, accountId, false);

		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public Account find(String userName, String password) throws Exception {
		Session session = null;
		try {
			session = DAFactory.getSessionFactory().openSession();
			
			String passEncoded = Base64.encodeBase64String(password.getBytes());
			return accountDAO.find(session, userName, passEncoded);

		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}

package com.bliss.account.jdbc;

import org.hibernate.Session;

import com.bliss.account.model.Account;

public interface AccountDAO {

	public Account save(Session session, Account account) throws Exception;

	public Account update(Session session, Account account) throws Exception;
	
	public Account find(Session session, String userName) throws Exception;

	public Account find(Session session, String userName, String password) throws Exception;
	
	public Account find(Session session, long id, boolean forUpdate) throws Exception;

}

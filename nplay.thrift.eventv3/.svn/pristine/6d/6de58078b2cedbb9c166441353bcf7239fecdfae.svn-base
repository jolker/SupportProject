package com.bliss.nplay.service.eventv3.thrift.client;

import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.bliss.nplay.service.eventv3.thrift.TEventV3;

import ga.log4j.GA;


public class PooledClient {

	private long dateCreated;

	private TEventV3.Client client;

	public PooledClient(String host, int port, int timeOut) {
		dateCreated = System.currentTimeMillis();
		TTransport transport = new TFramedTransport(new TSocket(host, port, timeOut));
		TProtocol protocol = new TBinaryProtocol(transport);
		try {
			transport.open();
			TServiceClientFactory<?> factory = new TEventV3.Client.Factory();
			client = (TEventV3.Client) factory.getClient(protocol);
		} catch (Exception ex) {
			GA.app.error("PooledClient " + ex.getMessage());
		}
	}

	public boolean isAlive() {
		if (client == null) {
			return false;
		}
		TFramedTransport transport = (TFramedTransport) client.getOutputProtocol().getTransport();
		if (transport == null || transport.isOpen() == false) {
			return false;
		}
		try {
			return client.ping();
		} catch (Exception ex) {
			return false;
		}
	}

	public void destroy() {        
		if (client == null) {
			return;
		}
		TTransport itrans = client.getInputProtocol().getTransport();
		TTransport otrans = client.getOutputProtocol().getTransport();
		if (itrans != null) {
			itrans.close();
		}
		if (otrans != null && otrans != itrans) {
			otrans.close();
		}
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public TEventV3.Client getClient() {
		return client;
	}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bliss.service.paymentv2.thrift.client;

import com.bliss.service.paymentv2.thrift.TPaymentV2;
import ga.log4j.GA;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author baotn
 */
public class PooledClient 
{
    private long dateCreated;

    private TPaymentV2.Client client;

    public PooledClient(String host, int port, int timeOut) 
    {
        dateCreated = System.currentTimeMillis();
        TTransport transport = new TFramedTransport(new TSocket(host, port, timeOut));
        TProtocol protocol = new TBinaryProtocol(transport);
        try 
        {
            transport.open();
            TServiceClientFactory<?> factory = new TPaymentV2.Client.Factory();
            client = (TPaymentV2.Client) factory.getClient(protocol);
        } catch (Exception ex) 
        {
            GA.app.error("PooledClient " + ex.getMessage());
        }
    }

    public boolean isAlive() 
    {
        if (client == null) 
        {
                return false;
        }
        TFramedTransport transport = (TFramedTransport) client.getOutputProtocol().getTransport();
        if (transport == null || transport.isOpen() == false) 
        {
                return false;
        }
        try 
        {
                return client.ping();
        } catch (Exception ex) 
        {
                return false;
        }
    }

    public void destroy() 
    {        
        if (client == null) 
        {
                return;
        }
        TTransport itrans = client.getInputProtocol().getTransport();
        TTransport otrans = client.getOutputProtocol().getTransport();
        if (itrans != null) 
        {
                itrans.close();
        }
        if (otrans != null && otrans != itrans) 
        {
                otrans.close();
        }
    }

    public long getDateCreated() 
    {
        return dateCreated;
    }

    public TPaymentV2.Client getClient() 
    {
        return client;
    }
}

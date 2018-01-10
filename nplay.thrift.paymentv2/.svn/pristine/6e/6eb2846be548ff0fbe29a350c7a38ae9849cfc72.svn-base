/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bliss.service.paymentv2.gearman;

import com.bliss.framework.gearman.GClientManager;
import com.bliss.framework.gearman.JobEnt;
import com.bliss.framework.util.JSONUtil;
import com.bliss.service.paymentv2.constant.BackEndTask;
import com.bliss.service.paymentv2.thrift.TInvoiceValue;
import ga.log4j.GA;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.cliffc.high_scale_lib.NonBlockingHashMap;

/**
 *
 * @author baotn
 */
public class PaymentV2Client
{    
    private static Map<String, PaymentV2Client> instances = new NonBlockingHashMap();
    private static final Lock createLock = new ReentrantLock();

    public static PaymentV2Client getInstance(String name) 
    {
        if (!instances.containsKey(name)) 
        {
            try 
            {
                createLock.lock();
                if (!instances.containsKey(name)) 
                {
                    instances.put(name, new PaymentV2Client(name));
                }
            } finally 
            {
                createLock.unlock();
            }
        }
        return (PaymentV2Client) instances.get(name);
    }

    private GClientManager gclient;

    private PaymentV2Client(String config) 
    {
        gclient = GClientManager.getInstance(config);
    }
    
    public void processPayment(TInvoiceValue invoice)
    {
        try 
        {
            JobEnt job = new JobEnt();
            job.ActionType = (short) BackEndTask.PROCESS_PAYMENT.getValue();
            job.Data = JSONUtil.json.toJson(invoice);
            boolean chk = gclient.push(job);
            if (!chk) 
            {
                GA.app.info("Push job to gearman fail:" + job);
            }
        } catch (Exception ex) 
        {
            GA.app.error(PaymentV2Client.class.getName(), ex);
        }
    }
}

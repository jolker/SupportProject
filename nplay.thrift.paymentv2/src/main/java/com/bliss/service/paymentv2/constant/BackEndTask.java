/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bliss.service.paymentv2.constant;

/**
 *
 * @author baotn
 */
public enum BackEndTask 
{
    PROCESS_PAYMENT(0);

    private final int value;

    private BackEndTask(int value) 
    {
      this.value = value;
    }

    public int getValue() 
    {
      return value;
    }

    public static BackEndTask findByValue(int value) 
    { 
      switch (value) 
      {
        case 0:
          return PROCESS_PAYMENT;
        default:
          return null;
      }
    }
}
package com.bsl.service;

public interface PaymentService {

    String pay(String amount,String mode,
               String sender, String receiver);
}

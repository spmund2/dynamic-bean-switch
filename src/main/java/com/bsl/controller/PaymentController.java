package com.bsl.controller;

import com.bsl.dto.PaymentRequest;
import com.bsl.service.impl.PaypalPaymentService;
import com.bsl.service.impl.RazorpayPaymentService;
import com.bsl.service.impl.StripePaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    public static final String PAYPAL = "paypal";
    public static final String RAZORPAY = "razorpay";
    public static final String STRIPE = "stripe";

    private final PaypalPaymentService paypalPaymentService;
    private final RazorpayPaymentService razorpayPaymentService;
    private final StripePaymentService stripePaymentService;

    public PaymentController(PaypalPaymentService paypalPaymentService,
                             RazorpayPaymentService razorpayPaymentService,
                             StripePaymentService stripePaymentService) {
        this.paypalPaymentService = paypalPaymentService;
        this.razorpayPaymentService = razorpayPaymentService;
        this.stripePaymentService = stripePaymentService;
    }

    @PostMapping("/pay")
    public String pay(@RequestBody PaymentRequest paymentRequest) {

        String amount = paymentRequest.getAmount();
        String paymentType = paymentRequest.getPaymentType();
        String sender = paymentRequest.getSender();
        String receiver = paymentRequest.getReceiver();

        return switch (paymentType.toLowerCase()) {
            case PAYPAL ->
                    paypalPaymentService.pay(amount, paymentType, sender, receiver);
            case RAZORPAY ->
                    razorpayPaymentService.pay(amount, paymentType, sender, receiver);
            case STRIPE ->
                    stripePaymentService.pay(amount, paymentType, sender, receiver);
            default ->
                    throw new IllegalArgumentException("Unsupported payment mode: " + paymentType);
        };
    }
}

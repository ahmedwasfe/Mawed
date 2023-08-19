package com.raiyansoft.mawed.listener;

import com.raiyansoft.mawed.model.payment.paymentMethods.PaymentMethodData;

public interface PaymentMethodSelectListener {

    void onPaymentSelected(PaymentMethodData payment, int pos);
}

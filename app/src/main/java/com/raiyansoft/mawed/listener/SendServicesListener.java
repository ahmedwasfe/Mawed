package com.raiyansoft.mawed.listener;

import com.raiyansoft.mawed.model.userOrders.sendOrder.SendService;

import java.util.List;

public interface SendServicesListener {

    void onSendService(List<SendService> services, int pos, boolean isCheck);
}

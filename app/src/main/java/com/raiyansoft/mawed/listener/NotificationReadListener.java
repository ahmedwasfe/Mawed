package com.raiyansoft.mawed.listener;

import com.raiyansoft.mawed.model.notifications.NotificationData;

public interface NotificationReadListener {

    void onReadNotification(NotificationData notification, int pos);

}

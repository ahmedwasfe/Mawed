package com.raiyansoft.mawed.listener;


import androidx.appcompat.app.AlertDialog;

import com.raiyansoft.mawed.model.Language;

public interface SelectLanguageListener {

    void onSelectLanguageListener(Language language, int pos, AlertDialog dialog);
}

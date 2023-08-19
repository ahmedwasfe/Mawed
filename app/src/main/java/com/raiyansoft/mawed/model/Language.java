package com.raiyansoft.mawed.model;

public class Language {

    private String name;
    private String langCode;

    public Language(String name, String langCode) {
        this.name = name;
        this.langCode = langCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }
}

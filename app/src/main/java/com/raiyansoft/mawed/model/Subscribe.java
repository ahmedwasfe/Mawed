package com.raiyansoft.mawed.model;

public class Subscribe {

    private String name;
    private double price;
    private int background;
    private int icon;

    private int textColor;

    public Subscribe(String name, double price, int background, int icon, int textColor) {
        this.name = name;
        this.price = price;
        this.background = background;
        this.icon = icon;
        this.textColor = textColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}

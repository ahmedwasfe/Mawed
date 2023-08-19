package com.raiyansoft.mawed.model.calendar;

import java.util.ArrayList;
import java.util.List;

public class Calendar {

    private String currentDay;

    private String currentDate;

    private List<Events> events = new ArrayList<>();

    public String getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }
}

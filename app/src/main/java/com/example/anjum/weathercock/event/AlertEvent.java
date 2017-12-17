package com.example.anjum.weathercock.event;

/**
 * Created by ANJUM on 12/18/2017.
 */

public class AlertEvent {
    boolean isCelcius;
    boolean isFarienhate;

    public boolean isCelcius() {
        return isCelcius;
    }

    public boolean isFarienhate() {
        return isFarienhate;
    }

    public void setCelcius(boolean celcius) {
        isCelcius = celcius;
    }

    public void setFarienhate(boolean farienhate) {
        isFarienhate = farienhate;
    }
}

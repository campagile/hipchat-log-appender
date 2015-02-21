package com.github.campagile.logging;

public class TimerConfiguration {
    private int period = 10; //period between logging in seconds
    private int initialDelay = 5; //initial logging delay in seconds

    static final TimerConfiguration DEFAULT = new TimerConfiguration();

    public TimerConfiguration() {}

    public int getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(int initialDelay) {
        if(initialDelay >= 0) {
            this.initialDelay = initialDelay;
        }
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        if(period >= 1) {
            this.period = period;
        }
    }
}

package org.cad.logging;

public class TimerConfiguration {
    private int period = 10;
    private int initialDelay = 5;

    public TimerConfiguration() {}

    public int getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }


    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}

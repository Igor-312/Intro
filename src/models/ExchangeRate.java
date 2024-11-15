package models;

import java.time.LocalDateTime;

public class ExchangeRate {
    private double rate;
    LocalDateTime date;


    public ExchangeRate( double rate) {
        date = LocalDateTime.now();
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

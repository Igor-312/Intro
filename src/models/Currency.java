package models;

import java.util.Objects;

public class Currency {
    private final CurrencyCode currencyCode;

    public Currency(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public String toString() {
        return "currency" + currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return currencyCode == currency.currencyCode;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(currencyCode);
    }
}

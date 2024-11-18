package models;

public class Currency {
    private final CurrencyCode currencyCode;
    private String name;

    public Currency(String currencyCode, String name) {
        this.currencyCode = CurrencyCode.valueOf(currencyCode);

        this.name = name;
    }

    public CurrencyCode getCode() {
        return currencyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

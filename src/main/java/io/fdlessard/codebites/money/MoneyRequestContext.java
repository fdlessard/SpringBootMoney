package io.fdlessard.codebites.money;

import java.util.Currency;

public class MoneyRequestContext {

    private static ThreadLocal<Currency> currencyThreadLocal = new ThreadLocal<>();

    public static void setCurrency(Currency currency) {
        currencyThreadLocal.set(currency);
    }

    public static Currency getCurrency() {
        return currencyThreadLocal.get();
    }
}

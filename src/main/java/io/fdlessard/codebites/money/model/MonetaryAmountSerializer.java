package io.fdlessard.codebites.money.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.fdlessard.codebites.money.MoneyRequestContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

public class MonetaryAmountSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(
            BigDecimal number,
            JsonGenerator jsonGenerator,
            SerializerProvider provider
    ) throws IOException {
        jsonGenerator.writeNumber(localizeMonetaryAmount(number, MoneyRequestContext.getCurrency()));
    }

    private BigDecimal localizeMonetaryAmount(BigDecimal number, Currency currency) {
        return number.setScale(currency.getDefaultFractionDigits(), BigDecimal.ROUND_HALF_UP);
    }
}

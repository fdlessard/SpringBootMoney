package io.fdlessard.codebites.money.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.fdlessard.codebites.money.MoneyRequestContext;

import java.io.IOException;
import java.util.Currency;

public class DocumentCurrencySerializer extends JsonSerializer<Currency> {

    @Override
    public void serialize(
            Currency currency,
            JsonGenerator jsonGenerator,
            SerializerProvider provider
    ) throws IOException {
        MoneyRequestContext.setCurrency(currency);
        jsonGenerator.writeObject(currency);
    }
}

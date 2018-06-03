package io.fdlessard.codebites.money.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DocumentSerializer extends JsonSerializer<Document> {

    @Override
    public void serialize(
            Document document,
            JsonGenerator jsonGenerator,
            SerializerProvider provider
    ) throws IOException {
        provider.setAttribute("LaCurrency", document.getCurrency().getCurrencyCode());
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", document.getId());
        jsonGenerator.writeStringField("currency", document.getCurrency().getCurrencyCode());
        jsonGenerator.writeObjectField("items", document.getItems());
        jsonGenerator.writeEndObject();
    }
}

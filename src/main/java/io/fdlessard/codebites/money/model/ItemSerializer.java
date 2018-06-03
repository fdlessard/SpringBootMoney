package io.fdlessard.codebites.money.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ItemSerializer extends JsonSerializer<Item> {

    @Override
    public void serialize(
            Item item,
            JsonGenerator jsonGenerator,
            SerializerProvider provider
    ) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", item.getId());
        jsonGenerator.writeStringField("code", item.getCode());
        jsonGenerator.writeNumberField("totalAmount", item.getTotalAmount());
        jsonGenerator.writeObjectField("items", item.getAmountGroups());
        jsonGenerator.writeEndObject();
    }
}

package io.fdlessard.codebites.money.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Currency;
import java.util.List;

@Data
@AllArgsConstructor
public class Document {
    private String id;
    @JsonSerialize(using = DocumentCurrencySerializer.class)
    private Currency currency;
    private List<Item> items;
}

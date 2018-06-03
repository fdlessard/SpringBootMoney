package io.fdlessard.codebites.money.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class Item {
    private String id;
    private String code;
    @JsonSerialize(using = MonetaryAmountSerializer.class)
    private BigDecimal totalAmount;
    private List<AmountGroup> amountGroups;
}

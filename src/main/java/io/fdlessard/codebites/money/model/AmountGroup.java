package io.fdlessard.codebites.money.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AmountGroup {
    private String name;
    @JsonSerialize(using = MonetaryAmountSerializer.class)
    private BigDecimal netAmount;
    @JsonSerialize(using = MonetaryAmountSerializer.class)
    private BigDecimal grossAmount;
}

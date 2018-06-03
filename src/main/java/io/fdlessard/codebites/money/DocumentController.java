package io.fdlessard.codebites.money;

import io.fdlessard.codebites.money.model.AmountGroup;
import io.fdlessard.codebites.money.model.Document;
import io.fdlessard.codebites.money.model.Item;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@RestController
public class DocumentController {

    // 0 http://localhost:8080/document?currencyCode=JPY
    // 2 http://localhost:8080/document?currencyCode=CAD
    // 3 http://localhost:8080/document?currencyCode=LYD

    @RequestMapping("/document")
    public Document get(@RequestParam(value = "currencyCode", defaultValue = "USD") String currencyCode) {
        return buildDocument(Currency.getInstance(currencyCode));
    }

    private static Document buildDocument(Currency currency) {
        return new Document("documentId", currency, buildItems());
    }

    private static List<Item> buildItems() {

        List<Item> items = new ArrayList<>();
        Item item = new Item("item1", "itemCode1", BigDecimal.ONE, buildAmountGroups());
        items.add(item);
        item = new Item("item2", "itemCode2", BigDecimal.TEN, buildAmountGroups());
        items.add(item);
        return items;
    }

    private static List<AmountGroup> buildAmountGroups() {

        List<AmountGroup> amountGroups = new ArrayList<>();
        AmountGroup amountGroup = new AmountGroup("amountGrp1", BigDecimal.ONE, BigDecimal.TEN);
        amountGroups.add(amountGroup);
        amountGroup = new AmountGroup("amountGrp2", BigDecimal.ZERO, BigDecimal.TEN);
        amountGroups.add(amountGroup);
        return amountGroups;
    }

}

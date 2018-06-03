package io.fdlessard.codebites.money;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@SpringBootApplication
public class SpringBootMoneyApplication {

    @RestController
    public class DocumentController {

        // 0 http://localhost:8080/document?currencyCode=JPY
        // 2 http://localhost:8080/document?currencyCode=CAD
        // 3 http://localhost:8080/document?currencyCode=LYD

        @RequestMapping("/document")
        public Document get(@RequestParam(value = "currencyCode", defaultValue = "USD") String currencyCode) {
            return buildDocument(Currency.getInstance(currencyCode));
        }
    }

    @Data
    @AllArgsConstructor
    static class Document {
        private String id;
        @JsonSerialize(using = DocumentCurrencySerializer.class)
        private Currency currency;
        private List<Item> items;
    }

    @Data
    @AllArgsConstructor
    static class Item {
        private String id;
        private String code;
        @JsonSerialize(using = MonetaryAmountSerializer.class)
        private BigDecimal totalAmount;
        private List<AmountGroup> amountGroups;
    }

    @Data
    @AllArgsConstructor
    static class AmountGroup {
        private String name;
        @JsonSerialize(using = MonetaryAmountSerializer.class)
        private BigDecimal netAmount;
        @JsonSerialize(using = MonetaryAmountSerializer.class)
        private BigDecimal grossAmount;
    }

    static class DocumentCurrencySerializer extends JsonSerializer<Currency> {

        @Override
        public void serialize(
                Currency currency,
                JsonGenerator jsonGenerator,
                SerializerProvider provider
        ) throws IOException {
            ThreadLocalStorage.setCurrency(currency);
            jsonGenerator.writeObject(currency);
        }
    }

    static class MonetaryAmountSerializer extends JsonSerializer<BigDecimal> {

        @Override
        public void serialize(
                BigDecimal number,
                JsonGenerator jsonGenerator,
                SerializerProvider provider
        ) throws IOException {
            jsonGenerator.writeNumber(localizeMonetaryAmount(number, ThreadLocalStorage.getCurrency()));
        }

        private BigDecimal localizeMonetaryAmount(BigDecimal number, Currency currency) {
            return number.setScale(currency.getDefaultFractionDigits(), BigDecimal.ROUND_HALF_UP);
        }
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

    public static class ThreadLocalStorage {

        private static ThreadLocal<Currency> curr = new ThreadLocal<>();

        public static void setCurrency(Currency currency) {
            curr.set(currency);
        }

        public static Currency getCurrency() {
            return curr.get();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMoneyApplication.class, args);
        //bigDecimalExperimentation();
        //documentSerializationExperimentation();
    }

    private static void bigDecimalExperimentation() {

        System.out.println("MoneyExperimentApplication");

        BigDecimal number1 = new BigDecimal("10.005");
        BigDecimal number2 = new BigDecimal("11.005");

        System.out.println("Number1 : " + number1);
        System.out.println("Number2 : " + number2);
        System.out.println("Sum : " + number1.add(number2));

        BigDecimal number1s2 = new BigDecimal("10.005");
        BigDecimal number2s2 = new BigDecimal("11.005");

        System.out.println("Number 1 Scale 2: " + number1s2.setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println("Number 2 Scale 2 : " + number2s2.setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println("Sums Scale 2 after addition : " + number1s2.add(number2s2).setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println("Sums Scale 2 before addition: " + number1s2.setScale(2, BigDecimal.ROUND_HALF_UP).add(number2s2.setScale(2, BigDecimal.ROUND_HALF_UP)));
    }

    private static void documentSerializationExperimentation() {

        Document document = buildDocument(ThreadLocalStorage.getCurrency());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(document));

        } catch (Exception e) {

        }
    }

    static class DocumentSerializer extends JsonSerializer<Document> {

        @Override
        public void serialize(
                Document document,
                JsonGenerator jsonGenerator,
                SerializerProvider provider
        ) throws IOException {
            provider.setAttribute("LaCurrency", document.currency.getCurrencyCode());
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", document.id);
            jsonGenerator.writeStringField("currency", document.currency.getCurrencyCode());
            jsonGenerator.writeObjectField("items", document.getItems());
            jsonGenerator.writeEndObject();
        }
    }

    static class ItemSerializer extends JsonSerializer<Item> {

        @Override
        public void serialize(
                Item item,
                JsonGenerator jsonGenerator,
                SerializerProvider provider
        ) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", item.id);
            jsonGenerator.writeStringField("code", item.code);
            jsonGenerator.writeNumberField("totalAmount", item.totalAmount);
            jsonGenerator.writeObjectField("items", item.getAmountGroups());
            jsonGenerator.writeEndObject();
        }
    }
}
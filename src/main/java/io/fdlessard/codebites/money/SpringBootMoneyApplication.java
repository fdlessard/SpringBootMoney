package io.fdlessard.codebites.money;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@SpringBootApplication
public class SpringBootMoneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMoneyApplication.class, args);
        bigDecimalExperimentation();
        documentSerializationExperimentation();
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


    private static void documentSerializationExperimentation()  {

        Document document  = buildDocument();


        ObjectMapper objectMapper = new ObjectMapper();

        try {
            System.out.println(objectMapper.writeValueAsString(document));

        } catch (Exception e) {

        }

    }

    private static Document buildDocument() {
        return  new Document("documentId", Currency.getInstance("USD"), buildItems());
    }

    private static List<Item> buildItems() {

        List<Item> items = new ArrayList<>();

        Item item =  new Item("item1", "itemCode1", buildAmountGroups());
        items.add(item);
        item =  new Item("item2", "itemCode2", buildAmountGroups());
        items.add(item);

        return items;

    }

    private static List<AmountGroup> buildAmountGroups(){

        List<AmountGroup> amountGroups = new ArrayList<>();

        AmountGroup amountGroup = new AmountGroup("amountGrp1", BigDecimal.ONE, BigDecimal.TEN);
        amountGroups.add(amountGroup);
        amountGroup = new AmountGroup("amountGrp2", BigDecimal.ZERO, BigDecimal.TEN);
        amountGroups.add(amountGroup);

        return amountGroups;
    }

    @Data
    @AllArgsConstructor
    @JsonSerialize(using = DocumentSerializer.class)
    static class  Document {
        private String id;
        private Currency currency;
        private List<Item> items;
    }

    @Data
    @AllArgsConstructor
    static class Item {
        private String id;
        private String code;
        private List<AmountGroup> amountGroups;
    }

    @Data
    @AllArgsConstructor
    static class AmountGroup {
        private String name;
        private BigDecimal netAmount;
        private BigDecimal grossAmount;
    }

    static class DocumentSerializer extends JsonSerializer<Document> {

        @Override
        public void serialize(
                Document document,
                JsonGenerator jsonGenerator,
                SerializerProvider provider
        ) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", document.id);
            jsonGenerator.writeStringField("currency", document.currency.getCurrencyCode());
            jsonGenerator.writeObjectField("items", document.getItems());
            jsonGenerator.writeEndObject();
        }

        private BigDecimal localize(BigDecimal number, Currency currency) {
            return number.setScale(currency.getDefaultFractionDigits(), BigDecimal.ROUND_HALF_UP);
        }

    }
}
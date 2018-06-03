package io.fdlessard.codebites.money;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class SpringBootMoneyApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootMoneyApplication.class, args);
        //bigDecimalExperimentation();
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
}
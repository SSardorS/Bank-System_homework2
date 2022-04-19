package uz.pdp.homework2.service;

import org.springframework.stereotype.Service;
import uz.pdp.homework2.entity.enums.Moneys;
import uz.pdp.homework2.payload.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BankomatService {

    List<Money> arrays = new ArrayList<>(
            Arrays.asList(
                    new Money(1000.00, Moneys.SUM),
                    new Money(5000.00, Moneys.SUM),
                    new Money(10000.00, Moneys.SUM),
                    new Money(50000.00, Moneys.SUM),
                    new Money(100000.00, Moneys.SUM),
                    new Money(1.00, Moneys.DOLLAR),
                    new Money(5.00, Moneys.DOLLAR),
                    new Money(10.00, Moneys.DOLLAR),
                    new Money(20.00, Moneys.DOLLAR),
                    new Money(50.00, Moneys.DOLLAR),
                    new Money(100.00, Moneys.DOLLAR)
            )
    );





}

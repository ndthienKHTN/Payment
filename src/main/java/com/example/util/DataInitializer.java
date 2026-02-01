package com.example.util;

import com.example.domain.*;
import com.example.repository.BillRepository;
import java.time.LocalDate;

public class DataInitializer {
    public static void initializeData(BillRepository billRepository) {
        Bill bill1 = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        Bill bill2 = new Bill(2, BillType.WATER, new Money(175000),
                LocalDate.of(2020, 10, 30), "SAVACO HCMC");
        Bill bill3 = new Bill(3, BillType.INTERNET, new Money(800000),
                LocalDate.of(2020, 11, 30), "VNPT");

        billRepository.save(bill1);
        billRepository.save(bill2);
        billRepository.save(bill3);
    }
}

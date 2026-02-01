package com.example.service;

import com.example.domain.Bill;
import com.example.repository.BillRepository;
import java.util.List;
import java.util.Optional;

public class BillService {
    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    public Optional<Bill> findBillById(int id) {
        return billRepository.findById(id);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> searchBillsByProvider(String provider) {
        return billRepository.findByProvider(provider);
    }

    public List<Bill> getUnpaidBillsSortedByDueDate() {
        return billRepository.findUnpaidBillsSortedByDueDate();
    }

    public void deleteBill(int id) {
        billRepository.delete(id);
    }

    public int getNextBillId() {
        return billRepository.getNextId();
    }
}

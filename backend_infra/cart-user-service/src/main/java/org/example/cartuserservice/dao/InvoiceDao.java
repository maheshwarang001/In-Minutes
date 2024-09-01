package org.example.cartuserservice.dao;

import org.example.cartuserservice.entity.Invoice;
import org.example.cartuserservice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDao {

    @Autowired
    private InvoiceRepository invoiceRepository;


    public void saveInvoice(Invoice invoice){
        invoiceRepository.save(invoice);
    }
}

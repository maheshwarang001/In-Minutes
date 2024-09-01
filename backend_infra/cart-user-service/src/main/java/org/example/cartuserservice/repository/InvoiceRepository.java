package org.example.cartuserservice.repository;

import org.example.cartuserservice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice , Long> {
}

package org.example.order_service.dto;

import lombok.Builder;
import lombok.Data;
import org.example.order_service.entity.Invoice;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderInit {

    private long referenceId;

    private UUID customerId;

    private UUID darkStoreId;

    private List<ItemDetailsDto> items;

    private InvoiceDto invoice;

}

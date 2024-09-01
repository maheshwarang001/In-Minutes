package org.example.cartuserservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderQueryDto {

    private long cartId;

    private UUID userId;

    private InvoiceDto invoiceDto;

    private List<ItemDetailsDto> productDetailDtoList;

    private AddressOrder addressOrder;

}

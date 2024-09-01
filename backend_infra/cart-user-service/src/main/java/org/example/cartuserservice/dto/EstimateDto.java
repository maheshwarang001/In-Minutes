package org.example.cartuserservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Data
@Builder
public class EstimateDto {

    private long cartId;

    private AddressOrder addressId;

    private List<ItemDetailsDto> productDetailDtoList;

    private InvoiceDto invoiceDto;

}

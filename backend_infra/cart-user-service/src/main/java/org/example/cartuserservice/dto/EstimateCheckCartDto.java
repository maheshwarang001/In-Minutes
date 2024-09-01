package org.example.cartuserservice.dto;

import lombok.Data;

import java.util.List;


@Data
public class EstimateCheckCartDto {

    private List<ItemDetailsDto> productDetailDtoList;

    private InvoiceProduct invoiceProduct;

}

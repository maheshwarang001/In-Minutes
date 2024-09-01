package org.example.paymentservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePaymentItem {

    String id;

    Long amount;

}

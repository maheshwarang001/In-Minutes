package org.example.cartuserservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class QueryProduct {

   private UUID storeId;

   private List<Item> items;

}



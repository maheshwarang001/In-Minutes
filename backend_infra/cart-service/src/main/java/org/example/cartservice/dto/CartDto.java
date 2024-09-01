package org.example.cartservice.dto;

import jakarta.persistence.*;
import org.example.cartservice.entity.Invoice;
import org.example.cartservice.entity.Item;

import java.util.List;
import java.util.UUID;

public class CartDto {

    private UUID cartId;

    private UUID darkStoreId;

    private Invoice invoice;

    private List<Item> itemList;
}

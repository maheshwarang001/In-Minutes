package org.example.inventory_read_service.entity.inventory;

import jakarta.persistence.*;

import java.util.UUID;

public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id", nullable = false,updatable = false)
    private UUID category_id;

    @Enumerated(value = CategoryEnum.class)
    @Column(name = "category_name", nullable = false)
    private CategoryEnum category_name;

    @Column(name = "category_image", nullable = false)
    private String category_image;

}

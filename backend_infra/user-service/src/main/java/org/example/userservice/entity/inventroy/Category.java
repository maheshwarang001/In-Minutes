package org.example.userservice.entity.inventroy;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity()
@Table(name = "category_table")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id", nullable = false)
    UUID category_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name", nullable = false)
    Categories category_name;

    @Column(name = "category_image", nullable = false)
    private String category_image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubCategory> subCategories;

}

package org.example.userservice.entity.inventroy;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "subcategory_table")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sub_category_id", nullable = false)
    UUID sub_category_id;

    @Column(name = "sub_category_name", nullable = false)
    String sub_category_name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "sub_category_image", nullable = false)
    private String subCategory_image;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}

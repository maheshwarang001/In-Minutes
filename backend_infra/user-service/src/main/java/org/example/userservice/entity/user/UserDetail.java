package org.example.userservice.entity.user;


import jakarta.persistence.*;
import lombok.Data;
import org.example.userservice.entity.order.OrderDetails;

import java.util.List;
import java.util.UUID;

@Data
@Entity()
@Table(name = "user_detail")
public class UserDetail {

    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID user_id;

    @Column(name = "user_email", nullable = false , updatable = false)
    private String user_email;

    @Column(name = "first_name", length = 40)
    private String first_name;

    @Column(name = "last_name" , length = 40)
    private String last_name;

    @Column(name = "phone_number" , length = 12)
    private String phone_number;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Address> address;

    @OneToMany(mappedBy = "userDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetails> orders;

}

package org.example.userservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.userservice.entity.order.Item;
import org.example.userservice.entity.user.Address;
import org.example.userservice.entity.user.UserDetail;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailPatch {

    private UUID orderID;

    private List<Item> items;

    private UUID storeId;

    private UserDetail userDetail;

    private Address address;

}

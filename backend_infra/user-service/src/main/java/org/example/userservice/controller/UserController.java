package org.example.userservice.controller;

import org.example.userservice.model.*;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserController {

    ResponseEntity<ApiResponse> storeUserToUserService(UserReplica userReplica);

    ResponseEntity<ApiResponse> saveUserDetails(UserDetailModel userDetail, UUID uuid);


    ResponseEntity<ApiResponse> saveAddress(AddressModel addressModel);
    ResponseEntity<ApiResponse> updateAddress(AddressPatchModel addressPatchModel);
    ResponseEntity<ApiResponse> updateDetails(UserDetailModel userDetailModel);

    ResponseEntity<ApiResponse> createOrder(OrderDetailPatch orderDetailPatch);


}

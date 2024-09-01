package org.example.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.userservice.model.*;
import org.example.userservice.service.OrderService;
import org.example.userservice.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user-service/v1")
@Slf4j
public class MainController implements UserController {

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    OrderService orderService;

    private ResponseEntity<ApiResponse> createErrorResponse(String message, HttpStatus status) {
        ApiResponse errorResponse = new ApiResponse(status.value(), message, null);
        return new ResponseEntity<>(errorResponse, status);
    }

    private ResponseEntity<ApiResponse> createSuccessResponse(Object data) {
        ApiResponse successResponse = new ApiResponse(HttpStatus.OK.value(), "Success", data);
        return ResponseEntity.ok(successResponse);
    }

    @Override
    @PostMapping("/user-init")
    public ResponseEntity<ApiResponse> storeUserToUserService(@RequestBody UserReplica userReplica) {
        try {
            userDetailService.createUserInstance(userReplica);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error("Error storing user: ", e);
            return createErrorResponse("Error storing user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping("/user-detail/profile")
    public ResponseEntity<ApiResponse> saveUserDetails(@RequestBody UserDetailModel userDetail , @RequestParam UUID uuid) {

        try {
            userDetailService.saveUserDetails(userDetail, uuid);
            return createSuccessResponse(true);
        } catch (Exception e) {
            log.error("Error saving user details: ", e);
            return createErrorResponse("Error saving user details", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping("/user-detail/address")
    public ResponseEntity<ApiResponse> saveAddress(@RequestBody AddressModel addressModel) {
        UUID uuid = UUID.randomUUID();
        try {
            userDetailService.saveAddress(addressModel, uuid);
            return createSuccessResponse(true);
        } catch (Exception e) {
            log.error("Error saving address: ", e);
            return createErrorResponse("Error saving address", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping("/user-detail/update/address")
    public ResponseEntity<ApiResponse> updateAddress(@RequestBody AddressPatchModel addressPatchModel) {
        UUID uuid = UUID.randomUUID();
        try {
            userDetailService.updateAddress(addressPatchModel, uuid);
            return createSuccessResponse(true);
        } catch (Exception e) {
            log.error("Error updating address: ", e);
            return createErrorResponse("Error updating address", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping("/user-detail/update/profile")
    public ResponseEntity<ApiResponse> updateDetails(@RequestBody UserDetailModel userDetailModel) {
        UUID uuid = UUID.randomUUID();
        try {
            userDetailService.updateUserDetails(userDetailModel, uuid);
            return createSuccessResponse(true);
        } catch (Exception e) {
            log.error("Error updating user details: ", e);
            return createErrorResponse("Error updating user details", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderDetailPatch orderDetailPatch) {
        try {
            orderService.placeOrder(orderDetailPatch);
            return createSuccessResponse(true);
        } catch (Exception e) {
            log.error("Error creating order: ", e);
            return createErrorResponse("Error creating order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
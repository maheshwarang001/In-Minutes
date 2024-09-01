package org.example.userservice.dao;

import org.example.userservice.entity.user.UserDetail;
import org.example.userservice.model.AddressModel;
import org.example.userservice.model.AddressPatchModel;
import org.example.userservice.model.UserDetailModel;
import org.example.userservice.model.UserReplica;

import java.util.UUID;

public interface UserDaoImpl {


    void save(UserReplica userReplica);

    UserDetail findByUserId(UUID uuid);

    void saveUserDetails(UserDetailModel userDetailModel, UUID id);

    void saveAddress(AddressModel addressModel, UUID id);

    void updateUserDetails(UserDetailModel userDetailModel, UUID id) throws Exception;

    void updateAddress(AddressPatchModel addressPatchModel, UUID id) throws  Exception;

}

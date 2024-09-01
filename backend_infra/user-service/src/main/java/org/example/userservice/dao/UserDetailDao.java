package org.example.userservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.userservice.entity.user.Address;
import org.example.userservice.entity.user.UserDetail;
import org.example.userservice.model.AddressModel;
import org.example.userservice.model.AddressPatchModel;
import org.example.userservice.model.UserDetailModel;
import org.example.userservice.model.UserReplica;
import org.example.userservice.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Component
public class UserDetailDao implements UserDaoImpl{

    @Autowired
    UserDetailRepository userDetailRepository;
    @Override
    public void save(UserReplica userReplica) {

        try {

            if (
                    userReplica.getUuid() == null || userReplica.getEmail() == null || userReplica.getEmail().isEmpty() || userReplica.getUuid().isEmpty())
                throw new NullPointerException();


            UserDetail userDetail = new UserDetail();
            userDetail.setUser_id(UUID.fromString(userReplica.getUuid()));
            userDetail.setUser_email(userReplica.getEmail());
            userDetail.setAddress(new ArrayList<>());


            userDetailRepository.save(userDetail);

        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }

    }



    @Override
    public UserDetail findByUserId(UUID uuid) {
        return userDetailRepository.findByUserId(uuid);
    }

    @Override
    public void saveUserDetails(UserDetailModel userDetailModel, UUID id) {
        try {

            if (userDetailModel.getFirst_name() == null || userDetailModel.getLast_name() == null || userDetailModel.getPhone_number() == null
                    || userDetailModel.getFirst_name().isEmpty() || userDetailModel.getLast_name().isEmpty() || userDetailModel.getPhone_number().isEmpty() || userDetailModel.getPhone_number().length() != 10)
                throw new InvalidParameterException();

            UserDetail user = findByUserId(id);
            if(user == null) throw new NullPointerException();

            user.setFirst_name(userDetailModel.getFirst_name());
            user.setLast_name(userDetailModel.getLast_name());
            user.setPhone_number(userDetailModel.getPhone_number());

            userDetailRepository.save(user);

        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void saveAddress(AddressModel addressModel, UUID id) {

        try {

            if (addressModel.getAddress() == null || addressModel.getTown() == null || addressModel.getPostcode() == null)
                throw new InvalidParameterException();

            UserDetail user = findByUserId(id);
            Address address = new Address();
            if(user == null) throw new NullPointerException();

            address.setAddress(addressModel.getAddress());
            address.setTown(addressModel.getTown());
            address.setPostcode(addressModel.getPostcode());
            address.setLatitude(addressModel.getLatitude());
            address.setLongitude(addressModel.getLongitude());
            user.getAddress().add(address);
            userDetailRepository.save(user);


        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }

    }

    @Override
    public void updateUserDetails(UserDetailModel userDetailModel, UUID id) throws Exception {
        UserDetail user = userDetailRepository.findByUserId(id);
        if (user == null) {
            throw new Exception("User not found");
        }

        if(userDetailModel.getFirst_name() != null){
            user.setFirst_name(userDetailModel.getFirst_name());
        }
        if(userDetailModel.getLast_name() != null){
            user.setLast_name(userDetailModel.getLast_name());
        }
        if(userDetailModel.getPhone_number() != null){
            user.setPhone_number(userDetailModel.getPhone_number());
        }
    }

    @Override
    public void updateAddress(AddressPatchModel addressPatchModel, UUID id) throws Exception {
        UserDetail user = userDetailRepository.findByUserId(id);
        if (user == null) {
            throw new Exception("User not found");
        }

        //find the address object

        Address address = user
                .getAddress()
                .stream()
                .filter(a->a.getAddress_id() == addressPatchModel.getUuid())
                .findFirst()
                .orElseThrow(()->new Exception("Address Not Found"));

        if (addressPatchModel.getAddress() != null) {
            address.setAddress(addressPatchModel.getAddress());
        }
        if (addressPatchModel.getTown() != null) {
            address.setTown(addressPatchModel.getTown());
        }
        if (addressPatchModel.getPostcode() != null) {
            address.setPostcode(addressPatchModel.getPostcode());
        }

        userDetailRepository.save(user);
    }
}

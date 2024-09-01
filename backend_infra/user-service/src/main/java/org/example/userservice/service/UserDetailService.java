package org.example.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.userservice.dao.UserDetailDao;
import org.example.userservice.model.AddressModel;
import org.example.userservice.model.AddressPatchModel;
import org.example.userservice.model.UserDetailModel;
import org.example.userservice.model.UserReplica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class UserDetailService {


    @Autowired
    UserDetailDao userDetailDao;

    public void createUserInstance(UserReplica userReplica){
        try {
            userDetailDao.save(userReplica);
        }catch (Exception e){
            throw e;
        }

    }

    @Transactional
    public void saveUserDetails(UserDetailModel userDetailModel, UUID id){
        try {
            userDetailDao.saveUserDetails(userDetailModel,id);
        }catch (Exception e){
            throw e;
        }

    }

    @Transactional
    public void saveAddress(AddressModel addressModel, UUID id){
        try {
            userDetailDao.saveAddress(addressModel,id);
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void updateUserDetails(UserDetailModel userDetailModel, UUID id) throws Exception {
        try{
            userDetailDao.updateUserDetails(userDetailModel,id);
        }catch (Exception e){

            throw e;
        }
    }

    @Transactional
    public void updateAddress(AddressPatchModel addressPatchModel, UUID id) throws Exception {
        try{
            userDetailDao.updateAddress(addressPatchModel,id);
        }catch (Exception e){
            throw e;
        }
    }
}

package org.example.customer.Service;

import org.example.customer.Model.PinSetter;

import java.util.concurrent.ExecutionException;

interface  CustomerService {

    boolean passStringToQueueForOTP(String email);

    String verifyOtpAndGenerateToken(PinSetter pinSetter) throws Exception;



}

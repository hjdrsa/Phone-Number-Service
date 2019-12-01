package com.belong.phone.number.service.phone.number;

import lombok.Data;

@Data
public class PhoneNumberActivationRequest {
    private Long phoneNumberId;
    private Long customerId;
}

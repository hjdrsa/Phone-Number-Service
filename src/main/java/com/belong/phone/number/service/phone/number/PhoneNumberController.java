package com.belong.phone.number.service.phone.number;

import com.belong.phone.number.service.customer.Customer;
import com.belong.phone.number.service.customer.CustomerRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("phone-number")
public class PhoneNumberController {

    @Autowired
    private PhoneNumberRepo phoneNumberRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @GetMapping(
            path = "all",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(
            summary = "Get all phone Numbers",
            description = "Get all the phone numbers",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved all phone numbers")
            }
    )
    public ResponseEntity<List<PhoneNumber>> findPhoneNumberAll() {

        List<PhoneNumber> PhoneNumbers = phoneNumberRepo.findAll();

        return ResponseEntity
                .ok()
                .body(PhoneNumbers);
    }

    @GetMapping(
            path = "CustomerId/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(
            summary = "Number by customer id",
            description = "Get phone number by customer id",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved phone number by customer id"),
                @ApiResponse(responseCode = "204", description = "No phone number data found for customer id")
            }
    )
    public ResponseEntity<List<PhoneNumber>> findPhoneNumberByCustomerID(@PathVariable Long customerId) {

        Optional<Customer> optionalCustomer = customerRepo.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity
                .ok()
                .body(optionalCustomer.get().getPhoneNumbers());
    }
    
    @PutMapping(
            path = "activate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Activate a phone number",
            description = "Activate a phone number by linking the number to a client",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved phone number by customer id"),
                @ApiResponse(responseCode = "404", description = "The phone number or customer id could not be found"),
                @ApiResponse(responseCode = "409", description = "The phone number has been is already active"),
                @ApiResponse(responseCode = "400", description = "Bad Request!!!")    
            }
    )
    public ResponseEntity<PhoneNumber> activePhoneNumber(
            @Parameter(
                    description = "Body of request containing the phone number id and customer id",
                    required = true
            )
            @RequestBody PhoneNumberActivationRequest activationRequest){
        
        if (activationRequest == null || activationRequest.getPhoneNumberId() == null || activationRequest.getCustomerId() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<PhoneNumber> optionalPhoneNumber = phoneNumberRepo.findById(activationRequest.getPhoneNumberId());
        
        //Check if phone number exists
        if (optionalPhoneNumber.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        PhoneNumber phoneNumber = optionalPhoneNumber.get();
        
        //Phone number already active
        if (phoneNumber.getCustomer() != null) {
            if (phoneNumber.getCustomer().getId().equals(activationRequest.getCustomerId())) {
                return ResponseEntity.ok(phoneNumber);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        Optional<Customer> optionalCustomer = customerRepo.findById(activationRequest.getCustomerId());
        
        //Check if customer exists
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Customer customer = optionalCustomer.get();
        
        phoneNumber.setCustomer(customer);
        
        phoneNumberRepo.saveAndFlush(phoneNumber);
        
        return ResponseEntity.ok(phoneNumber);
    }
}

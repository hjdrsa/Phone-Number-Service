package com.belong.phone.number.service.phone.number;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepo extends JpaRepository<PhoneNumber, Long>{
    
}

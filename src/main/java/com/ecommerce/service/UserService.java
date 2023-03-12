package com.ecommerce.service;

import com.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> ListAllPageUser(int pageNo);

    Page<User> searchPageUserByEmail(String email, int pageNo);
}

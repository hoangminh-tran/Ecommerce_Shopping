package com.ecommerce.service.impl;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Page<User> ListAllPageUser(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        Page<User> page = userRepository.ListAllPageUser(pageable);
        return page;
    }

    @Override
    public Page<User> searchPageUserByEmail(String email, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        Page<User> page = userRepository.searchPageUserByEmail(email, pageable);
        return page;
    }
}

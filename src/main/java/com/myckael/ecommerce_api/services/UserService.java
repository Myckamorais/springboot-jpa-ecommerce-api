package com.myckael.ecommerce_api.services;

import com.myckael.ecommerce_api.entities.User;
import com.myckael.ecommerce_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById (Long id){
        Optional <User> obj = repository.findById(id);
        return obj.get();
    }
}

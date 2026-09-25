package com.myckael.ecommerce_api.services;

import com.myckael.ecommerce_api.entities.Product;
import com.myckael.ecommerce_api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> findAll(){
        return repository.findAll();
    }

    public Product findById (Long id){
        Optional <Product> obj = repository.findById(id);
        return obj.get();
    }
}

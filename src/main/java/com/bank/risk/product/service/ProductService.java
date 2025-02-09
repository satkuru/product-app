package com.bank.risk.product.service;

import com.bank.risk.product.model.Product;
import com.bank.risk.product.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Cacheable(value = "products", key = "#id")
    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }


    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @CachePut(value = "products", key = "#id")
    public Product updateProduct(Long id, Product product){
        Product current = repository.findById(id).orElseThrow();
        current.setName(product.getName());
        current.setType(product.getType());
        current.setPrice(product.getPrice());
        return repository.save(current);
    }

    @CacheEvict(value = "products", key = "#product.id")
    public void deleteProduct(Product product){
        repository.delete(product);
    }
}

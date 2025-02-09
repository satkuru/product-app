package com.bank.risk.product.controller;

import com.bank.risk.product.dto.ProductDto;
import com.bank.risk.product.model.Product;
import com.bank.risk.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<Product> products = service.getAllProducts();
        return ResponseEntity.ok(products.stream()
                .map(this::fromProduct)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductId(@PathVariable Long id){
        Optional<Product> productOpt = service.getProductById(id);
        return productOpt
                .map(p->ResponseEntity.ok(this.fromProduct(p)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody ProductDto product){
        Product saved = service.createProduct(toProduct(product));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productUpdate){
        Product updated = service.updateProduct(id, toProduct(productUpdate));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(fromProduct(updated));
    }

    private ProductDto fromProduct(Product product){
        return new ProductDto(product.getId(), product.getName(), product.getType(), product.getPrice());
    }

    private Product toProduct(ProductDto productDto){
        return Product.builder()
                .name(productDto.name())
                .type(productDto.type())
                .price(productDto.price())
                .build();
    }
}

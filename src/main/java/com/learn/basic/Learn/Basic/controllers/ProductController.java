package com.learn.basic.Learn.Basic.controllers;

import com.learn.basic.Learn.Basic.models.Product;
import com.learn.basic.Learn.Basic.models.ResponseObject;
import com.learn.basic.Learn.Basic.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/product")
public class ProductController {
    // DI = Dependency Injection
    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    List<Product> getAllProducts() {
        return repository.findAll();
    }

    // let's return an object with data, message status
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){ // nullable return
        Optional<Product> foundObject = repository.findById(id);
        return foundObject.isPresent() ?
            ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "Query Product Successfully", foundObject)
            )
        :
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Can't Find Product with id = " + id, null)
            );

    };

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        List<Product> foundProduct = repository.findByProductName(newProduct.getProductName().trim());
        if (foundProduct.size() > 0 ){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed","Product Name Already Taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Product successfully", repository.save(newProduct))
        );
    }

    @PutMapping("/{id}")
    // UpSert : Update! if not found => Insert
    ResponseEntity<ResponseObject> updateProduct (@RequestBody Product newProduct, @PathVariable Long id){
        Product updatedProduct =  repository.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    return repository.save(product);
                })
        .orElseGet(() -> {
            return repository.save(newProduct);
        });

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Success", updatedProduct)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct (@PathVariable Long id){
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Deleted Success","" )
            );
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found", null)
            );
        }

    }

}

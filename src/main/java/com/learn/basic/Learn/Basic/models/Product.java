package com.learn.basic.Learn.Basic.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // Make this be an entity
public class Product {
    @Id // this gonna be id
    @GeneratedValue(strategy =  GenerationType.AUTO) // it is auto generated
    private Long id;
    private String productName;

    // Default Constructor
    public Product () {}

    public Product( String productName) {
        this.productName = productName;
    }
    // is called POJO object; Plain Object Java Object

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                '}';
    }
}

package com.learn.basic.Learn.Basic.models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity // Make this be an entity
@Table(name="tblProduct") // Make it db table
public class Product {
    @Id // this going to be id
    //    @GeneratedValue(strategy =  GenerationType.AUTO) // it is auto generated
    // You can also use "sequence"
    @SequenceGenerator(
            name="product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1 // increment by 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )

    private Long id;

    // validate = constraint
    @Column(nullable = false, unique = true, length = 300)
    private String productName;
    private int year;

    // Default Constructor
    public Product () {}

    public Product( String productName, int year) {
        this.productName = productName;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Transient // calculated field = transient, not exist in mysql //as column ?
    private  int ages;
    public int getAge (){
        return Calendar.getInstance().get(Calendar.YEAR) - year;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                '}';
    }

    // Equals


    @Override  // How to tell two Product is different/equal
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return year == product.year
                && ages == product.ages
                && Objects.equals(id, product.id)
                && Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, year, ages);
    }
}

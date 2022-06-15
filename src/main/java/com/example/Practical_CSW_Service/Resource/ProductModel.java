package com.example.Practical_CSW_Service.Resource;

import com.example.Practical_CSW_Service.entity.Product;

import java.util.List;

public interface ProductModel {
    Product save (Product product);
    Product update (Product product,int id);
    List<Product> findall ();
    Product findbyid (int id);
}

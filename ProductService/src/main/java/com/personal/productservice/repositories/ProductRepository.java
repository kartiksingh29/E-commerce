package com.personal.productservice.repositories;

import com.personal.productservice.models.Product;
import com.personal.productservice.projections.ProductWithIdNamePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 1) Declared methods
    // here you do not need to write any query just write appropriate
    // method names and Hibernate ORM will implement the queries.
    Product getProductByIdIs(Long id);
    Product getProductByName(String name);


    // 2) HQL queries
    // here we can write our queries using Hibernate query language.
    @Query("select p from Product p where p.id=:id")
    Product hqlGetProductById(@Param("id") Long id);

    @Query("select p.id as id, p.name as name, p.price as price from Product p where p.id in(:ids)")
    List<ProductWithIdNamePrice> hqlGetProductProjectionsWithId(@Param("ids") List<Long> id);

    // HQL queries and Declared methods are loosely coupled with the database.
    // If the database changes, then also queries remain same.

    //3) Native Queries
    // here we can write our native database queries which will directly run on database.
    @Query(value = "select * from Product p where p.id=:id",nativeQuery = true)
    Product nativeQueryGetProductById(@Param("id") Long id);

    @Query(value = "select p.id as id, p.name as name, p.price as price from product p where p.id in(:ids)",nativeQuery = true)
    List<ProductWithIdNamePrice> nativeQueryGetProductProjectionsWithId(@Param("ids") List<Long> id);

    // Native queries have tight coupling with the database.
    // If the database changes, then the native query will also change accordingly
}

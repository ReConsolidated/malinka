package io.github.reconsolidated.malinka.basket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  this is repository for connecting to database
 **/
@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Long> {
}

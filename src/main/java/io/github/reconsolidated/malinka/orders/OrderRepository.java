package io.github.reconsolidated.malinka.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository for orders
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

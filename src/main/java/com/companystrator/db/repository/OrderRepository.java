package com.companystrator.db.repository;

import com.companystrator.db.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.client.id IN :clientIds")
    List<Order> findByClientIds(@Param("clientIds") List<Long> clientIds);

}

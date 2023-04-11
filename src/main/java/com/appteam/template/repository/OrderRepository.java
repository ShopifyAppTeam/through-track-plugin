package com.appteam.template.repository;


import com.appteam.template.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o From Order o where o.merchant = :merchant and o.status = :status")
    List<Order> findUserOrdersByStatus(
            @Param("merchant") String merchant,
            @Param("status") String status);

//    @Query("UPDATE Order o SET o.status = :status where o.id = :id")
//    @Modifying
//    int updateOrderStatus(
//            @Param("id") Long id,
//            @Param("status") String status
//    );

}

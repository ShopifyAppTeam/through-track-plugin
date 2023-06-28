package com.appteam.template.repository;

import com.appteam.template.data.Shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopRepository extends JpaRepository<Shop, String> {
    @Query("SELECT u FROM Shop u WHERE u.subdomain = :subdomain")
    public Shop getShopBySubdomain(@Param("subdomain") String subdomain);
}

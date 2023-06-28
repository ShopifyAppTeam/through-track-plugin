package com.appteam.template.service;

import com.appteam.template.data.Shop;
import com.appteam.template.dto.ShopData;
import com.appteam.template.repository.ShopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service("shopService")
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public ShopData saveShop(ShopData shopData) {
        Shop shop = populateShopEntity(shopData);
        return populateShopData(shopRepository.save(shop));
    }

    public Boolean deleteShop(String subdomain) {
        Shop shop = shopRepository.getShopBySubdomain(subdomain);
        if (shop != null) {
            shopRepository.delete(shop);
            return true;
        }
        return false;
    }

    public List<ShopData> getAllShops() {
        List<Shop> shops = shopRepository.findAll();
        List<ShopData> data = new ArrayList<>();
        shops.forEach(shop -> data.add(populateShopData(shop)));
        return data;
    }

    public ShopData getShopBySubdomain(String subdomain) throws EntityNotFoundException {
        Shop shop = shopRepository.getShopBySubdomain(subdomain);
        if (shop != null) {
            return populateShopData(shop);
        } else {
            return null;
        }
    }

    private Shop populateShopEntity(final ShopData data) {
        Shop shop = new Shop();
        shop.setSubdomain(data.getSubdomain());
        shop.setToken(data.getToken());
        shop.setUser(data.getUser());
        shop.setOrders(data.getOrders());
        return shop;
    }

    private ShopData populateShopData(final Shop shop) {
        return new ShopData(shop);
    }
}

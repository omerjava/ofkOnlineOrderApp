package com.ofkMasay.Service;

import com.ofkMasay.Entity.MenuItem;
import com.ofkMasay.Entity.Restaurant;
import com.ofkMasay.Exception.CustomException;
import com.ofkMasay.Repository.MenuItemRepository;
import com.ofkMasay.Repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;


    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }


    public boolean createRestaurant(Restaurant restaurant) {
        try{
            restaurant.setActive(true);
            return restaurantRepository.createRestaurant(restaurant);
        }catch (Exception e){
            throw new CustomException("Restaurant could not be saved due to: "+e.getMessage());
        }
    }

    public List<Restaurant> getAllRestaurants() {
        try{
            return restaurantRepository.getRestaurants();
        }catch (Exception e){
            throw new CustomException("Restaurants could not get due to: "+e.getMessage());
        }
    }


    public Restaurant getRestaurantById(Long id) {
        try{
            return restaurantRepository.getRestaurantById(id);
        }catch (Exception e){
            throw new CustomException("Restaurant could not found due to: "+e.getMessage());
        }
    }

    public boolean updateRestaurant(Long id, Restaurant restaurant) {
        try{
            return restaurantRepository.updateRestaurant(id, restaurant);
        }catch (Exception e){
            throw new CustomException("Restaurant could not be updated due to: "+e.getMessage());
        }
    }

    public boolean deleteRestaurant(Long id) {
        try{
            return restaurantRepository.deleteRestaurant(id);
        }catch (Exception e){
            throw new CustomException("Restaurant could not be deleted due to: "+e.getMessage());
        }
    }

    public List<Restaurant> getRestaurantsByOwnerId(Long userId) {
        try{
            return restaurantRepository.getRestaurantsByOwnerId(userId);
        }catch (Exception e){
            throw new CustomException("Restaurants by owner id could not found due to: "+e.getMessage());
        }
    }
}

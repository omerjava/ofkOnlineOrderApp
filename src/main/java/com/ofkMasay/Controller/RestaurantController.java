package com.ofkMasay.Controller;

import com.ofkMasay.Entity.MenuItem;
import com.ofkMasay.Entity.Restaurant;
import com.ofkMasay.Service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @PostMapping("/restaurant")
    public ResponseEntity<Void> createRestaurant(@RequestBody Restaurant restaurant){
        if (restaurantService.createRestaurant(restaurant)) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(){
        return new ResponseEntity<>(restaurantService.getAllRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id){
        return new ResponseEntity<>(restaurantService.getRestaurantById(id), HttpStatus.OK);
    }

    @GetMapping("/restaurants/{userId}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(restaurantService.getRestaurantsByOwnerId(userId), HttpStatus.OK);
    }

    @PutMapping("/restaurant/{id}")
    public ResponseEntity<Void> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant){
        if(restaurantService.updateRestaurant(id, restaurant)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/restaurant/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id){
        if(restaurantService.deleteRestaurant(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

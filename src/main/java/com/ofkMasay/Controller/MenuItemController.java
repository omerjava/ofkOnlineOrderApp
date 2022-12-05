package com.ofkMasay.Controller;

import com.ofkMasay.Entity.MenuItem;
import com.ofkMasay.Service.MenuItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping("/menuItem")
    public ResponseEntity<Void> createMenuItem(@RequestBody MenuItem menuItem){
        if (menuItemService.createMenuItem(menuItem)) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/menuItems")
    public ResponseEntity<List<MenuItem>> getAllMenuItems(){
        return new ResponseEntity<>(menuItemService.getAllMenuItems(), HttpStatus.OK);
    }

    @GetMapping("/menuItem/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id){
        return new ResponseEntity<>(menuItemService.getMenuItemById(id), HttpStatus.OK);
    }

    @PutMapping("/menuItem/{id}")
    public ResponseEntity<Void> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem){
        if(menuItemService.updateMenuItem(id, menuItem)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/menuItem/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id){
        if(menuItemService.deleteMenuItem(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

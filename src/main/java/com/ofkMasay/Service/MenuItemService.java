package com.ofkMasay.Service;

import com.ofkMasay.Entity.MenuItem;
import com.ofkMasay.Exception.CustomException;
import com.ofkMasay.Repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public boolean createMenuItem(MenuItem menuItem) {
        try{
            return menuItemRepository.createMenuItem(menuItem);
        }catch (Exception e){
            throw new CustomException("Menu item could not be created due to: "+e.getMessage());
        }
    }

    public List<MenuItem> getAllMenuItems() {
        try{
            return menuItemRepository.getMenuItems();
        }catch (Exception e){
            throw new CustomException("Menu items could not be found due to: "+e.getMessage());
        }
    }

    public MenuItem getMenuItemById(Long id) {
        try{
            return menuItemRepository.getMenuItemById(id);
        }catch (Exception e){
            throw new CustomException("Menu item with Id "+id+"could not be found due to: "+e.getMessage());
        }
    }

    public boolean updateMenuItem(Long id, MenuItem menuItem) {
        try{
            return menuItemRepository.updateMenuItem(id, menuItem);
        }catch (Exception e){
            throw new CustomException("Menu item could not be updated due to: "+e.getMessage());
        }
    }

    public boolean deleteMenuItem(Long id) {
        try{
            return menuItemRepository.deleteMenuItem(id);
        }catch (Exception e){
            throw new CustomException("Menu item could not be deleted due to: "+e.getMessage());
        }

    }
}

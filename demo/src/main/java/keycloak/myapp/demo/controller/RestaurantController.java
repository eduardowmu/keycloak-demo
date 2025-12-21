package keycloak.myapp.demo.controller;

import keycloak.myapp.demo.entity.Menu;
import keycloak.myapp.demo.entity.MenuItem;
import keycloak.myapp.demo.entity.Restaurant;
import keycloak.myapp.demo.repository.MenuItemRepository;
import keycloak.myapp.demo.repository.MenuRepository;
import keycloak.myapp.demo.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping("/public/list")
    public List<Restaurant> getRestaurants() {
        return this.restaurantRepository.findAll();
    }
    @GetMapping("/public/menu/{restaurantId}")
    public Menu getMenu(@PathVariable Long restaurantId) {
        Menu menu = this.menuRepository.findByRestaurantId(restaurantId);
        menu.setMenuItems(this.menuItemRepository.findAllByMenuId(menu.getId()));
        return menu;
    }

    @PostMapping
    public Restaurant createRestaurant(Restaurant restaurant) {
        return this.restaurantRepository.save(restaurant);
    }

    @PostMapping("/menu")
    public Menu createMenu(Menu menu) {
        this.menuRepository.save(menu);
        menu.getMenuItems().forEach(menuItem -> {
            menuItem.setMenuId(menu.getId());
            this.menuItemRepository.save(menuItem);
        });
        return menu;
    }

    @PutMapping("/menu/item/{itemId}/{price}")
    public MenuItem updateMenuItemPrice(@PathVariable Long itemId,
                                        @PathVariable BigDecimal price) {
        Optional<MenuItem> menuItem = this.menuItemRepository.findById(itemId);
        menuItem.get().setPrice(price);
        return menuItem.get();
    }
}
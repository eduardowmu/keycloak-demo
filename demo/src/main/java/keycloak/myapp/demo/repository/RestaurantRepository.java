package keycloak.myapp.demo.repository;

import keycloak.myapp.demo.entity.MenuItem;
import keycloak.myapp.demo.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
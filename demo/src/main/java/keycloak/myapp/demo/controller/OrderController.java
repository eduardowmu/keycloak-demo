package keycloak.myapp.demo.controller;

import keycloak.myapp.demo.entity.Order;
import keycloak.myapp.demo.entity.OrderItem;
import keycloak.myapp.demo.repository.OrderItemRepository;
import keycloak.myapp.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @GetMapping("/{restaurtantId}/list")
    public ResponseEntity<List<Order>> getOrders(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(this.orderRepository.findByRestaurantId(restaurantId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId) {
        Order order = this.orderRepository.findById(orderId).get();
        order.setOrderItems(this.orderItemRepository.findByOrderId(orderId));
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        this.orderRepository.save(order);
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem -> {
            orderItem.setOrderId(order.getId());
            this.orderItemRepository.save(orderItem);
        });
        return ResponseEntity.ok(order);
    }
}
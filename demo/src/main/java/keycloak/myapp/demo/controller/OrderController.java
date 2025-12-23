package keycloak.myapp.demo.controller;

import keycloak.myapp.demo.entity.Order;
import keycloak.myapp.demo.entity.OrderItem;
import keycloak.myapp.demo.repository.OrderItemRepository;
import keycloak.myapp.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @GetMapping("/{restaurantId}/list")
    public ResponseEntity<List<Order>> getOrders(@PathVariable Long restaurantId) {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(restaurantId);
        order.setRestaurantId(restaurantId);
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setId(restaurantId);
        orderItem.setOrderId(order.getId());
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);
        orders.add(order);
        return ResponseEntity.ok(
                orders
                //this.orderRepository.findByRestaurantId(restaurantId)
        );
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
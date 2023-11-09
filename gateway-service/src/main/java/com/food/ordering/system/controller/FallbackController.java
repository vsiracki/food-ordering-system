package com.food.ordering.system.controller;

import com.food.ordering.system.model.CustomerServiceFallbackModel;
import com.food.ordering.system.model.OrderServiceFallbackModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    private static final Logger LOG = LoggerFactory.getLogger(FallbackController.class);

    @Value("${server.port}")
    private String port;

    @PostMapping("/customer-fallback")
    public ResponseEntity<CustomerServiceFallbackModel> customerServiceFallback() {
        LOG.info("Returning fallback result for customer-service! on port {}", port);
        return ResponseEntity.ok(CustomerServiceFallbackModel.builder()
                .fallbackMessage("Fallback result for customer-service!")
                .build());
    }

    @PostMapping("/order-fallback")
    public ResponseEntity<OrderServiceFallbackModel> orderServiceFallback() {
        LOG.info("Returning fallback result for order-service! on port {}", port);
        return ResponseEntity.ok(OrderServiceFallbackModel.builder()
                .fallbackMessage("Fallback result for order-service!")
                .build());
    }
}

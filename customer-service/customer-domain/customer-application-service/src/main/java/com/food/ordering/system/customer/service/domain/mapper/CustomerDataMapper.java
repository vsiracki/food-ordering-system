package com.food.ordering.system.customer.service.domain.mapper;

import com.food.ordering.system.customer.service.domain.create.CreateCustomerCommand;
import com.food.ordering.system.customer.service.domain.create.CreateCustomerResponse;
import com.food.ordering.system.customer.service.domain.entity.Customer;
import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.elastic.model.index.impl.CustomerIndexModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Component
public class CustomerDataMapper {

    public Customer createCustomerCommandToCustomer(CreateCustomerCommand createCustomerCommand) {
        return new Customer(new CustomerId(createCustomerCommand.getCustomerId()),
                createCustomerCommand.getUsername(),
                createCustomerCommand.getFirstName(),
                createCustomerCommand.getLastName());
    }

    public CreateCustomerResponse customerToCreateCustomerResponse(Customer customer, String message) {
        return new CreateCustomerResponse(customer.getId().getValue(), message);
    }

    public List<CustomerIndexModel> createCustomerCommandToCustomerIndexModel(CreateCustomerCommand createCustomerCommand) {
        return List.of (CustomerIndexModel.builder ( )
                    .id (UUID.randomUUID ().toString ())
                    .userId (createCustomerCommand.getCustomerId ().toString ())
                    .username (createCustomerCommand.getUsername ( ))
                    .firstName (createCustomerCommand.getFirstName ())
                    .lastName (createCustomerCommand.getLastName ( ))
                    .createdAt (Instant.now ( ).atZone (ZoneId.of ("UTC") ))
                .build ());
    }
}

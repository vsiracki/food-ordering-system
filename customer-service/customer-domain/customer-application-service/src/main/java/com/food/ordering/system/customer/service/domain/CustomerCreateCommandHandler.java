package com.food.ordering.system.customer.service.domain;

import com.food.ordering.system.customer.service.domain.create.CreateCustomerCommand;
import com.food.ordering.system.customer.service.domain.entity.Customer;
import com.food.ordering.system.customer.service.domain.event.CustomerCreatedEvent;
import com.food.ordering.system.customer.service.domain.exception.CustomerDomainException;
import com.food.ordering.system.customer.service.domain.mapper.CustomerDataMapper;
import com.food.ordering.system.customer.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.elastic.index.client.service.ElasticIndexClient;
import com.food.ordering.system.elastic.model.index.impl.CustomerIndexModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
class CustomerCreateCommandHandler {

    private final CustomerDomainService customerDomainService;

    private final CustomerRepository customerRepository;

    private final CustomerDataMapper customerDataMapper;

    private final ElasticIndexClient<CustomerIndexModel> elasticsearchClient;

    public CustomerCreateCommandHandler(CustomerDomainService customerDomainService,
                                        CustomerRepository customerRepository,
                                        CustomerDataMapper customerDataMapper,
                                        ElasticIndexClient<CustomerIndexModel> elasticsearchClient) {
        this.customerDomainService = customerDomainService;
        this.customerRepository = customerRepository;
        this.customerDataMapper = customerDataMapper;
        this.elasticsearchClient = elasticsearchClient;
    }

    @Transactional
    public CustomerCreatedEvent createCustomer(CreateCustomerCommand createCustomerCommand) {
        Customer customer = customerDataMapper.createCustomerCommandToCustomer(createCustomerCommand);
        CustomerCreatedEvent customerCreatedEvent = customerDomainService.validateAndInitiateCustomer(customer);
        Customer savedCustomer = customerRepository.createCustomer(customer);
        elasticsearchClient.save (customerDataMapper.createCustomerCommandToCustomerIndexModel(createCustomerCommand));
        if (savedCustomer == null) {
            log.error("Could not save customer with id: {}", createCustomerCommand.getCustomerId());
            throw new CustomerDomainException("Could not save customer with id " +
                    createCustomerCommand.getCustomerId());
        }
        log.info("Returning CustomerCreatedEvent for customer id: {}", createCustomerCommand.getCustomerId());
        return customerCreatedEvent;
    }
}

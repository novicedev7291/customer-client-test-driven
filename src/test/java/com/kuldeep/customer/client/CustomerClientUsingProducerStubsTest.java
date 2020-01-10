package com.kuldeep.customer.client;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@SpringBootTest(classes = CustomerClientConfiguration.class)
@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(ids = "com.kuldeep.test:customer-svc-simple-test:+:stubs:8080", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class CustomerClientUsingProducerStubsTest {
    @Autowired
    private CustomerClient client;

    @Test
    public void shouldReturnAllCustomers() {
        Collection<Customer> customers = client.getAllCustomers();
        BDDAssertions.then(customers.size()).isEqualTo(2);
    }

    @Test
    public void shouldReturnCustomerById() {
        Customer customer = client.getCustomerById(1L);
        BDDAssertions.then(customer.getId()).isEqualTo(1L);
        BDDAssertions.then(customer.getFirst()).isEqualToIgnoringCase("first");
        BDDAssertions.then(customer.getLast()).isEqualToIgnoringCase("last");
        BDDAssertions.then(customer.getEmail()).isEqualTo("first@email.com");
    }
}



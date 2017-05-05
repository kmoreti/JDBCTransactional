package br.com.moreti.service;

import br.com.moreti.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kmoreti on 5/4/17.
 */
@Component
public class CustomerService {
    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertCustomerBatch(List<Object[]> splitUpNames) {
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
        "".substring(0,10);
    }

    public void insertCustomer(Customer customer) {
        jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES (?,?)", new Object[]{customer.getFirstName(), customer.getLastName()});
    }

    public List<Customer> getCustomers() {
        return jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers",
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
        );
    }
}

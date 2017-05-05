package br.com.moreti;

import br.com.moreti.domain.Customer;
import br.com.moreti.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kmoreti on 5/4/17.
 */
@SpringBootApplication
@EnableTransactionManagement
public class JdbcApp implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(JdbcApp.class);

    public static void main(String [] args) {
        SpringApplication.run(JdbcApp.class, args);
    }


    @Autowired
    CustomerService service;

    @Override
    public void run(String... strings) throws Exception {

        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));
        try {
            processCustomers(splitUpNames);
        } catch (Exception e) {}
        log.info("Querying for customer records where first_name = 'Josh':");

        service.getCustomers().forEach(customer -> log.info(customer.toString()));
    }

    public void processCustomers(List<Object[]> names) {
        service.insertCustomerBatch(names);
    }
}

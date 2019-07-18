package com.bwg.orderservice.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * https://github.com/spring-projects/spring-data-rest/blob/master/src/main/asciidoc/configuring-cors.adoc
 */
@Repository
@CrossOrigin("*")
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, String> {

}

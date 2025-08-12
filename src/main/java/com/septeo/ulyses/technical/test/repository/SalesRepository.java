package com.septeo.ulyses.technical.test.repository;

import com.septeo.ulyses.technical.test.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Sales entity.
 */
@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

}

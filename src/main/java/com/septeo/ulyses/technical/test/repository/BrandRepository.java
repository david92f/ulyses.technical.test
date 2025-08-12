package com.septeo.ulyses.technical.test.repository;

import com.septeo.ulyses.technical.test.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Brand entity.
 * This interface defines methods for brand-related database operations.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {}

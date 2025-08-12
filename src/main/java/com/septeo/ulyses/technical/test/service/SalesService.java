package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Sales operations.
 */
public interface SalesService {

    /**
     * Get all sales with pagination.
     *
     * @param pageable pageable object with pagination information
     * @return a page of sales
     */
    Page<Sales> getAllSales(Pageable pageable);

    /**
     * Get a sales by its ID.
     *
     * @param id the ID of the sales to find
     * @return an Optional containing the sales if found, or empty if not found
     */
    Optional<Sales> getSalesById(Long id);

    /**
     * Get all sales for a specific brand.
     *
     * @param brandId the ID of the brand
     * @return a list of sales for the specified brand
     */
    List<Sales> getSalesByBrand(Long brandId);

    /**
     * Get all sales for a specific vehicle.
     *
     * @param vehicleId the ID of the vehicle
     * @return a list of sales for the specified vehicle
     */
    List<Sales> getSalesByVehicle(Long vehicleId);

    /**
     * Get the best-selling vehicles, optionally within a date range.
     *
     * @param startDate the start date to filter sales (optional)
     * @param endDate the end date to filter sales (optional)
     * @return a list of the 5 best-selling vehicles
     */
    List<Vehicle> getBestSellingVehicles(LocalDate startDate, LocalDate endDate);

}

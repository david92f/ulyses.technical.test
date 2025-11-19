package com.septeo.ulyses.technical.test.controller;

import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;
import com.septeo.ulyses.technical.test.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    /**
     * Get all sales with pagination.
     *
     * @param page page number to retrieve, default is 0
     * @return a list of sales for the specified page
     */
    @GetMapping
    public ResponseEntity<Page<Sales>> getAllSales(@RequestParam(defaultValue = "0") int page) {
        // Asumiendo un tamaño de página de 10
        Pageable pageable = Pageable.ofSize(10).withPage(page);
        return ResponseEntity.ok(salesService.getAllSales(pageable));
    }

    /**
     * Get a sale by its ID.
     *
     * @param id the ID of the sale to find
     * @return an Optional containing the sale if found, or empty if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sales> getSalesById(@PathVariable Long id) {
        return salesService.getSalesById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all sales for a specific brand.
     *
     * @param brandId the ID of the brand
     * @return a list of sales for the specified brand
     */
    @GetMapping("/brands/{brandId}")
    public ResponseEntity<List<Sales>> getSalesByBrand(@PathVariable Long brandId) {
        List<Sales> sales = salesService.getSalesByBrand(brandId);
        return ResponseEntity.ok(sales);
    }

    /**
     * Get all sales for a specific vehicle.
     *
     * @param vehicleId the ID of the vehicle
     * @return a list of sales for the specified vehicle
     */
    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<List<Sales>> getSalesByVehicle(@PathVariable Long vehicleId) {
        List<Sales> sales = salesService.getSalesByVehicle(vehicleId);
        return ResponseEntity.ok(sales);
    }

    /**
     * Get the best-selling vehicles, optionally within a date range.
     *
     * @param startDate the start date to filter sales (optional)
     * @param endDate the end date to filter sales (optional)
     * @return a list of the 5 best-selling vehicles
     */
    @GetMapping("/vehicles/bestSelling")
    public ResponseEntity<List<Vehicle>> getBestSellingVehicles(
            @RequestParam(required = false) Optional<LocalDate> startDate,
            @RequestParam(required = false) Optional<LocalDate> endDate) {
        List<Vehicle> bestSellingVehicles = salesService.getBestSellingVehicles(
                startDate.orElse(null),
                endDate.orElse(null)
        );
        return ResponseEntity.ok(bestSellingVehicles);
    }
}

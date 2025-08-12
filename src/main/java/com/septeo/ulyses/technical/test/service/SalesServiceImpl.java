package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;
import com.septeo.ulyses.technical.test.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the SalesService interface.
 * This class provides the implementation for all sales-related operations.
 */
@Service
@Transactional(readOnly = false)
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Sales> getAllSales(Pageable pageable) {
        return salesRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Sales> getSalesById(Long id) {
        return salesRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sales> getSalesByBrand(Long brandId) {
        return salesRepository.findAll().stream()
                .filter(sale -> sale.getVehicle().getBrand().getId().equals(brandId))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sales> getSalesByVehicle(Long vehicleId) {
        return salesRepository.findAll().stream()
                .filter(sale -> sale.getVehicle().getId().equals(vehicleId))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Vehicle> getBestSellingVehicles(LocalDate startDate, LocalDate endDate) {
        // Obtener todas las ventas y filtrarlas por fecha si se proporcionan
        List<Sales> allSales = salesRepository.findAll();
        if (startDate != null && endDate != null) {
            allSales = allSales.stream()
                    .filter(sale -> !sale.getSaleDate().isBefore(startDate) && !sale.getSaleDate().isAfter(endDate))
                    .toList();
        }

        // Contar las ventas por vehículo
        Map<Vehicle, Long> salesCountByVehicle = allSales.stream()
                .collect(Collectors.groupingBy(Sales::getVehicle, Collectors.counting()));

        // Encontrar los 5 vehículos más vendidos sin usar Collections.sort()
        return salesCountByVehicle.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }
}

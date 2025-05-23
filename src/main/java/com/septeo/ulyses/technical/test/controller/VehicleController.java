package com.septeo.ulyses.technical.test.controller;

import com.septeo.ulyses.technical.test.entity.Vehicle;
import com.septeo.ulyses.technical.test.service.BrandService;
import com.septeo.ulyses.technical.test.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        return brandService.getBrandById(vehicle.getBrand().getId())
                .map(brand -> {
                    vehicle.setBrand(brand);
                    Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);
                    return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        if (!vehicleService.getVehicleById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return brandService.getBrandById(vehicle.getBrand().getId())
                .map(brand -> {
                    vehicle.setId(id);
                    vehicle.setBrand(brand);
                    return ResponseEntity.ok(vehicleService.saveVehicle(vehicle));
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (!vehicleService.getVehicleById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}

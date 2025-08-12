package com.septeo.ulyses.technical.test.caching;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class BrandCacheService {
    // Servicio de caché para los datos de la marca.
    private final ConcurrentHashMap<Long, CacheEntry<Optional<Brand>>> brandCache = new ConcurrentHashMap<>();
    private final BrandService brandService;
    private static final long CACHE_TTL_MS = TimeUnit.MINUTES.toMillis(5); // 5 minutos de TTL

    public BrandCacheService(BrandService brandService) {
        this.brandService = brandService;
    }

    public Optional<Brand> getBrandById(Long id) {
        CacheEntry<Optional<Brand>> entry = brandCache.get(id);

        // Comprueba si la entrada de caché existe y no ha caducado
        if (entry != null && (System.currentTimeMillis() - entry.getTimestamp() < CACHE_TTL_MS)) {
            return entry.getData();
        }

        // Si no existe o ha caducado, busca en el servicio
        Optional<Brand> brand = brandService.getBrandById(id);

        // Actualiza la caché con el nuevo valor
        if (brand.isPresent()) {
            brandCache.put(id, new CacheEntry<>(brand));
        } else {
            brandCache.remove(id);
        }

        return brand;
    }

    public void evictCache() {
        brandCache.clear();
    }
}

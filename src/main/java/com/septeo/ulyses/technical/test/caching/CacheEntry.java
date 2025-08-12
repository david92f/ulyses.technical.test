package com.septeo.ulyses.technical.test.caching;

public class CacheEntry<T> {
    // Clase auxiliar para el sistema de caché.
    private T data;
    private long timestamp;

    public CacheEntry(T data) {
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public T getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

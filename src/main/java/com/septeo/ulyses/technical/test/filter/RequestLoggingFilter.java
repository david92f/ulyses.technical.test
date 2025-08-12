package com.septeo.ulyses.technical.test.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RequestLoggingFilter implements Filter {
    // Filtro personalizado para registrar solicitudes y respuestas.
    private final AtomicLong requestIdCounter = new AtomicLong();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String LOG_FILE = "requests.log";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long requestId = requestIdCounter.incrementAndGet();
        long startTime = System.currentTimeMillis();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            // Log de la solicitud
            logToFile(String.format("Request %d - Received at: %s, Method: %s, URL: %s",
                    requestId, LocalDateTime.now().format(formatter), httpRequest.getMethod(), httpRequest.getRequestURL().toString()));

            // Continúa con la cadena de filtros
            chain.doFilter(request, response);
        } finally {
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;

            // Log de la respuesta
            logToFile(String.format("Request %d - Response sent, Status: %d, Processing Time: %d ms",
                    requestId, httpResponse.getStatus(), processingTime));
        }
    }

    private void logToFile(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}

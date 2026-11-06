package com.demo;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {
    static final String MESSAGE = "Hello, from gcp 2";

    public static String buildPage(String message) {
        return "<!DOCTYPE html><html><head><title>CI/CD Demo</title></head>"
             + "<body style='font-family:sans-serif;text-align:center;margin-top:15%'>"
             + "<h1>" + message + "</h1>"
             + "<p>Java app on Kubernetes | Maven + Docker + GitHub Actions + Gitleaks</p>"
             + "</body></html>";
    }

    public static String health() { return "{\"status\":\"UP\"}"; }

    private static void send(com.sun.net.httpserver.HttpExchange ex, String type, String body) throws IOException {
        byte[] b = body.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().add("Content-Type", type);
        ex.sendResponseHeaders(200, b.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(b); }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", ex -> send(ex, "text/html; charset=utf-8", buildPage(MESSAGE)));
        server.createContext("/health", ex -> send(ex, "application/json", health()));
        server.start();
        System.out.println("Server started on port 8080");
    }
}

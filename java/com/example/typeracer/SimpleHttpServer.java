package com.example.typeracer;

import java.io.*;
import java.net.*;
import java.nio.file.*;

public class SimpleHttpServer {
    private static final int PORT = 8081;

    public static void main(String[] args) {
        System.out.println("Starting HTTP server...");
        new SimpleHttpServer().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new HttpClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class HttpClientHandler extends Thread {
        private Socket clientSocket;

        public HttpClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                 OutputStream outputStream = clientSocket.getOutputStream()) {

                String requestLine = in.readLine();
                if (requestLine != null && requestLine.startsWith("GET")) {
                    String filePath = requestLine.split(" ")[1];
                    if (filePath.equals("/")) {
                        filePath = "/demo.html";
                    }

                    File file = new File("M:\\ZOHOTASKS\\TypeRacer\\src\\main\\webapp" + filePath);
                    if (file.exists() && !file.isDirectory()) {
                        String contentType = getContentType(filePath);
                        byte[] fileContent = Files.readAllBytes(file.toPath());

                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-Type: " + contentType);
                        out.println("Content-Length: " + fileContent.length);
                        out.println(fileContent);
                        out.flush();

                        outputStream.write(fileContent);
                        outputStream.flush();
                    } else {
                        out.println("HTTP/1.1 404 Not Found");
                        out.println();
                        out.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String getContentType(String filePath) {
            if (filePath.endsWith(".html")) {
                return "text/html";
            } else if (filePath.endsWith(".css")) {
                return "text/css";
            } else if (filePath.endsWith(".js")) {
                return "application/javascript";
            } else {
                return "application/octet-stream";
            }
        }
    }
}


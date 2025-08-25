package com.vrsoft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class PedidoClient {
    private static final String BASE_URL = "http://localhost:8080/api/pedidos";
    private final ObjectMapper mapper;

    public PedidoClient() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public void enviarPedido(Pedido pedido) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");

        String json = mapper.writeValueAsString(pedido);

        try (OutputStream os = con.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = con.getResponseCode();
        if (responseCode != 202) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> erros = mapper.readValue(response.toString(), Map.class);

                String mensagem = erros.values().stream().findFirst().orElse("Erro desconhecido");

                throw new RuntimeException(mensagem);
            }
        }
    }

    public String consultarStatus(String id) throws IOException {
        URL url = new URL(BASE_URL + "/status/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            return in.readLine();
        }
    }
}

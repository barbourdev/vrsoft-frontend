package com.vrsoft;

import java.time.LocalDateTime;
import java.util.UUID;

public class Pedido {
    private UUID id;
    private String produto;
    private int quantidade;
    private LocalDateTime dataCriacao;
    private String status;

    public Pedido(String produto, int quantidade) {
        this.id = UUID.randomUUID();
        this.produto = produto;
        this.quantidade = quantidade;
        this.dataCriacao = LocalDateTime.now();
        this.status = "ENVIADO, AGUARDANDO PROCESSO";
    }

    // getters e setters
    public UUID getId() { return id; }
    public String getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

# Frontend - VR Software
sistema de Pedidos - Frontend (Java Swing)

aplicacao **Java Swing** que consome o backend do sistema de pedidos e exibe a interface grafica para o usuario

---

## Funcionalidades
- inserir **produto** e **quantidade** via campos de texto  
- enviar pedido para o backend (`POST /api/pedidos`)  
- exibir tabela com **ID, Produto, Quantidade e Status** de cada pedido  
- atualizar automaticamente o status (polling a cada 3s no endpoint `GET /api/pedidos/status/{id}`)  
- mensagens de erro tratadas para o usuário    

---

## Estrutura do projeto
```
src/main/java/com/vrsoft/pedidos/swing
 ├── Pedido.java          # Modelo do pedido no frontend
 ├── PedidoClient.java    # Cliente HTTP que chama o backend
 ├── PedidoApp.java       # Interface Swing principal
```

---

## Autor
**Felipe Barbour**  
[LinkedIn](https://linkedin.com/in/felipebarbour) | [GitHub](https://github.com/barbourdev)

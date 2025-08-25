package com.vrsoft;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoApp extends JFrame {
    private JTextField produtoField;
    private JTextField quantidadeField;
    private DefaultTableModel tableModel;
    private List<Pedido> pedidos = new ArrayList<>();
    private PedidoClient client = new PedidoClient();

    public PedidoApp() {
        setTitle("Sistema de Pedidos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        produtoField = new JTextField(15);
        quantidadeField = new JTextField(5);
        JButton enviarBtn = new JButton("Enviar Pedido");

        inputPanel.add(new JLabel("Produto:"));
        inputPanel.add(produtoField);
        inputPanel.add(new JLabel("Quantidade:"));
        inputPanel.add(quantidadeField);
        inputPanel.add(enviarBtn);

        add(inputPanel, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Produto", "Quantidade", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // acao do botao
        enviarBtn.addActionListener(e -> enviarPedido());

        // timer de atualizacao
        Timer timer = new Timer(3000, e -> atualizarStatusPedidos());
        timer.start();
    }

    // metodo de enviar o pedido ao clicar no botao
    private void enviarPedido() {
        String produto = produtoField.getText();
        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser número");
            return;
        }

        Pedido pedido = new Pedido(produto, quantidade);
        try {
            client.enviarPedido(pedido);
            pedidos.add(pedido);
            tableModel.addRow(new Object[]{pedido.getId(), pedido.getProduto(), pedido.getQuantidade(), pedido.getStatus()});
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao enviar pedido: " + ex.getMessage());
        }
    }

    // metodo do timer, para buscar atualizacao de acordo com o id do produto lancado, considerando apenas os que estao em "aguardando"
    private void atualizarStatusPedidos() {
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            if (p.getStatus().contains("AGUARDANDO")) {
                try {
                    String status = client.consultarStatus(p.getId().toString());
                    if (!status.equals("ENFILEIRADO") && !status.equals("PROCESSANDO")) {
                        p.setStatus(status);
                        final int row = i;
                        SwingUtilities.invokeLater(() -> tableModel.setValueAt(status, row, 3));
                    }
                } catch (Exception ignored) {}
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PedidoApp app = new PedidoApp();
            app.setVisible(true);
        });
    }
}

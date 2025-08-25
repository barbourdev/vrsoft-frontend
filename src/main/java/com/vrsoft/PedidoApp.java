package com.vrsoft;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PedidoApp extends JFrame {
    private JTextField produtoField;
    private JTextField quantidadeField;
    private DefaultTableModel tableModel;

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PedidoApp app = new PedidoApp();
            app.setVisible(true);
        });
    }
}

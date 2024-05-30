/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Bruno Cezar
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaContrato extends JFrame {

    private final JTextField nomeContratoField;
    private final JTextArea descricaoContratoArea;
    private final JTextField dataInicioField;
    private final JTextField dataTerminoField;
    private final JButton buscaButton;
    private final JButton limparButton;
    private final JTable tabelaResultado;

    public ConsultaContrato() {
        super("Consulta de Contrato");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);

        JLabel logotipo = new JLabel("Consulta de Contrato");
        logotipo.setFont(new Font("Arial", Font.BOLD, 24));
        logotipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(logotipo);

        nomeContratoField = new JTextField(10);
        descricaoContratoArea = new JTextArea(4, 20);
        dataInicioField = new JTextField(10);
        dataTerminoField = new JTextField(10);

        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 6));
        infoPanel.add(new JLabel("Nome do Contrato:"));
        infoPanel.add(nomeContratoField);
        infoPanel.add(new JLabel("Descrição do Contrato:"));
        infoPanel.add(new JScrollPane(descricaoContratoArea));
        infoPanel.add(new JLabel("Data de Início:"));
        infoPanel.add(dataInicioField);
        infoPanel.add(new JLabel("Data de Término:"));
        infoPanel.add(dataTerminoField);

        panel.add(infoPanel);

        buscaButton = new JButton("Busca");
        limparButton = new JButton("Limpar");

        buscaButton.addActionListener((ActionEvent e) -> {
            consultaContrato();
        });

        limparButton.addActionListener((ActionEvent e) -> {
            limparCampos();
        });

        tabelaResultado = new JTable();
        tabelaResultado.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nome", "Descrição", "Data de Início", "Data de Término"}));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buscaButton);
        buttonPanel.add(limparButton);

        add(infoPanel);
        add(buttonPanel);
        add(new JScrollPane(tabelaResultado));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    private void consultaContrato() {
        DefaultTableModel model = (DefaultTableModel) tabelaResultado.getModel();
        model.setRowCount(0);

        String nomeContrato = nomeContratoField.getText();
        String descricaoContrato = descricaoContratoArea.getText();
        String dataInicio = dataInicioField.getText();
        String dataTermino = dataTerminoField.getText();

        try {
            String url = "jdbc:mysql://localhost:3306/Projeto_Gestao_Contratos_Etapa_5";
            String usuario = "root";
            String senha = "=senaCEad2022";

            StringBuilder consultaSQL = new StringBuilder("SELECT * FROM Contratos WHERE 1=1");

            if (!nomeContrato.isEmpty()) {
                consultaSQL.append(" AND nome_contrato LIKE ?");
            }

            if (!descricaoContrato.isEmpty()) {
                consultaSQL.append(" AND descricao_contrato LIKE ?");
            }

            if (!dataInicio.isEmpty()) {
                consultaSQL.append(" AND data_inicio = ?");
            }

            if (!dataTermino.isEmpty()) {
                consultaSQL.append(" AND data_termino = ?");
            }

            try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(consultaSQL.toString())) {
                    int parametroIndex = 1;

                    if (!nomeContrato.isEmpty()) {
                        preparedStatement.setString(parametroIndex++, "%" + nomeContrato + "%");
                    }

                    if (!descricaoContrato.isEmpty()) {
                        preparedStatement.setString(parametroIndex++, "%" + descricaoContrato + "%");
                    }

                    if (!dataInicio.isEmpty()) {
                        preparedStatement.setString(parametroIndex++, dataInicio);
                    }

                    if (!dataTermino.isEmpty()) {
                        preparedStatement.setString(parametroIndex, dataTermino);
                    }

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            int idResultado = resultSet.getInt("id_contrato");
                            String nomeResultado = resultSet.getString("nome_contrato");
                            String descricaoResultado = resultSet.getString("descricao_contrato");
                            String inicioResultado = resultSet.getString("data_inicio");
                            String fimResultado = resultSet.getString("data_termino");

                            model.addRow(new Object[]{idResultado, nomeResultado, descricaoResultado, inicioResultado, fimResultado});
                        }

                        JOptionPane.showMessageDialog(this, "Consulta realizada com sucesso!");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar contrato: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        nomeContratoField.setText("");
        descricaoContratoArea.setText("");
        dataInicioField.setText("");
        dataTerminoField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConsultaContrato().setVisible(true);
        });
    }
}


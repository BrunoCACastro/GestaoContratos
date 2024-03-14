/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
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

public class Contrato extends JFrame {

    private final JTextField nomeContratoField;
    private final JTextArea descricaoContratoArea;
    private final JTextField dataInicioField;
    private final JTextField dataTerminoField;
    private final JButton cadastrarButton;
    private final JButton limparButton;
    private final JTable tabelaContratos;

    private final List<Contrato> contratos;

    public Contrato() {
        super("Cadastro de Contrato");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);

        JLabel logotipo = new JLabel("Contratos");
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

        cadastrarButton = new JButton("Cadastrar");
        limparButton = new JButton("Limpar");

        cadastrarButton.addActionListener((ActionEvent e) -> {
            cadastrarContrato();
        });

        limparButton.addActionListener((ActionEvent e) -> {
            limparCampos();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(limparButton);

        panel.add(buttonPanel);

        tabelaContratos = new JTable();
        tabelaContratos.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nome", "Descrição", "Data de Início", "Data de Término"}));
        add(new JScrollPane(tabelaContratos));

        contratos = new ArrayList<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    private void cadastrarContrato() {
        String nomeContrato = nomeContratoField.getText();
        String descricaoContrato = descricaoContratoArea.getText();
        String dataInicio = dataInicioField.getText();
        String dataTermino = dataTerminoField.getText();

        try {
            String url = "jdbc:mysql://localhost:3306/Projeto_Gestao_Contratos_Etapa_5";
            String usuario = "root";
            String senha = "=senaCEad2022";

            try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
                String sql = "INSERT INTO Contratos (nome_contrato, descricao_contrato, data_inicio, data_termino) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, nomeContrato);
                    preparedStatement.setString(2, descricaoContrato);
                    preparedStatement.setDate(3, java.sql.Date.valueOf(dataInicio));
                    preparedStatement.setDate(4, java.sql.Date.valueOf(dataTermino));

                    preparedStatement.executeUpdate();

                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        DefaultTableModel model = (DefaultTableModel) tabelaContratos.getModel();
                        model.addRow(new Object[]{id, nomeContrato, descricaoContrato, dataInicio, dataTermino});

                        JOptionPane.showMessageDialog(this, "Cadastro de contrato realizado com sucesso!");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar contrato: " + ex.getMessage());
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
            new Contrato().setVisible(true);
        });
    }
}















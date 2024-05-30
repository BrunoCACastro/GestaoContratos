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

public class Fatura extends JFrame {

    private final JTextField numeroFaturaField;
    private final JTextField dataEmissaoField;
    private final JTextField dataVencimentoField;
    private final JTextField valorField;
    private final JTextField contratoIdField;
    private final JButton cadastrarButton;
    private final JButton limparButton;
    private final JTable tabelaFaturas;

    private List<Fatura> faturas;

    public Fatura() {
        super("Cadastro de Fatura");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        JLabel logotipo = new JLabel("Faturas");
        logotipo.setFont(new Font("Arial", Font.BOLD, 24));
        logotipo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(logotipo, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 6));
        infoPanel.add(new JLabel("Número da Fatura:"));
        numeroFaturaField = new JTextField(10);
        infoPanel.add(numeroFaturaField);
        infoPanel.add(new JLabel("Data de Emissão:"));
        dataEmissaoField = new JTextField(10);
        infoPanel.add(dataEmissaoField);
        infoPanel.add(new JLabel("Data de Vencimento:"));
        dataVencimentoField = new JTextField(10);
        infoPanel.add(dataVencimentoField);
        infoPanel.add(new JLabel("Valor:"));
        valorField = new JTextField(10);
        infoPanel.add(valorField);
        infoPanel.add(new JLabel("ID do Contrato:"));
        contratoIdField = new JTextField(10);
        infoPanel.add(contratoIdField);

        panel.add(infoPanel, BorderLayout.CENTER);

        cadastrarButton = new JButton("Cadastrar");
        limparButton = new JButton("Limpar");

        cadastrarButton.addActionListener((ActionEvent e) -> cadastrarFatura());

        limparButton.addActionListener((ActionEvent e) -> limparCampos());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(limparButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        faturas = new ArrayList<>();

        tabelaFaturas = new JTable();
        tabelaFaturas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Número da Fatura", "Data de Emissão", "Data de Vencimento", "Valor", "ID do Contrato"}
        ));
        add(new JScrollPane(tabelaFaturas), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    private void cadastrarFatura() {
        String numeroFatura = numeroFaturaField.getText();
        String dataEmissao = dataEmissaoField.getText();
        String dataVencimento = dataVencimentoField.getText();
        String valor = valorField.getText();
        String contratoId = contratoIdField.getText();

        try {
            String url = "jdbc:mysql://localhost:3306/Projeto_Gestao_Contratos_Etapa_5";
            String usuario = "root";
            String senha = "=senaCEad2022";

            try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
                String sql = "INSERT INTO faturas_contrato (numero_fatura, data_emissao, data_vencimento, valor, contrato_id) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, numeroFatura);
                    preparedStatement.setString(2, dataEmissao);
                    preparedStatement.setString(3, dataVencimento);
                    preparedStatement.setString(4, valor);
                    preparedStatement.setString(5, contratoId);

                    preparedStatement.executeUpdate();

                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        DefaultTableModel model = (DefaultTableModel) tabelaFaturas.getModel();
                        model.addRow(new Object[]{id, numeroFatura, dataEmissao, dataVencimento, valor, contratoId});

                        JOptionPane.showMessageDialog(this, "Cadastro de fatura realizado com sucesso!");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar fatura: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        numeroFaturaField.setText("");
        dataEmissaoField.setText("");
        dataVencimentoField.setText("");
        valorField.setText("");
        contratoIdField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Fatura().setVisible(true);
        });
    }
}

















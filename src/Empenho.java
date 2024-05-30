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

public class Empenho extends JFrame {

    private JTextField numeroEmpenhoField;
    private JTextField numeroContratoField;
    private JTextField contaContabilField;
    private JTextField valorEmpenhoField;
    private JButton cadastrarButton;
    private JButton limparButton;
    private JTable tabelaEmpenhos;

    private List<Empenho> empenhos;

    public Empenho() {
        super("Cadastro de Empenho");
        createComponents();
        arrangeComponents();
        setDefaults();
    }

    private void createComponents() {
        numeroEmpenhoField = new JTextField(10);
        numeroContratoField = new JTextField(10);
        contaContabilField = new JTextField(10);
        valorEmpenhoField = new JTextField(10);
        cadastrarButton = new JButton("Cadastrar");
        limparButton = new JButton("Limpar");
        tabelaEmpenhos = new JTable();
    }

    private void arrangeComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);

        JLabel logotipo = new JLabel("Empenhos");
        logotipo.setFont(new Font("Arial", Font.BOLD, 24));
        logotipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(logotipo);

        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 6));
        infoPanel.add(new JLabel("Número do Empenho:"));
        infoPanel.add(numeroEmpenhoField);
        infoPanel.add(new JLabel("Número do Contrato:"));
        infoPanel.add(numeroContratoField);
        infoPanel.add(new JLabel("Conta Contábil:"));
        infoPanel.add(contaContabilField);
        infoPanel.add(new JLabel("Valor do Empenho:"));
        infoPanel.add(valorEmpenhoField);

        panel.add(infoPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(limparButton);
        panel.add(buttonPanel);

        tabelaEmpenhos.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Número Empenho", "Número Contrato", "Conta Contábil", "Valor do Empenho"}));
        add(new JScrollPane(tabelaEmpenhos));
    }

    private void setDefaults() {
        empenhos = new ArrayList<>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(900, 700);
        setLocationRelativeTo(null);

        cadastrarButton.addActionListener((ActionEvent e) -> {
            cadastrarEmpenho();
        });

        limparButton.addActionListener((ActionEvent e) -> {
            limparCampos();
        });
    }

    private void cadastrarEmpenho() {
        String numeroEmpenho = numeroEmpenhoField.getText();
        String numeroContrato = numeroContratoField.getText();
        String contaContabil = contaContabilField.getText();
        String valorEmpenho = valorEmpenhoField.getText();

        try {
            String url = "jdbc:mysql://localhost:3306/Projeto_Gestao_Contratos_Etapa_5";
            String usuario = "root";
            String senha = "=senaCEad2022";

            try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
                String sql = "INSERT INTO Empenhos_Contrato (numero_empenho, numero_contrato, conta_contabil, valor_empenho) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, numeroEmpenho);
                    preparedStatement.setString(2, numeroContrato);
                    preparedStatement.setString(3, contaContabil);
                    preparedStatement.setString(4, valorEmpenho);

                    preparedStatement.executeUpdate();

                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        DefaultTableModel model = (DefaultTableModel) tabelaEmpenhos.getModel();
                        model.addRow(new Object[]{id, numeroEmpenho, numeroContrato, contaContabil, valorEmpenho});

                        JOptionPane.showMessageDialog(this, "Cadastro de empenho realizado com sucesso!");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar empenho: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        numeroEmpenhoField.setText("");
        numeroContratoField.setText("");
        contaContabilField.setText("");
        valorEmpenhoField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Empenho().setVisible(true);
        });
    }
}










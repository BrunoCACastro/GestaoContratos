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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TermoAditivo extends JFrame {

    private final JTextField idContratoField;
    private final JTextField descricaoTermoAditivoField;
    private final JTextField dataInicioField;
    private final JTextField dataFimField;
    private final JButton cadastrarButton;
    private final JButton limparButton;
    private final JTable tabelaTermosAditivos;

    private List<TermoAditivo> termosAditivos;

    public TermoAditivo() {
        super("Cadastro de Termo Aditivo");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        JLabel logotipo = new JLabel("Termos Aditivos");
        logotipo.setFont(new Font("Arial", Font.BOLD, 24));
        logotipo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(logotipo, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 6));
        infoPanel.add(new JLabel("ID Contrato:"));
        idContratoField = new JTextField(10);
        infoPanel.add(idContratoField);
        infoPanel.add(new JLabel("Descrição:"));
        descricaoTermoAditivoField = new JTextField(10);
        infoPanel.add(descricaoTermoAditivoField);
        infoPanel.add(new JLabel("Data Início:"));
        dataInicioField = new JTextField(10);
        infoPanel.add(dataInicioField);
        infoPanel.add(new JLabel("Data Fim:"));
        dataFimField = new JTextField(10);
        infoPanel.add(dataFimField);

        panel.add(infoPanel, BorderLayout.CENTER);

        cadastrarButton = new JButton("Cadastrar");
        limparButton = new JButton("Limpar");

        cadastrarButton.addActionListener((ActionEvent e) -> cadastrarTermoAditivo());

        limparButton.addActionListener((ActionEvent e) -> limparCampos());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(limparButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        tabelaTermosAditivos = new JTable();
        tabelaTermosAditivos.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Contrato", "Descrição", "Data Início", "Data Fim"}
        ));
        add(new JScrollPane(tabelaTermosAditivos), BorderLayout.SOUTH);

        termosAditivos = new ArrayList<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    private void cadastrarTermoAditivo() {
        String idContrato = idContratoField.getText();
        String descricaoTermoAditivo = descricaoTermoAditivoField.getText();
        String dataInicio = dataInicioField.getText();
        String dataFim = dataFimField.getText();

        try {
            String url = "jdbc:mysql://localhost:3306/Projeto_Gestao_Contratos_Etapa_5";
            String usuario = "root";
            String senha = "=senaCEad2022";

            try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
                String sql = "INSERT INTO termos_aditivos (id_contrato, descricao_termo_aditivo, data_inicio, data_fim) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, idContrato);
                    preparedStatement.setString(2, descricaoTermoAditivo);
                    preparedStatement.setString(3, dataInicio);
                    preparedStatement.setString(4, dataFim);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);

                            DefaultTableModel model = (DefaultTableModel) tabelaTermosAditivos.getModel();
                            model.addRow(new Object[]{idContrato, descricaoTermoAditivo, dataInicio, dataFim});

                            JOptionPane.showMessageDialog(this, "Cadastro de termo aditivo realizado com sucesso!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao cadastrar termo aditivo. Nenhuma linha afetada.");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar termo aditivo: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        idContratoField.setText("");
        descricaoTermoAditivoField.setText("");
        dataInicioField.setText("");
        dataFimField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TermoAditivo().setVisible(true);
        });
    }
}













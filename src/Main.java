/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Bruno Cezar
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> criarInterface());
    }

    private static void criarInterface() {
        JFrame frame = new JFrame("GESTÃO DE CONTRATOS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JButton contratoButton = new JButton("Contrato");
        JButton empenhoButton = new JButton("Empenho");
        JButton faturaButton = new JButton("Faturas");
        JButton aditivoButton = new JButton("Termo Aditivo");
        JButton consultaButton = new JButton("Consulta");

        contratoButton.addActionListener((var e) -> {
            Contrato contratoTela = new Contrato();
            contratoTela.setVisible(true);
        });

        empenhoButton.addActionListener((ActionEvent e) -> {
            Empenho empenhoTela = new Empenho();
            empenhoTela.setVisible(true);
        });

        faturaButton.addActionListener((ActionEvent e) -> {
            Fatura faturaTela = new Fatura();
            faturaTela.setVisible(true);
        });

        aditivoButton.addActionListener((ActionEvent e) -> {
            TermoAditivo termoAditivoTela = new TermoAditivo();
            termoAditivoTela.setVisible(true);
        });

        consultaButton.addActionListener((ActionEvent e) -> {
            ConsultaContrato consultaContratoTela = new ConsultaContrato();
            consultaContratoTela.setVisible(true);
        });

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        JLabel logotipo = new JLabel("GESTÃO DE CONTRATOS");
        logotipo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel titulo = new JLabel("(IF Sudeste MG)");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        panelPrincipal.add(logotipo);
        panelPrincipal.add(titulo);

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new GridLayout(5, 1));
        panelBotoes.add(contratoButton);
        panelBotoes.add(empenhoButton);
        panelBotoes.add(faturaButton);
        panelBotoes.add(aditivoButton);
        panelBotoes.add(consultaButton);

        frame.add(panelPrincipal);
        frame.add(panelBotoes);

        frame.setLayout(new GridLayout(2, 1));
        frame.setVisible(true);
    }
}





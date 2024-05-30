<%-- 
    Document   : TesteConexao
    Created on : 29 de mai. de 2024, 14:14:06
    Author     : Bruno Cezar
--%>

<%@ page import="java.sql.*, java.io.*, jakarta.servlet.*, jakarta.servlet.http.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Verificação da conexão com o Banco de Dados</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
    <h1>Verificação da conexão com o Banco de Dados</h1>

    <%
        String url = "jdbc:mysql://localhost:3306/Projeto_Gestao_Contratos_Etapa_5";
        String usuario = "root";
        String senha = "=senaCEad2022";
        String mensagem = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            mensagem = "Erro ao carregar o driver JDBC: " + e.getMessage();
        }

        if (mensagem.isEmpty()) {
            try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
                if (connection != null) {
                    mensagem = "Conexão com o banco de dados estabelecida com sucesso!";
                } else {
                    mensagem = "Falha ao estabelecer a conexão com o banco de dados.";
                }
            } catch (SQLException ex) {
                mensagem = "Erro ao conectar ao banco de dados: " + ex.getMessage();
            }
        }

        out.println("<p>" + mensagem + "</p>");
    %>
</body>
</html>


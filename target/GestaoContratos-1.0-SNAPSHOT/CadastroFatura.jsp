<%-- 
    Document   : CadastroFatura
    Created on : 29 de mai. de 2024, 13:57:07
    Author     : Bruno Cezar
--%>

<%@ page import="java.io.*, java.sql.*, jakarta.servlet.*, jakarta.servlet.http.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastro de Fatura</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
    <script>
        function submitForm(event) {
            event.preventDefault(); // Prevent the form from submitting in the traditional way
            var form = document.getElementById('cadastroForm');
            var formData = new URLSearchParams(new FormData(form)).toString();

            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'CadastroFatura.jsp', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function () {
                if (xhr.status === 200) {
                    document.getElementById('message').innerHTML = xhr.responseText;
                } else {
                    document.getElementById('message').innerText = 'Erro ao cadastrar fatura.';
                }
            };
            xhr.send(formData);
        }
    </script>
</head>
<body>
    <h1>Cadastro de Fatura</h1>
    <form id="cadastroForm" onsubmit="submitForm(event)" method="post">
        <label for="numeroFatura">Número da fatura:</label>
        <input type="text" id="numeroFatura" name="numeroFatura" required><br>
        
        <label for="valor">Valor:</label>
        <input type="number" step="0.01" id="valor" name="valor" required><br>
        
        <label for="contratoId">ID do contrato:</label>
        <input type="text" id="contratoId" name="contratoId" required><br>
        
        <label for="dataEmissao">Data de emissão:</label>
        <input type="date" id="dataEmissao" name="dataEmissao" required><br><br>
        
        <label for="dataVencimento">Data de vencimento:</label>
        <input type="date" id="dataVencimento" name="dataVencimento" required><br><br><br>

        <input type="submit" value="Cadastrar">
    </form>
    <p id="message"></p>

    <%
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String numeroFatura = request.getParameter("numeroFatura");
            String dataEmissao = request.getParameter("dataEmissao");
            String dataVencimento = request.getParameter("dataVencimento");
            String valor = request.getParameter("valor");
            String contratoId = request.getParameter("contratoId");

            // Validação dos parâmetros
            boolean hasError = false;
            StringBuilder errorMessage = new StringBuilder();

            if (numeroFatura == null || numeroFatura.trim().isEmpty()) {
                hasError = true;
                errorMessage.append("Número da Fatura é obrigatório.<br>");
            }

            if (dataEmissao == null || dataEmissao.trim().isEmpty()) {
                hasError = true;
                errorMessage.append("Data de Emissão é obrigatória.<br>");
            }

            if (dataVencimento == null || dataVencimento.trim().isEmpty()) {
                hasError = true;
                errorMessage.append("Data de Vencimento é obrigatória.<br>");
            }

            if (valor == null || valor.trim().isEmpty()) {
                hasError = true;
                errorMessage.append("Valor é obrigatório.<br>");
            }

            if (contratoId == null || contratoId.trim().isEmpty()) {
                hasError = true;
                errorMessage.append("ID do Contrato é obrigatório.<br>");
            }

            if (hasError) {
                out.println("<p>Erro ao cadastrar fatura:</p>");
                out.println("<p>" + errorMessage.toString() + "</p>");
            } else {
                response.setContentType("text/html;charset=UTF-8");

                Connection conn = null;
                PreparedStatement pstmt = null;

                try {
                    String url = "jdbc:mysql://localhost:3306/Projeto_Gestao_Contratos_Etapa_5";
                    String usuario = "root";
                    String senha = "=senaCEad2022";

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection(url, usuario, senha);

                    String sql = "INSERT INTO faturas_contrato (numero_fatura, data_emissao, data_vencimento, valor, contrato_id) VALUES (?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, numeroFatura);
                    pstmt.setString(2, dataEmissao);
                    pstmt.setString(3, dataVencimento);
                    pstmt.setString(4, valor);
                    pstmt.setString(5, contratoId);

                    int rows = pstmt.executeUpdate();
                    if (rows > 0) {
                        ResultSet generatedKeys = pstmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            out.println("<p>Cadastro de fatura realizado com sucesso! ID: " + id + "</p>");
                        }
                    } else {
                        out.println("<p>Erro ao cadastrar fatura. Nenhuma linha afetada.</p>");
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    out.println("<p>Erro ao cadastrar fatura: " + e.getMessage() + "</p>");
                    out.println("<pre>" + sw.toString() + "</pre>");
                } finally {
                    try {
                        if (pstmt != null) {
                            pstmt.close();
                        }
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        out.println("<pre>" + sw.toString() + "</pre>");
                    }
                }
            }
        }
    %>
</body>
</html>


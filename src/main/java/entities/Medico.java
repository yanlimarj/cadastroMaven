package entities;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Medico extends Funcionario{

    private String especialidade;


    public Medico(){
        super();

    }
    public Medico(String nome, String telefone, String matricula, String endereco, String email, String funcao, String especialidade) {
        super(nome, telefone, matricula, endereco, email, funcao);
        this.especialidade = especialidade;

    }








    private Connection connect(){
        String jdbcURL = "jdbc:mysql://localhost:3306/cadastro";
        String username = "root";
        String password = "741963";

        try {
            return DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }


    public void cadastrarMedico() {
        String sql = "INSERT INTO Medico (nome, telefone, matricula, endereco, email, funcao, especialidade) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, getNome());
            pstmt.setString(2, getTelefone());
            pstmt.setString(3, getMatricula());
            pstmt.setString(4, getEndereco());
            pstmt.setString(5, getEmail());
            pstmt.setString(6, getFuncao());
            pstmt.setString(7, especialidade);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterarMedico() {
        String sql = "UPDATE Medico SET nome = ?, telefone = ?, endereco = ?, email = ?, funcao = ?, especialidade = ? WHERE matricula = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, getNome());
            pstmt.setString(2, getTelefone());
            pstmt.setString(3, getEndereco());
            pstmt.setString(4, getEmail());
            pstmt.setString(5, getFuncao());
            pstmt.setString(6, especialidade);
            pstmt.setString(7, getMatricula());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirMedico() {
        String sql = "DELETE FROM Medico WHERE matricula = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql))  {

            pstmt.setString(1, getMatricula());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Medico> buscarMedicosPorEspecialidade(String especialidade) {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM Medico WHERE especialidade = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cadastro", "root", "741963");
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, especialidade);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Medico medico = new Medico(rs.getString("nome"), rs.getString("telefone"), rs.getString("matricula"),
                        rs.getString("endereco"), rs.getString("email"), rs.getString("funcao"), rs.getString("especialidade"));
                medicos.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }

    public static List<Medico> buscarTodosMedicos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM Medico";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cadastro", "root", "741963");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medico medico = new Medico(rs.getString("nome"), rs.getString("telefone"), rs.getString("matricula"),
                        rs.getString("endereco"), rs.getString("email"), rs.getString("funcao"), rs.getString("especialidade"));
                medicos.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }

    public String getEspecialidade() {
        return especialidade;
    }


}

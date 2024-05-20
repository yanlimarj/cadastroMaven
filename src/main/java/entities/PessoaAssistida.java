package entities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaAssistida {

    private String nomeCompleto;
    private String dataNascimento;
    private String endereco;
    private String telefone;
    private String dataInicio;
    private String dataEncerramento;
    private String status;

    public static final String STATUS_ATIVO = "Ativo";
    public static final String STATUS_ENCERRADO = "Encerrado";

    public PessoaAssistida(){
        this.status = STATUS_ATIVO;
    }

    public PessoaAssistida(String nomeCompleto, String dataNascimento, String endereco, String telefone){
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public PessoaAssistida(String nomeCompleto, String dataNascimento, String endereco, String telefone, String dataInicio, String dataEncerramento) {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.telefone = telefone;
        this.dataInicio = dataInicio;
        this.dataEncerramento = dataEncerramento;
        this.status = STATUS_ATIVO;
    }

    public PessoaAssistida(String nomeCompleto, String dataNascimento, String endereco, String telefone, String dataInicio) {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.telefone = telefone;
        this.dataInicio = dataInicio;
        this.status = STATUS_ATIVO;
        this.dataEncerramento ="";
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(String dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
        if (dataEncerramento != null) {
            this.status = STATUS_ENCERRADO;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    Connection connect(){
        String jdbcURL = "jdbc:mysql://localhost:3306/cadastro";
        String username = "root";
        String password = "741963";

        try {
            return DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public void cadastrarCliente() {
        String sql = "INSERT INTO pessoa_assistida_cesta_basica (nomeCompleto, dataNascimento, endereco, telefone, dataInicio, dataEncerramento, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nomeCompleto);
            pstmt.setString(2, dataNascimento);
            pstmt.setString(3, endereco);
            pstmt.setString(4, telefone);
            pstmt.setString(5, dataInicio);
            pstmt.setString(6, dataEncerramento.isEmpty() ? null : dataEncerramento);
            pstmt.setString(7, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterarCliente() {
    }

    public void excluirCliente() {
    }

    public static List<PessoaAssistida> buscarClientesPorNome(String nome, String tabela) {
        List<PessoaAssistida> pessoaAssistidas = new ArrayList<>();
        String sql = "SELECT * FROM " + tabela + " WHERE nomeCompleto LIKE ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cadastro", "root", "741963");
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nome + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PessoaAssistida cliente = null;
                if (tabela.equals("pessoa_assistida_cesta_basica")) {
                    cliente = new PessoaAssistidaCestaBasica();
                } else if (tabela.equals("pessoa_assistida_especialidades")) {
                    cliente = new PessoaServicosEspecialidade();
                }

                if (cliente != null) {
                    cliente.setNomeCompleto(rs.getString("nomeCompleto"));
                    cliente.setDataNascimento(rs.getString("dataNascimento"));
                    cliente.setEndereco(rs.getString("endereco"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setDataInicio(rs.getString("dataInicio"));
                    cliente.setDataEncerramento(rs.getString("dataEncerramento"));
                    cliente.setStatus(rs.getString("status"));

                    if (cliente instanceof PessoaServicosEspecialidade) {
                        ((PessoaServicosEspecialidade) cliente).setEspecialidade(rs.getString("especialidade"));
                        ((PessoaServicosEspecialidade) cliente).setNomeMedico(rs.getString("nomeMedico"));
                        ((PessoaServicosEspecialidade) cliente).setObsProfissional(rs.getString("obsProfissional"));
                    }

                    pessoaAssistidas.add(cliente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoaAssistidas;
    }

}

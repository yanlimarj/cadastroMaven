package entities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteServicosEspecialidade extends Cliente {

    private String especialidade;
    private String nomeMedico;
    private String obsProfissional;

    public ClienteServicosEspecialidade() {
        super();
    }

    public ClienteServicosEspecialidade(String nomeCompleto, String dataNascimento, String endereco, String telefone, String dataInicio, String especialidade, String nomeMedico) {
        super(nomeCompleto, dataNascimento, endereco, telefone, dataInicio);
        this.especialidade = especialidade;
        this.nomeMedico = nomeMedico;
        this.obsProfissional = "";
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getObsProfissional() {
        return obsProfissional;
    }

    public void setObsProfissional(String obsProfissional) {
        this.obsProfissional = obsProfissional;
    }

    public void cadastrarCliente() {
        String sql = "INSERT INTO Cliente (nomeCompleto, dataNascimento, endereco, telefone, dataInicio, dataEncerramento, status, especialidade, nomeMedico) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getNomeCompleto());
            pstmt.setString(2, getDataNascimento());
            pstmt.setString(3, getEndereco());
            pstmt.setString(4, getTelefone());
            pstmt.setString(5, getDataInicio());
            pstmt.setString(6, getDataEncerramento().isEmpty() ? null : super.getDataEncerramento());
            pstmt.setString(7, getStatus());
            pstmt.setString(8, especialidade);
            pstmt.setString(9, nomeMedico);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterarCliente() {
        super.alterarCliente();
    }

    public void excluirCliente() {
        super.excluirCliente();
    }

    public static List<Cliente> buscarClientesPorNome(String nome) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM pessoa_assistida_especialidade WHERE nomeCompleto LIKE ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cadastro", "root", "741963");
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nome + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ClienteServicosEspecialidade cliente = new ClienteServicosEspecialidade();
                cliente.setNomeCompleto(rs.getString("nomeCompleto"));
                cliente.setDataNascimento(rs.getString("dataNascimento"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setDataInicio(rs.getString("dataInicio"));
                cliente.setDataEncerramento(rs.getString("dataEncerramento"));
                cliente.setStatus(rs.getString("status"));
                cliente.setEspecialidade(rs.getString("especialidade"));
                cliente.setNomeMedico(rs.getString("nomeMedico"));
                cliente.setObsProfissional(rs.getString("obsProfissional"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    @Override
    public String toString() {


        return "Nome: "
                + getNomeCompleto()
                + ", Data de nascimento: "
                + getDataNascimento()
                + ", Endereço: "
                + getEndereco()
                + ", Telefone: "
                + getTelefone()
                + ", Data de inicio do tratamento: "
                + getDataInicio()
                + ", Especialidade: "
                + especialidade
                + "Medico: "
                + nomeMedico
                + ", Observação do profissional: "
                + obsProfissional
                + ", Status: "
                + getStatus();

    }


}

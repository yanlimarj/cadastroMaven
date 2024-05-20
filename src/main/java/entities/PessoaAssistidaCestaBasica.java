package entities;


import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaAssistidaCestaBasica extends PessoaAssistida {

    public PessoaAssistidaCestaBasica(){
        super();
    }

    public PessoaAssistidaCestaBasica(String nomeCompleto, String dataNascimento, String endereco, String telefone, String dataInicio, String dataEncerramento) {
        super(nomeCompleto, dataNascimento, endereco, telefone, dataInicio, dataEncerramento);
    }

    public void cadastrarCliente() {
        super.cadastrarCliente();
    }

    public void alterarCliente(int selectedRow, DefaultTableModel tableModelCesta) {
        String nomeCompleto = (String) tableModelCesta.getValueAt(selectedRow, 0);
        String dataNascimento = (String) tableModelCesta.getValueAt(selectedRow, 1);
        String endereco = (String) tableModelCesta.getValueAt(selectedRow, 2);
        String telefone = (String) tableModelCesta.getValueAt(selectedRow, 3);
        String dataInicio = (String) tableModelCesta.getValueAt(selectedRow, 4);
        String dataEncerramento = (String) tableModelCesta.getValueAt(selectedRow, 5);

        String sql = "UPDATE pessoa_assistida_cesta_basica SET nomeCompleto = ?, dataNascimento = ?, endereco = ?, telefone = ?, dataInicio = ?, dataEncerramento = ? WHERE nomeCompleto = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, nomeCompleto);
            pstmt.setString(2, dataNascimento);
            pstmt.setString(3, endereco);
            pstmt.setString(4, telefone);
            pstmt.setString(5, dataInicio);
            if (dataEncerramento == null || dataEncerramento.isEmpty()) {
                pstmt.setNull(6, Types.DATE);
            } else {
                pstmt.setString(6, dataEncerramento);
            }
            pstmt.setString(7, nomeCompleto);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o nome fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void excluirCliente(int selectedRow, DefaultTableModel tableModelCesta) {
        String nomeCompleto = (String) tableModelCesta.getValueAt(selectedRow, 0);

        String sql = "DELETE FROM pessoa_assistida_cesta_basica WHERE nomeCompleto = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, nomeCompleto);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Cliente excluído com sucesso!");
                tableModelCesta.removeRow(selectedRow);
            } else {
                System.out.println("Nenhum cliente encontrado com o nome fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    public static List<PessoaAssistidaCestaBasica> buscarTodos() {
        List<PessoaAssistidaCestaBasica> clientes = new ArrayList<>();
        String sql = "SELECT * FROM pessoa_assistida_cesta_basica";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cadastro", "root", "741963");
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                PessoaAssistidaCestaBasica cliente = new PessoaAssistidaCestaBasica();
                cliente.setNomeCompleto(rs.getString("nomeCompleto"));
                cliente.setDataNascimento(rs.getString("dataNascimento"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setDataInicio(rs.getString("dataInicio"));
                cliente.setDataEncerramento(rs.getString("dataEncerramento"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public static List<PessoaAssistida> buscarClientesPorNome(String nome) {
        return PessoaAssistida.buscarClientesPorNome(nome, "pessoa_assistida_cesta_basica");
    }








}


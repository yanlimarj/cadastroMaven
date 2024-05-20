package application;

import entities.*;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static entities.PessoaServicosEspecialidade.buscarClientesPorNome;

public class JanelaComAbas extends JFrame {

    JTabbedPane abas = new JTabbedPane();
    JPanel panelCadastro = new JPanel();
    JPanel panelBusca = new JPanel();
    JPanel panelCesta = new JPanel();
    JPanel panelCadastroMedico = new JPanel();
    JPanel panelListaMedicos = new JPanel();
    Listas listas = new Listas();

    private JTextField textFieldNome;
    private JTextField textFieldDataNascimento;
    private JTextField textFieldEndereco;
    private JTextField textFieldTelefone;
    private JTextField textFieldDataInicio;
    private JTextField textFieldDataEncerramento;
    private JTextField textFieldNomeMedico;
    private JTextField textFieldTelefoneMedico;
    private JTextField textFieldMatricula;
    private JTextField textFieldEnderecoMedico;
    private JTextField textFieldEmailMedico;
    private JTextField textFieldFuncaoMedico;
    private JComboBox<String> comboBoxEspecialidadeMedico;
    private JComboBox<String> comboBoxServico;
    private JComboBox<String> comboBoxEspecialidade;
    private JComboBox<String> comboBoxMedico;
    private JComboBox<String> comboBoxEspecialidadeBusca;
    private JComboBox<String> comboBoxMedicoBusca;
    private JComboBox<String> comboBoxEspecialidadeListaMedicos;
    private ArrayList<PessoaServicosEspecialidade> pacientes = new ArrayList<>();
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModelCesta;
    private JTable table;
    private JTable tableCesta;
    private DefaultTableModel tableModelMedicos;
    private JTable tableMedicos;


    public JanelaComAbas() {
        configurarJanela();
        add(BorderLayout.CENTER, abas);
        abas.addTab("Cadastro", panelCadastro);
        abas.addTab("Cadastrar Medicos", panelCadastroMedico);
        abas.addTab("Busca", panelBusca);
        abas.addTab("Cesta Basica", panelCesta);
        abas.addTab("Médicos", panelListaMedicos);
        abas.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(abas.getSelectedIndex() == 3){
                    atualizarTabelaCesta();
                }
                else if(abas.getSelectedIndex() == 4){
                    atualizarTabelaMedicos();
                }
                else if(abas.getSelectedIndex() == 2){
                    atualizarTabela();
                }
            }
        });

        panelCadastro.setBackground(Color.LIGHT_GRAY);
        panelCadastroMedico.setBackground(Color.LIGHT_GRAY);
        panelBusca.setBackground(Color.LIGHT_GRAY);
        panelCesta.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        configurarPanelCadastro();
        configurarPanelCadastroMedico();
        configurarPanelBusca(gbc);


        configurarPanelCesta();

        configurarPanelMedicos();

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void configurarPanelCesta() {

        PessoaAssistidaCestaBasica cliente = new PessoaAssistidaCestaBasica();
        panelCesta.setLayout(new GridBagLayout());
        GridBagConstraints gbcCesta = new GridBagConstraints();

        gbcCesta.gridx = 0;
        gbcCesta.gridy = 0;
        gbcCesta.anchor = GridBagConstraints.WEST;
        panelCesta.add(new JLabel("Nome do Paciente:"), gbcCesta);

        gbcCesta.gridx = 1;
        gbcCesta.fill = GridBagConstraints.HORIZONTAL;
        gbcCesta.weightx = 1.0;
        JTextField txtNomePacienteCesta = new JTextField(20);
        panelCesta.add(txtNomePacienteCesta, gbcCesta);

        gbcCesta.gridx = 0;
        gbcCesta.gridy = 1;
        gbcCesta.gridwidth = 2;
        gbcCesta.fill = GridBagConstraints.BOTH;
        gbcCesta.weightx = 1.0;
        gbcCesta.weighty = 1.0;

        tableModelCesta = new DefaultTableModel();
        tableModelCesta.addColumn("Nome");
        tableModelCesta.addColumn("Nascimento");
        tableModelCesta.addColumn("Endereço");
        tableModelCesta.addColumn("Telefone");
        tableModelCesta.addColumn("Início");
        tableModelCesta.addColumn("Encerramento");

        tableCesta = new JTable(tableModelCesta);
        JScrollPane scrollPaneCesta = new JScrollPane(tableCesta);
        scrollPaneCesta.setPreferredSize(new Dimension(800, 600));
        panelCesta.add(scrollPaneCesta, gbcCesta);

        gbcCesta.gridy++;
        gbcCesta.fill = GridBagConstraints.NONE;
        gbcCesta.weightx = 0;
        gbcCesta.weighty = 0;
        gbcCesta.anchor = GridBagConstraints.CENTER;
        JButton btnBuscar = new JButton("Buscar");
        panelCesta.add(btnBuscar, gbcCesta);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNomePacienteCesta.getText();
                if (!nome.isEmpty()) {
                    List<PessoaAssistida> pacientes = PessoaAssistidaCestaBasica.buscarClientesPorNome(nome);
                    atualizarTabelaCesta(pacientes);
                } else {
                    atualizarTabelaCesta();
                }
            }
        });


        gbcCesta.gridx = 0;
        gbcCesta.gridy = 3;
        JButton btnAlterar = new JButton("Alterar");
        panelCesta.add(btnAlterar, gbcCesta);

        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableCesta.getSelectedRow();
                if (selectedRow != -1){
                    cliente.alterarCliente(selectedRow, tableModelCesta);
                }
                else{
                    JOptionPane.showMessageDialog(panelCesta, "Selecione uma linha para alterar.");
                }
            }
        });


        gbcCesta.gridx = 0;
        gbcCesta.gridy = 4;
        JButton btnExcluir = new JButton("Excluir");
        panelCesta.add(btnExcluir, gbcCesta);

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableCesta.getSelectedRow();
                if (selectedRow != -1){
                    cliente.excluirCliente(selectedRow, tableModelCesta);
                }
                else{
                    JOptionPane.showMessageDialog(panelCesta, "Selecione uma linha para excluir.");
                }
            }
        });
    }

    private void configurarPanelBusca(GridBagConstraints gbc) {
        PessoaServicosEspecialidade paciente = new PessoaServicosEspecialidade();

        panelBusca.setLayout(new GridBagLayout());

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panelBusca.add(new JLabel("Nome do Paciente:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField txtNomePaciente = new JTextField(20);
        panelBusca.add(txtNomePaciente, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelBusca.add(new JLabel("Especialidade:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        comboBoxEspecialidadeBusca = new JComboBox<>();
        comboBoxEspecialidadeBusca.addItem("Todas");
        for (String especialidade : listas.getListaEspecialidades()) {
            comboBoxEspecialidadeBusca.addItem(especialidade);
        }
        panelBusca.add(comboBoxEspecialidadeBusca, gbc);

        gbc.gridy++;
        panelBusca.add(new JLabel("Médico:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        comboBoxMedicoBusca = new JComboBox<>();
        comboBoxMedicoBusca.addItem("Selecione");
        panelBusca.add(comboBoxMedicoBusca, gbc);

        comboBoxEspecialidadeBusca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preencherMedicosBusca();
            }
        });


        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Nome");
        tableModel.addColumn("Nascimento");
        tableModel.addColumn("Endereço");
        tableModel.addColumn("Telefone");
        tableModel.addColumn("Início");
        tableModel.addColumn("Especialidade");
        tableModel.addColumn("Médico");


        TableColumn column = null;
        for (int i = 0; i < 7; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 1 || i == 3 || i == 5 || i == 2) {
                column.setPreferredWidth(150);
            } else {
                column.setPreferredWidth(100);
            }
        }
        panelBusca.add(table, gbc);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        panelBusca.add(scrollPane, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnBuscar = new JButton("Buscar");
        panelBusca.add(btnBuscar, gbc);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNomePaciente.getText();
                if (!nome.isEmpty()){
                    List<PessoaAssistida> pacientes = buscarClientesPorNome(nome);
                    atualizarTabela(pacientes);
                }
                else {
                    atualizarTabela();
                }
            }
        });

        gbc.gridy++;
        JButton btnAlterarMedico = new JButton("Alterar");
        panelBusca.add(btnAlterarMedico, gbc);

        btnAlterarMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1){
                    paciente.alterarCliente(selectedRow, tableModel);
                }
                else{
                    JOptionPane.showMessageDialog(panelBusca, "Selecione uma linha para alterar.");
                }
            }
        });
        gbc.gridy++;
        JButton btnExcluir = new JButton("Excluir");
        panelBusca.add(btnExcluir, gbc);

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1){
                    paciente.excluirCliente(selectedRow, tableModel);
                }
                else{
                    JOptionPane.showMessageDialog(panelBusca, "Selecione uma linha para excluir.");
                }
            }
        });
    }

    private void configurarPanelCadastroMedico() {
        panelCadastroMedico.setLayout(new GridBagLayout());
        GridBagConstraints gbcMedico = new GridBagConstraints();
        gbcMedico.fill = GridBagConstraints.HORIZONTAL;
        gbcMedico.insets = new Insets(5, 5, 5, 5);


        gbcMedico.gridx = 0;
        gbcMedico.gridy = 0;
        panelCadastroMedico.add(new JLabel("Nome:"), gbcMedico);

        gbcMedico.gridx = 1;
        textFieldNomeMedico = new JTextField(20);
        panelCadastroMedico.add(textFieldNomeMedico, gbcMedico);

        gbcMedico.gridx = 0;
        gbcMedico.gridy++;
        panelCadastroMedico.add(new JLabel("Telefone:"), gbcMedico);

        gbcMedico.gridx = 1;
        textFieldTelefoneMedico = new JTextField(20);
        panelCadastroMedico.add(textFieldTelefoneMedico, gbcMedico);

        gbcMedico.gridx = 0;
        gbcMedico.gridy++;
        panelCadastroMedico.add(new JLabel("Matrícula:"), gbcMedico);

        gbcMedico.gridx = 1;
        textFieldMatricula = new JTextField(20);
        panelCadastroMedico.add(textFieldMatricula, gbcMedico);

        gbcMedico.gridx = 0;
        gbcMedico.gridy++;
        panelCadastroMedico.add(new JLabel("Endereço:"), gbcMedico);

        gbcMedico.gridx = 1;
        textFieldEnderecoMedico = new JTextField(20);
        panelCadastroMedico.add(textFieldEnderecoMedico, gbcMedico);

        gbcMedico.gridx = 0;
        gbcMedico.gridy++;
        panelCadastroMedico.add(new JLabel("Email:"), gbcMedico);

        gbcMedico.gridx = 1;
        textFieldEmailMedico = new JTextField(20);
        panelCadastroMedico.add(textFieldEmailMedico, gbcMedico);

        gbcMedico.gridx = 0;
        gbcMedico.gridy++;
        panelCadastroMedico.add(new JLabel("Função:"), gbcMedico);

        gbcMedico.gridx = 1;
        textFieldFuncaoMedico = new JTextField(20);
        panelCadastroMedico.add(textFieldFuncaoMedico, gbcMedico);

        gbcMedico.gridx = 0;
        gbcMedico.gridy++;
        panelCadastroMedico.add(new JLabel("Especialidade:"), gbcMedico);

        gbcMedico.gridx = 1;
        comboBoxEspecialidadeMedico = new JComboBox<>();
        for (String servico : listas.getListaEspecialidades()) {
            comboBoxEspecialidadeMedico.addItem(servico);
        }
        panelCadastroMedico.add(comboBoxEspecialidadeMedico, gbcMedico);


        gbcMedico.gridx = 0;
        gbcMedico.gridy++;
        gbcMedico.gridwidth = 2;
        gbcMedico.anchor = GridBagConstraints.CENTER;
        JButton btnCadastrarMedico = new JButton("Cadastrar Médico");
        panelCadastroMedico.add(btnCadastrarMedico, gbcMedico);

        btnCadastrarMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nome = textFieldNomeMedico.getText();
                String telefone = textFieldTelefoneMedico.getText();
                String matricula = textFieldMatricula.getText();
                String endereco = textFieldEnderecoMedico.getText();
                String email = textFieldEmailMedico.getText();
                String funcao = textFieldFuncaoMedico.getText();
                String especialidade = (String) comboBoxEspecialidadeMedico.getSelectedItem();


                Medico novoMedico = new Medico(nome, telefone, matricula, endereco, email, funcao, especialidade);

                novoMedico.cadastrarMedico();


                JOptionPane.showMessageDialog(null, "Médico cadastrado com sucesso!", "Cadastro de Médico", JOptionPane.INFORMATION_MESSAGE);

                limparCamposMedico();
            }
        });
    }

    private void configurarPanelCadastro() {
        panelCadastro.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCadastro.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        textFieldNome = new JTextField(20);
        panelCadastro.add(textFieldNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCadastro.add(new JLabel("Data de Nascimento:"), gbc);

        gbc.gridx = 1;
        textFieldDataNascimento = new JTextField(20);
        panelCadastro.add(textFieldDataNascimento, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCadastro.add(new JLabel("Endereço:"), gbc);

        gbc.gridx = 1;
        textFieldEndereco = new JTextField(20);
        panelCadastro.add(textFieldEndereco, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCadastro.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;
        textFieldTelefone = new JTextField(20);
        panelCadastro.add(textFieldTelefone, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCadastro.add(new JLabel("Data de Início:"), gbc);

        gbc.gridx = 1;
        textFieldDataInicio = new JTextField(20);
        panelCadastro.add(textFieldDataInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCadastro.add(new JLabel("Data de Encerramento:"), gbc);

        gbc.gridx = 1;
        textFieldDataEncerramento = new JTextField(20);
        panelCadastro.add(textFieldDataEncerramento, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCadastro.add(new JLabel("Serviço:"), gbc);

        gbc.gridx = 1;
        comboBoxServico = new JComboBox<>();
        comboBoxServico.addItem("Cesta Básica");
        comboBoxServico.addItem("Tratamento");
        panelCadastro.add(comboBoxServico, gbc);

        comboBoxServico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String servicoSelecionado = (String) comboBoxServico.getSelectedItem();
                boolean isTratamento = servicoSelecionado.equals("Tratamento");
                comboBoxEspecialidade.setEnabled(isTratamento);
                comboBoxMedico.setEnabled(isTratamento);

            }
        });

        gbc.gridx = 0;
        gbc.gridy++;
        panelCadastro.add(new JLabel("Especialidade:"), gbc);

        gbc.gridx = 1;
        comboBoxEspecialidade = new JComboBox<>();
        for (String especialidade : listas.getListaEspecialidades()) {
            comboBoxEspecialidade.addItem(especialidade);
        }
        panelCadastro.add(comboBoxEspecialidade, gbc);

        comboBoxEspecialidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preencherMedicos();
            }
        });


        gbc.gridx = 0;
        gbc.gridy++;
        panelCadastro.add(new JLabel("Médico:"), gbc);

        gbc.gridx = 1;
        comboBoxMedico = new JComboBox<>();
        comboBoxMedico.addItem("Selecione");
        panelCadastro.add(comboBoxMedico, gbc);

        comboBoxEspecialidade.setEnabled(false);
        comboBoxMedico.setEnabled(false);


        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnCadastrar = new JButton("Cadastrar");
        panelCadastro.add(btnCadastrar, gbc);

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });
    }

    private void configurarPanelMedicos() {
        panelListaMedicos.setLayout(new GridBagLayout());
        GridBagConstraints gbcListaMedicos = new GridBagConstraints();
        gbcListaMedicos.gridx = 0;
        gbcListaMedicos.gridy = 0;
        gbcListaMedicos.anchor = GridBagConstraints.WEST;
        gbcListaMedicos.insets = new Insets(5, 5, 5, 5);

        panelListaMedicos.add(new JLabel("Especialidade:"), gbcListaMedicos);

        gbcListaMedicos.gridy++;
        comboBoxEspecialidadeListaMedicos = new JComboBox<>();
        comboBoxEspecialidadeListaMedicos.addItem("Todas");
        for (String especialidade : listas.getListaEspecialidades()) {
            comboBoxEspecialidadeListaMedicos.addItem(especialidade);
        }
        panelListaMedicos.add(comboBoxEspecialidadeListaMedicos, gbcListaMedicos);

        gbcListaMedicos.gridy++;
        gbcListaMedicos.fill = GridBagConstraints.BOTH;
        gbcListaMedicos.weightx = 1.0;
        gbcListaMedicos.weighty = 1.0;
        tableModelMedicos = new DefaultTableModel();
        tableMedicos = new JTable(tableModelMedicos);
        tableModelMedicos.addColumn("Nome");
        tableModelMedicos.addColumn("Telefone");
        tableModelMedicos.addColumn("Matrícula");
        tableModelMedicos.addColumn("Endereço");
        tableModelMedicos.addColumn("Email");
        tableModelMedicos.addColumn("Função");
        tableModelMedicos.addColumn("Especialidade");

        JScrollPane scrollPaneMedicos = new JScrollPane(tableMedicos);
        scrollPaneMedicos.setPreferredSize(new Dimension(800, 600));
        panelListaMedicos.add(scrollPaneMedicos, gbcListaMedicos);

        gbcListaMedicos.gridy++;
        gbcListaMedicos.fill = GridBagConstraints.NONE;
        gbcListaMedicos.weightx = 0;
        gbcListaMedicos.weighty = 0;
        gbcListaMedicos.anchor = GridBagConstraints.CENTER;
        JButton btnBuscarMedicos = new JButton("Buscar");
        panelListaMedicos.add(btnBuscarMedicos, gbcListaMedicos);

        btnBuscarMedicos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTabelaMedicos();
            }
        });

        gbcListaMedicos.gridy++;
        JButton btnAlterarMedico = new JButton("Alterar Médico");
        panelListaMedicos.add(btnAlterarMedico, gbcListaMedicos);

        btnAlterarMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarMedico();
            }
        });

        gbcListaMedicos.gridy++;
        JButton btnExcluirMedico = new JButton("Excluir Médico");
        panelListaMedicos.add(btnExcluirMedico, gbcListaMedicos);

        btnExcluirMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirMedico();
            }
        });

        comboBoxEspecialidadeListaMedicos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTabelaMedicos();
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void alterarMedico() {
        int linhaSelecionada = tableMedicos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um médico para alterar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }


        String nome = (String) tableModelMedicos.getValueAt(linhaSelecionada, 0);
        String telefone = (String) tableModelMedicos.getValueAt(linhaSelecionada, 1);
        String matricula = (String) tableModelMedicos.getValueAt(linhaSelecionada, 2);
        String endereco = (String) tableModelMedicos.getValueAt(linhaSelecionada, 3);
        String email = (String) tableModelMedicos.getValueAt(linhaSelecionada, 4);
        String funcao = (String) tableModelMedicos.getValueAt(linhaSelecionada, 5);
        String especialidade = (String) tableModelMedicos.getValueAt(linhaSelecionada, 6);


        Medico medico = new Medico(nome, telefone, matricula, endereco, email, funcao, especialidade);


        medico.alterarMedico();


        atualizarTabelaMedicos();
    }

    private void atualizarTabelaMedicos() {
        String especialidadeSelecionada = (String) comboBoxEspecialidadeListaMedicos.getSelectedItem();
        List<Medico> medicos;
        if ("Todas".equals(especialidadeSelecionada)) {
            medicos = Medico.buscarTodosMedicos();
        } else {
            medicos = Medico.buscarMedicosPorEspecialidade(especialidadeSelecionada);
        }

        tableModelMedicos.setRowCount(0);

        for (Medico medico : medicos) {
            Object[] rowData = {
                    medico.getNome(),
                    medico.getTelefone(),
                    medico.getMatricula(),
                    medico.getEndereco(),
                    medico.getEmail(),
                    medico.getFuncao(),
                    medico.getEspecialidade()
            };
            tableModelMedicos.addRow(rowData);
        }
    }

    private void excluirMedico() {
        int linhaSelecionada = tableMedicos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um médico para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este médico?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {

            int matricula = (int) tableModelMedicos.getValueAt(linhaSelecionada, 2);


            Medico medico = new Medico();
            medico.setMatricula(String.valueOf(matricula));


            medico.excluirMedico();


            atualizarTabelaMedicos();
        }
    }

    private void atualizarTabelaCesta(List<PessoaAssistida> pacientes) {
        tableModelCesta.setRowCount(0);

        for (PessoaAssistida pessoaAssistida : pacientes) {
            tableModelCesta.addRow(new Object[]{
                    pessoaAssistida.getNomeCompleto(),
                    pessoaAssistida.getDataNascimento(),
                    pessoaAssistida.getEndereco(),
                    pessoaAssistida.getTelefone(),
                    pessoaAssistida.getDataInicio(),
                    pessoaAssistida.getDataEncerramento()
            });
        }
    }

    private void atualizarTabelaCesta() {

        List<PessoaAssistidaCestaBasica> clientes = PessoaAssistidaCestaBasica.buscarTodos();

        tableModelCesta.setRowCount(0);

        for (PessoaAssistidaCestaBasica cliente : clientes) {
            tableModelCesta.addRow(new Object[]{
                    cliente.getNomeCompleto(),
                    cliente.getDataNascimento(),
                    cliente.getEndereco(),
                    cliente.getTelefone(),
                    cliente.getDataInicio(),
                    cliente.getDataEncerramento()
            });
        }
    }

    private void atualizarTabela(List<PessoaAssistida> pacientes) {
        tableModel.setRowCount(0);

        for (PessoaAssistida pessoaAssistida : pacientes) {
            String especialidade = "";
            String nomeMedico = "";

            if (pessoaAssistida instanceof PessoaServicosEspecialidade) {
                PessoaServicosEspecialidade clienteEspecial = (PessoaServicosEspecialidade) pessoaAssistida;
                especialidade = clienteEspecial.getEspecialidade();
                nomeMedico = clienteEspecial.getNomeMedico();
            }

            tableModel.addRow(new Object[]{
                    pessoaAssistida.getNomeCompleto(),
                    pessoaAssistida.getDataNascimento(),
                    pessoaAssistida.getEndereco(),
                    pessoaAssistida.getTelefone(),
                    pessoaAssistida.getDataInicio(),
                    especialidade,
                    nomeMedico
            });
        }
    }

    private void atualizarTabela() {
        String nomeMedico = (String) comboBoxMedicoBusca.getSelectedItem();
        String especialidade = (String) comboBoxEspecialidadeBusca.getSelectedItem();

        List<PessoaServicosEspecialidade> pacientes;
        if("Todas".equals(especialidade)){
            pacientes = PessoaServicosEspecialidade.buscarTodos();
        }
        else{
            pacientes = PessoaServicosEspecialidade.buscarPorMedico(nomeMedico);
        }

        tableModel.setRowCount(0);

        for (PessoaServicosEspecialidade paciente : pacientes) {
            Object[] rowData = {
                    paciente.getNomeCompleto(),
                    paciente.getDataNascimento(),
                    paciente.getEndereco(),
                    paciente.getTelefone(),
                    paciente.getDataInicio(),
                    paciente.getEspecialidade(),
                    paciente.getNomeMedico()
            };
            tableModel.addRow(rowData);
        }
    }

    public void configurarJanela() {
        setTitle("Janela Com Abas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cadastrarCliente() {

        String nome = textFieldNome.getText();
        String dataNascimento = textFieldDataNascimento.getText();
        String endereco = textFieldEndereco.getText();
        String telefone = textFieldTelefone.getText();
        String dataInicio = textFieldDataInicio.getText();
        String dataEncerramento = textFieldDataEncerramento.getText();
        String servico = (String) comboBoxServico.getSelectedItem();
        String especialidade = (String) comboBoxEspecialidade.getSelectedItem();
        String medico = (String) comboBoxMedico.getSelectedItem();


        if (nome.isEmpty() || dataNascimento.isEmpty() || endereco.isEmpty() || telefone.isEmpty() || dataInicio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }


        PessoaAssistida pessoaAssistida;


        if (servico.equals("Cesta Básica")) {

            pessoaAssistida = new PessoaAssistidaCestaBasica(nome, dataNascimento, endereco, telefone, dataInicio, dataEncerramento);
        } else {
            if (especialidade == null || especialidade.isEmpty() || medico == null || medico.equals("Selecione")) {
                JOptionPane.showMessageDialog(this, "Especialidade e médico são obrigatórios para tratamentos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            pessoaAssistida = new PessoaServicosEspecialidade(nome, dataNascimento, endereco, telefone, dataInicio, especialidade, medico);

        }


        if (servico.equals("Cesta Básica")) {

            pessoaAssistida.cadastrarCliente();
            JOptionPane.showMessageDialog(this, "Cliente da Cesta Basica cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {

            pessoaAssistida.cadastrarCliente();
            JOptionPane.showMessageDialog(this, "Paciente do tratamento cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        limparCampos();
    }

    private void limparCampos() {
        textFieldNome.setText("");
        textFieldDataNascimento.setText("");
        textFieldEndereco.setText("");
        textFieldTelefone.setText("");
        textFieldDataInicio.setText("");
        textFieldDataEncerramento.setText("");
        comboBoxServico.setSelectedIndex(0);
        comboBoxEspecialidade.setSelectedIndex(0);
        comboBoxMedico.removeAllItems();
        textFieldDataEncerramento.setText("");
    }

    private void limparCamposMedico() {
        textFieldNomeMedico.setText("");
        textFieldTelefoneMedico.setText("");
        textFieldMatricula.setText("");
        textFieldEnderecoMedico.setText("");
        textFieldEmailMedico.setText("");
        textFieldFuncaoMedico.setText("");
        comboBoxEspecialidadeMedico.setSelectedIndex(0);
    }

    private void preencherMedicos() {
        String especialidadeSelecionada = (String) comboBoxEspecialidade.getSelectedItem();
        comboBoxMedico.removeAllItems();

        if (!especialidadeSelecionada.isEmpty()) {
            List<Medico> medicos = Medico.buscarMedicosPorEspecialidade(especialidadeSelecionada);
            for (Medico medico : medicos) {
                comboBoxMedico.addItem(medico.getNome());
            }
        }
    }

    private void preencherMedicosBusca() {
        String especialidadeSelecionada = (String) comboBoxEspecialidadeBusca.getSelectedItem();
        comboBoxMedicoBusca.removeAllItems();

        if (!especialidadeSelecionada.isEmpty()) {
            List<Medico> medicos = Medico.buscarMedicosPorEspecialidade(especialidadeSelecionada);
            for (Medico medico : medicos) {
                comboBoxMedicoBusca.addItem(medico.getNome());
            }
        }
    }

}

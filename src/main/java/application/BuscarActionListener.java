package application;

import entities.ClienteServicosEspecialidade;
import entities.Listas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class BuscarActionListener implements ActionListener {
    private Listas listas;
    private JComboBox<String> comboBoxEspecialidadeBusca;
    private JComboBox<String> comboBoxMedicoBusca;
    private JTable table;
    private DefaultTableModel tableModel;

    public BuscarActionListener(Listas listas, JComboBox<String> comboBoxEspecialidadeBusca, JComboBox<String> comboBoxMedicoBusca, JTable table) {
        this.listas = listas;
        this.comboBoxEspecialidadeBusca = comboBoxEspecialidadeBusca;
        this.comboBoxMedicoBusca = comboBoxMedicoBusca;
        this.table = table;
        this.tableModel = (DefaultTableModel) table.getModel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<ClienteServicosEspecialidade> pacientes = listas.buscarPacientes(comboBoxEspecialidadeBusca, comboBoxMedicoBusca);
        tableModel.setRowCount(0);

        for (ClienteServicosEspecialidade paciente : pacientes) {
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
}

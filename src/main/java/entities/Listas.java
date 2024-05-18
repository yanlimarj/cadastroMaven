package entities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Listas extends Component {

    private final ArrayList<String> listaEspecialidades;

    private final ArrayList<String> listaMedicosFisioterapia;

    private final ArrayList<String> listaMedicosFonoaudiologia;

    private final ArrayList<String> listaMedicosTratamentoNeural;

    private final ArrayList<String> listaMedicosPsicologia;

    private final ArrayList<ClienteCestaBasica> listaClienteCestaBasica;

    private final ArrayList<ClienteServicosEspecialidade> listaRenataSilva;

    private final ArrayList<ClienteServicosEspecialidade> listaAndreSantos;

    private final ArrayList<ClienteServicosEspecialidade> listaJulianaMendes;

    private final ArrayList<ClienteServicosEspecialidade> listaThiagoOliveira;

    private final ArrayList<ClienteServicosEspecialidade> listaMarcosCosta;

    private final ArrayList<ClienteServicosEspecialidade> listaCarolinaFernandes;

    private final ArrayList<ClienteServicosEspecialidade> listaAnaTorres;

    private final ArrayList<ClienteServicosEspecialidade> listaLucasPereira;

    public Listas() {

        listaClienteCestaBasica = new ArrayList<>();

        listaEspecialidades = new ArrayList<>();
        listaEspecialidades.add("Fisioterapia");
        listaEspecialidades.add("Fonoaudiologia");
        listaEspecialidades.add("Tratamento Neural");
        listaEspecialidades.add("Psicologia");

        listaMedicosFisioterapia = new ArrayList<>();
        listaMedicosFisioterapia.add("Dra. Renata Silva");
        listaMedicosFisioterapia.add("Dr. André Santos");

        listaRenataSilva = new ArrayList<>();
        listaAndreSantos = new ArrayList<>();

        listaMedicosFonoaudiologia = new ArrayList<>();
        listaMedicosFonoaudiologia.add("Dra. Juliana Mendes");
        listaMedicosFonoaudiologia.add("Dr. Thiago Oliveira");

        listaJulianaMendes = new ArrayList<>();
        listaThiagoOliveira = new ArrayList<>();

        listaMedicosTratamentoNeural = new ArrayList<>();
        listaMedicosTratamentoNeural.add("Dr. Marcos Costa");
        listaMedicosTratamentoNeural.add("Dra. Carolina Fernandes");

        listaMarcosCosta = new ArrayList<>();
        listaCarolinaFernandes = new ArrayList<>();

        listaMedicosPsicologia = new ArrayList<>();
        listaMedicosPsicologia.add("Dra. Ana Torres");
        listaMedicosPsicologia.add("Dr. Lucas Pereira");

        listaAnaTorres = new ArrayList<>();
        listaLucasPereira = new ArrayList<>();



    }

    public void adicionarPaciente(String nomeMedico, ClienteServicosEspecialidade paciente) {

        if (nomeMedico.equals("Dra. Ana Torres")) {
            listaAnaTorres.add(paciente);
        } else if (nomeMedico.equals("Dr. Lucas Pereira")) {
            listaLucasPereira.add(paciente);
        } else if (nomeMedico.equals("Dr. Marcos Costa")) {
            listaMarcosCosta.add(paciente);
        } else if (nomeMedico.equals("Dra. Carolina Fernandes")) {
            listaCarolinaFernandes.add(paciente);
        } else if (nomeMedico.equals("Dra. Juliana Mendes")) {
            listaJulianaMendes.add(paciente);
        } else if (nomeMedico.equals("Dr. Thiago Oliveira")) {
            listaThiagoOliveira.add(paciente);
        } else if (nomeMedico.equals("Dra. Renata Silva")) {
            listaRenataSilva.add(paciente);
        } else if (nomeMedico.equals("Dr. André Santos")) {
            listaAndreSantos.add(paciente);
        }


    }

    public ArrayList<ClienteServicosEspecialidade> buscarPacientes(JComboBox<String> comboBoxEspecialidadeBusca, JComboBox<String> comboBoxMedicoBusca) {
        String especialidadeSelecionada = (String) comboBoxEspecialidadeBusca.getSelectedItem();
        String medicoSelecionado = (String) comboBoxMedicoBusca.getSelectedItem();
        ArrayList<ClienteServicosEspecialidade> pacientes = new ArrayList<>();

        if (comboBoxMedicoBusca.getSelectedItem() == null || comboBoxMedicoBusca.getSelectedItem().equals("Selecione")) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um médico.", "Erro", JOptionPane.ERROR_MESSAGE);
            return pacientes;
        }

        if (especialidadeSelecionada.equals("Fisioterapia")) {
            if (medicoSelecionado.equals("Dra. Renata Silva")) {
                pacientes = listaRenataSilva;
            } else if (medicoSelecionado.equals("Dr. André Santos")) {
                pacientes = listaAndreSantos;
            }
        } else if (especialidadeSelecionada.equals("Fonoaudiologia")) {
            if (medicoSelecionado.equals("Dra. Juliana Mendes")) {
                pacientes = listaJulianaMendes;
            } else if (medicoSelecionado.equals("Dr. Thiago Oliveira")) {
                pacientes = listaThiagoOliveira;
            }
        } else if (especialidadeSelecionada.equals("Tratamento Neural")) {
            if (medicoSelecionado.equals("Dr. Marcos Costa")) {
                pacientes = listaMarcosCosta;
            } else if (medicoSelecionado.equals("Dra. Carolina Fernandes")) {
                pacientes = listaCarolinaFernandes;
            }
        } else if (especialidadeSelecionada.equals("Psicologia")) {
            if (medicoSelecionado.equals("Dra. Ana Torres")) {
                pacientes = listaAnaTorres;
            } else if (medicoSelecionado.equals("Dr. Lucas Pereira")) {
                pacientes = listaLucasPereira;
            }
        }

        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum paciente encontrado para o médico selecionado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }

        return pacientes;
    }

    public void adicionarClienteCestaBasica(ClienteCestaBasica cliente){
        listaClienteCestaBasica.add(cliente);
    }

    public String obterNomeEspecialide(int indice) {
        return listaEspecialidades.get(indice);
    }

    public void mostrarEspecialides() {
        for (int i = 0; i < listaEspecialidades.size(); i++) {
            System.out.println((i + 1) + "- " + listaEspecialidades.get(i));
        }
    }


    public String[] getListaEspecialidades() {
        return listaEspecialidades.toArray(new String[0]);
    }

    public String[] getListaMedicosFisioterapia() {
        return listaMedicosFisioterapia.toArray(new String[0]);
    }
    public String[] getListaMedicosFonoaudiologia() {
        return listaMedicosFonoaudiologia.toArray(new String[0]);
    }
    public String[] getListaMedicosTratamentoNeural() {
        return listaMedicosTratamentoNeural.toArray(new String[0]);
    }
    public String[] getListaMedicosPsicologia() {
        return listaMedicosPsicologia.toArray(new String[0]);
    }

    public ArrayList<ClienteCestaBasica> getListaClienteCestaBasica() {
        return listaClienteCestaBasica;
    }

    public ThreadLocal<Object> gerListaPacientesPorMedico() {
        return null;
    }
}

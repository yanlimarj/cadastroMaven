package entities;

import java.awt.*;
import java.util.ArrayList;


public class Listas extends Component {

    private final ArrayList<String> listaEspecialidades;

    private final ArrayList<PessoaAssistidaCestaBasica> listaClienteCestaBasica;

    public Listas() {

        listaClienteCestaBasica = new ArrayList<>();

        listaEspecialidades = new ArrayList<>();
        listaEspecialidades.add("Fisioterapia");
        listaEspecialidades.add("Fonoaudiologia");
        listaEspecialidades.add("Tratamento Neural");
        listaEspecialidades.add("Psicologia");


    }


    public String[] getListaEspecialidades() {
        return listaEspecialidades.toArray(new String[0]);
    }


}

package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Leitor {
    public List<String> LerTXT() {

        List<String> listaDePerguntas = new ArrayList<>();

        File arquivo = new File("src/form/formulario.txt");

        //Para rodar na IDE:
        if(arquivo.exists()) {
            try {
                BufferedReader leitorDePerguntas = new BufferedReader(new FileReader(arquivo));
                String linhaAserLida;
                while ((linhaAserLida = leitorDePerguntas.readLine()) != null) {
                    listaDePerguntas.add(linhaAserLida);
                }
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        } else {
            //Para rodar no terminal:
            arquivo = new File("form/formulario.txt");
            try {
                BufferedReader leitorDePerguntas = new BufferedReader(new FileReader(arquivo));
                String linhaAserLida;
                while ((linhaAserLida = leitorDePerguntas.readLine()) != null) {
                    listaDePerguntas.add(linhaAserLida);
                }
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
        return listaDePerguntas;
    }
}

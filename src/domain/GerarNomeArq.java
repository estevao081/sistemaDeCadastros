package domain;

import java.io.File;

public class GerarNomeArq {
    public static String gerarNomeArquivo(File pasta, String nomeUsuario, int id) {
        String nomeBase = nomeUsuario.toUpperCase().replaceAll("\\s+", "");
        return id + "-" + nomeBase;
    }
}

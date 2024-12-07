package domain;

import java.io.File;

public class GerarNomeArq {
    public static String gerarNomeArquivo(File pasta, String nomeUsuario) {

        String nomeBase = nomeUsuario.toUpperCase().replaceAll("\\s+", "");
        int numero = 1;

        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                String nome = arquivo.getName();
                if (!nome.startsWith(nomeBase) && nome.contains("-")) {
                    String[] partes = nome.split("-");
                    try {
                        int numArquivo = Integer.parseInt(partes[0]);
                        if (numArquivo >= numero) {
                            numero = numArquivo + 1;
                        }
                    } catch (NumberFormatException ignored) {
                        System.out.println("Nome de arquivo imcompatível!");
                    }
                }
            }
        }
        return numero + "-" + nomeBase + ".txt";
    }
}

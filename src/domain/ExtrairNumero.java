package domain;

public class ExtrairNumero {
    public static int extrairNumero(String nomeArquivo) {
        int index = nomeArquivo.indexOf("-");
        if (index > 0) {
            return Integer.parseInt(nomeArquivo.substring(0, index));
        }
        return Integer.MAX_VALUE; // Coloca arquivos sem número no final
    }
}

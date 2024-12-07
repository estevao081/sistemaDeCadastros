package domain;

public class ExtrairNumero {
    public static int extrairNumero(String nomeArquivo) {
        try {
            int index = nomeArquivo.indexOf("-");
            if (index > 0) {
                return Integer.parseInt(nomeArquivo.substring(0, index));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return Integer.MAX_VALUE; // Coloca arquivos sem número no final
    }
}

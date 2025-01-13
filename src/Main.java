import domain.*;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);

        String diretorio = "usuarios";
        File pasta = new File(diretorio);

        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        boolean wl = true;

        try {
            while (wl) {
                System.out.println(" ");
                System.out.println("1 - Cadastrar Usuário");
                System.out.println("2 - Listar todos usuários cadastrados");
                System.out.println("3 - Cadastrar nova pergunta no formulário");
                System.out.println("4 - Deletar pergunta do formulário");
                System.out.println("5 - Pesquisar usuário por nome / idade / email");
                System.out.println("0 - Encerrar");

                int opcao = scan.nextInt();
                switch (opcao) {
                    case 1:
                        cadastrarUsuario(pasta);
                        break;
                    case 2:
                        listarUsuarios(pasta);
                        break;
                    case 3:
                        adicionarPergunta();
                        break;
                    case 4:
                        deletarPergunta();
                        break;
                    case 5:
                        buscarUsuario(pasta);
                        break;
                    case 0:
                        System.out.println("\nEncerrando...");
                        wl = false;
                        break;
                    default:
                        System.out.print("\nSelecione uma opção válida!");
                        break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Opção inválida!");
        }
        scan.close();
    }

    public static void cadastrarUsuario(File pasta) {

        Scanner scan = new Scanner(System.in, "UTF-8");
        Leitor leitorArquivo = new Leitor();
        Usuario usuario = new Usuario();

        File[] arquivos = pasta.listFiles();

        List<String> perguntasLidasDoTXT = leitorArquivo.LerTXT();
        List<String> respostas = new ArrayList<>();

        for (String pergunta : perguntasLidasDoTXT) {
            System.out.println(pergunta);
            respostas.add(scan.nextLine().strip());
        }

        if (pasta == null) {
            usuario.setId(1);
        } else {
            usuario.setId(arquivos.length + 1);
        }

        // Validação do nome de usuário:
        try {
            String rn = respostas.get(0);
            if (rn.length() >= 10 && rn.matches("^[A-Za-zÀ-ÖØ-öø-ÿ'’´`\\s-]+$")) {
                usuario.setNome(respostas.get(0));
            } else {
                System.out.println("\nO nome de usuário deve conter no mínimo 10 caracteres e não pode conter caracteres especiais!");
                usuario.setNome(respostas.get(0));
                System.out.println(usuario.getNome());
                return;
            }
        } catch (NullPointerException e) {
            System.out.println("\nO nome de usuário não pode ser nulo!");
        }

        // Validação do e-mail do usuário:
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    List<String> linhas = new ArrayList<>();
                    String linha;
                    while ((linha = br.readLine()) != null) {
                        linhas.add(linha);
                    }
                    if (linhas.contains(respostas.get(1))) {
                        System.out.println("\nE-mail já cadastrado!");
                        return;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        try {
            String rm = respostas.get(1);
            if (rm.contains("@") && rm.contains(".")) {
                usuario.setEmail(respostas.get(1));
            } else {
                System.out.println("\nO e-mail informado é inválido!");
                return;
            }
        } catch (NullPointerException e) {
            System.out.println("\nO e-mail do usuário não pode ser nulo!");
        }

        // Validação da idade do usuário:
        try {
            if (Integer.parseInt(respostas.get(2)) >= 18 && Integer.parseInt(respostas.get(2)) < 120) {
                usuario.setIdade(Integer.parseInt(respostas.get(2)));
            } else {
                System.out.println("\nO usuário deve ter uma idade válida!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("\nCampo idade deve conter apenas números!");
            return;
        }

        // Validação da altura do usuário:
        try {
            if (respostas.get(3).contains(".")) {
                usuario.setAltura(Float.parseFloat(respostas.get(3).replace(".", ",")));
            } else {
                usuario.setAltura(Float.parseFloat(respostas.get(3)));
            }
        } catch (NumberFormatException e) {
            e.fillInStackTrace();
        }

        String nomeArq = GerarNomeArq.gerarNomeArquivo(pasta, usuario.getNome(), usuario.getId());
        File file = new File(pasta, nomeArq);
        try (PrintWriter pw = new PrintWriter(file)) {
            for (String resposta : respostas) {
                pw.println(resposta);
            }
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("\nUsuário cadastrado com sucesso!");
    }

    public static void listarUsuarios(File pasta) {

        File[] arquivos = pasta.listFiles();

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("\nNão existem usuários cadastrados na pasta: " + pasta.getAbsolutePath());
            return;
        }

        Arrays.sort(arquivos, (f1, f2) -> {
            int num1 = ExtrairNumero.extrairNumero(f1.getName());
            int num2 = ExtrairNumero.extrairNumero(f2.getName());
            return Integer.compare(num1, num2);
        });

        System.out.println("\nExibindo lista de usuários cadastrados:");

        List<File> userList = new ArrayList<>(List.of(arquivos));
        for (File arquivo : userList) {
            String arq = arquivo.toString();
            arq = arq.replaceAll(".*\\\\", "");
            arq = arq.replaceAll("[\\[\\]]", "");
            System.out.println(arq);
        }
    }

    public static void adicionarPergunta() throws FileNotFoundException {

        Scanner scan = new Scanner(System.in);
        Leitor leitor = new Leitor();
        File file = new File("src/form/formulario.txt");

        System.out.println("\nDigite a pergunta que deseja adicionar ao formulário:");
        String novaPergunta = scan.nextLine();

        List<String> perguntasLidasDoTXT = leitor.LerTXT();

        int numPerguntas = perguntasLidasDoTXT.size() + 1;
        String perguntaF = numPerguntas + " - " + novaPergunta;

        perguntasLidasDoTXT.add(perguntaF);

        try (PrintWriter pw = new PrintWriter(file)) {
            for (String pergunta : perguntasLidasDoTXT) {
                pw.println(pergunta);
            }
            pw.flush();
        } catch (FileNotFoundException e) {
            //Para rodar no terminal
            file = new File("form/formulario.txt");
            PrintWriter pw = new PrintWriter(file);
            for (String pergunta : perguntasLidasDoTXT) {
                pw.println(pergunta);
            }
            pw.flush();
        }

        System.out.println("\nPergunta adicionada ao formulário com sucesso!");
    }

    public static void deletarPergunta() throws FileNotFoundException {

        Scanner scan = new Scanner(System.in);
        Leitor leitor = new Leitor();

        List<String> perguntasLidasDoTXT = leitor.LerTXT();
        System.out.println("\nDigite o índice de qual pergunta deseja deletar:\n");

        for (String pergunta : perguntasLidasDoTXT) {
            System.out.println(pergunta);
        }

        int indicePergunta = scan.nextInt();

        try {
            if (indicePergunta >= 5) {
                perguntasLidasDoTXT.remove(indicePergunta - 1);
                try (PrintWriter pw = new PrintWriter("src/form/formulario.txt")) {
                    for (String pergunta : perguntasLidasDoTXT) {
                        pw.println(pergunta);
                    }
                    pw.flush();
                } catch (FileNotFoundException e) {
                    //Para rodar no terminal
                    PrintWriter pw = new PrintWriter("form/formulario.txt");
                    for (String pergunta : perguntasLidasDoTXT) {
                        pw.println(pergunta);
                    }
                    pw.flush();
                    e.fillInStackTrace();
                }
                System.out.println("\nPergunta deletada com sucesso!");
            } else if (indicePergunta >= 1 && indicePergunta <= 4) {
                System.out.println("\nNão é possível apagar as quatro perguntas iniciais!");
            } else {
                System.out.println("\nDigite uma opção válida!");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\nOpção inválida!");
        }
    }

    public static void buscarUsuario(File pasta) throws IOException {

        File[] arquivos = pasta.listFiles();
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("\nBuscar usuário por:");
            System.out.println("1 - Nome");
            System.out.println("2 - Idade");
            System.out.println("3 - E-mail");
            int opcao = scan.nextInt();

            if (arquivos != null) {
                switch (opcao) {
                    case 1:
                        Buscar.buscarNome(pasta);
                        break;
                    case 2:
                        Buscar.buscarIdade(pasta);
                        break;
                    case 3:
                        Buscar.buscarEmail(pasta);
                        break;
                }
            } else {
                System.out.println("\nNão existem usuários cadastrados!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Opção inválida!");
        }
    }
}

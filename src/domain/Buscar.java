package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Buscar {
    public static void buscarNome(File pasta) {

        File[] arquivos = pasta.listFiles();
        Scanner scan = new Scanner(System.in);

        System.out.println("\nDigite o nome do usuário que deseja encontrar:");
        String buscarNome = scan.nextLine().toUpperCase();

        if (arquivos != null && arquivos.length > 0) {
            for (File arquivo : arquivos) {
                String nomeArq = arquivo.getName();
                String nomeF = nomeArq.replaceAll("[-\\d]", "");
                if (nomeF.startsWith(buscarNome)) {
                    try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                        System.out.println("\n" + nomeArq);
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                        System.out.println(" ");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("\nUsuário não encontrado!");
                }
            }
        } else {
            System.out.println("\nNão existem usuários cadastrados!");
        }
    }

    public static void buscarIdade(File pasta) {

        File[] arquivos = pasta.listFiles();
        Scanner scan = new Scanner(System.in);

        System.out.println("\nDigite a idade do usuário que deseja econtrar:");
        String buscarIdade = scan.nextLine();

        List<String> encontrados = new ArrayList<>();

        String regex = "^\\d+$";
        Pattern pattern = Pattern.compile(regex);

        for (File arquivo : arquivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String line;
                String arq = arquivo.toString();
                while ((line = br.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.matches() && line.equals(buscarIdade)) {
                        encontrados.add(arq);
                        break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (encontrados.isEmpty()) {
            System.out.println("\nNenhum usuário encontrado com a idade informada!");
        } else {
            System.out.println("\nEncontrados:\n");
            for (String usuarios : encontrados) {
                try (BufferedReader br = new BufferedReader(new FileReader(usuarios))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(" ");
            }
        }
    }

    public static void buscarEmail(File pasta) {

        File[] arquivos = pasta.listFiles();
        Scanner scan = new Scanner(System.in);

        System.out.println("\nDigite o e-mail do usuário que deseja encontrar:");
        String buscarEmail = scan.nextLine();

        List<String> encontrados = new ArrayList<>();

        for (File arquivo : arquivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String line;
                String arq = arquivo.toString();
                while ((line = br.readLine()) != null) {
                    if (line.contains(buscarEmail)) {
                        encontrados.add(arq);
                        break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (encontrados.isEmpty()) {
            System.out.println("\nNenhum usuário encontrado com o e-mail informado!");
        } else {
            System.out.println("\nEncontrado:\n");
            for (String usuarios : encontrados) {
                try (BufferedReader br = new BufferedReader(new FileReader(usuarios))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(" ");
            }
        }
    }
}

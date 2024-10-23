package maquinavendas;

import java.util.Scanner;

public class Ler {
    // Cria uma única instância de Scanner para toda a classe
    private static Scanner scanner = new Scanner(System.in);

    // Construtor privado para evitar instanciar a classe
    private Ler() { }

    // Metodo para ler um número inteiro do input
    public static int getInt(String prompt) {
        System.out.print(prompt); // Exibe o prompt
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, insira um número inteiro válido.");
            scanner.next(); // Limpa a entrada inválida
            System.out.print(prompt);
        }
        int numero=scanner.nextInt();
        limparBuffer();
        return numero;
    }

    // Metodo para ler um número decimal (double) do input
    public static double getDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada inválida. Por favor, insira um número decimal válido.");
            scanner.next(); // Limpa a entrada inválida
            System.out.print(prompt);
        }
        return scanner.nextDouble();
    }

    // Metodo para ler uma palavra do input
    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.next(); // Para uma única palavra
    }

    // Metodo para ler uma linha inteira de texto do input
    public static String getLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine(); // Para linha completa
    }

    public static void limparBuffer(){
        scanner.nextLine();
    }

    // Fecha o Scanner quando o programa terminar
    public static void close() {
        scanner.close();
    }
}

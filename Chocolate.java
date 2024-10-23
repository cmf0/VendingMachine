package maquinavendas;

public class Chocolate extends Produto{

    public Chocolate(String marca, String tipo, double preco) {
        super(marca, tipo, preco);
        nome = "Chocolate";
    }

    public static Chocolate newChocolate() {
        String marca = Ler.getLine("Introduza a marca do chocolate: ");
        String tipo = Ler.getLine("Introduza o tipo de cacau (negro, branco, leite): ");
        double preco = Ler.getDouble("Introduza o preco do produto: ");
        System.out.println("\nPRODUTO ADICIONADO COM SUCESSO");
        return new Chocolate(marca, tipo, preco);
    }
}

package maquinavendas;

public class Sandes extends Produto{

    public Sandes(String marca, String tipo, double preco) {
        super(marca, tipo, preco);
        nome="Sandes";
    }

    public static Sandes newSandes() {
        String marca = Ler.getLine("Introduza o produtor da sandes: ");
        String tipo = Ler.getLine("A sandes e mista, de fiambre ou de queijo?: ");
        double preco = Ler.getDouble("Introduza o preco do produto: ");
        System.out.println("\nPRODUTO ADICIONADO COM SUCESSO");
        return new Sandes(marca, tipo, preco);
    }
}
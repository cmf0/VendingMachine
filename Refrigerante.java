package maquinavendas;

public class Refrigerante extends Produto{

    public Refrigerante(String marca, String tipo, double preco) {
        super(marca, tipo, preco);
        nome = "Refrigerante";
    }

    public static Refrigerante newRefrigerante() {
            String marca = Ler.getLine("Introduza a marca do refrigerante: ");
            String tipo;
            int i;
            do {
                i = Ler.getInt("Indique o tipo de refrigerante: ( 1 - Com acucar | 2 - Sem acucar ): ");
                if (i==1){
                    tipo = "Com Acucar";
                    break;
                }else if (i==2){
                    tipo = "Zero Acucar";
                    break;
                }else System.out.println("\nINTRODUZA UM VALOR VALIDO");
            }while (true);

            double preco = Ler.getDouble("Introduza o preco do produto: ");
            System.out.println("\nPRODUTO ADICIONADO COM SUCESSO");
            return new Refrigerante(marca, tipo, preco);
    }
}

package maquinavendas;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    public Produto(String marca, String tipo, double preco) {
        this.marca = marca;
        this.tipo = tipo;
        this.preco = preco;

    }

    protected String nome;
    protected String tipo;
    protected String marca;
    protected double preco;
    protected LocalDate prazoDeValidade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getPrazoDeValidade() {
        DateTimeFormatter eu = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return prazoDeValidade.format(eu);
    }

    public abstract void setPrazoDeValidade();

    public void mostrarPrazo(){
        System.out.println("Prazo de validade: " + prazoDeValidade);
    }

    public double calcularTroco(double pago){
            return pago-preco;
    }
}

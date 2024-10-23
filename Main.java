package maquinavendas;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MaquinaDeVendas maquina = new MaquinaDeVendas();
        maquina.ligar();
        Ler.close();
    }
}
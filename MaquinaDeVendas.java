package maquinavendas;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

public class MaquinaDeVendas implements Serializable{
    //VERSAO DA SERIALIZACAO
    @Serial
    private static final long serialVersionUID = 1L;

    //ATRIBUTOS
    private final int MAXCHOC = 20; //CAPACIDADE MAXIMA DA MAQUINA PARA CHOCOLATES
    private final int MAXREFRI = 15; //CAPACIDADE MAXIMA DA MAQUINA PARA REFRIGERANTES
    private final int MAXSANDES = 10; //CAPACIDADE MAXIMA DA MAQUINA PARA SANDES
    private final String NOMEMAQUINA = "\nLUNCHBREAK™";  //NOME DA MAQUINA
    private final String PASSWORD = new String(Base64.getDecoder().decode("cXVlcm9lbnRyYXI=")); //PASSWORD ENCRIPTADA EM BASE64
    private final DecimalFormat df = new DecimalFormat("0.00"); //UTILIZADO PARA FORMATAR OS VALORES DOUBLE COM DUAS CASAS DECIMAIS
    private double saldo;  //SALDO QUE O CLIENTE VAI INTRODUZIR NA MAQUINA
    private double receita; //TOTAL DE DINHEIRO ACUMULADO PELAS VENDAS DA MAQUINA


    //LISTAS DE PRODUTOS E HISTORICO
    private ArrayList<String> historico = new ArrayList<>();
    private ArrayList<Produto> chocolates = new ArrayList<>();
    private ArrayList<Produto> refrigerantes = new ArrayList<>();
    private ArrayList<Produto> sandes = new ArrayList<>();

    //METODO DE INICIALIZACAO DA MAQUINA E ONDE SAO CARREGADOS OS DADOS DA INSTANCIA ANTERIOR
    public void ligar(){
        System.out.println(NOMEMAQUINA + " A INICIAR...");
        carregarStock();
        do{
            menuAcesso();
        }while(true);
    }

    //MENU ONDE O UTILIZADOR IRA ESCOLHER SE QUER FAZER UMA COMPRA OU CONFIGURAR A MAQUINA ATRAVES DE AUTENTICACAO
    public void menuAcesso(){
        System.out.println(NOMEMAQUINA);
        System.out.println("Que operacao deseja fazer?");
        System.out.println("1 - Comprar");
        System.out.println("2 - Configurar");
        System.out.println("3 - Sair");
        int escolha = Ler.getInt("-> ");
        switch (escolha){
            case 1: //OPCAO DE ENTRAR COMO CLIENTE E FAZER PEDIDOS
                menuCompra();
                break;
            case 2: //OPCAO DE ENTRAR COMO COLABORADOR COM AUTENTICACAO
                loginColaborador();
                break;
            case 3:
                System.out.println("\nVOLTE SEMPRE!");
                System.out.println(NOMEMAQUINA);
                System.out.println("DESENVOLVIDO POR CLAUDIO FONTE & DIOGO SAMUEL");
                System.out.println("@CITEFORMA2024");
                System.exit(0); //FECHAR O PROGRAMA

            default:System.out.println("OPCAO INVALIDA!");
        }
    }

    //MENU ONDE O UTILIZADOR PODERA FAZER A SUA COMPRA DE PRODUTOS
    public void menuCompra(){
        System.out.println("\nMENU COMPRA");
        System.out.println("1 - Introduzir montante");
        System.out.println("2 - Voltar");
        int escolha = Ler.getInt("-> ");
        switch (escolha){
            case 1:
                addSaldo();
                do {
                    mostrarSaldo();
                    System.out.println("Que categoria deseja vizualizar? ");
                    System.out.println("1 - Chocolates");
                    System.out.println("2 - Refrigerantes");
                    System.out.println("3 - Sandes");
                    System.out.println("4 - Cancelar");
                    switch (Ler.getInt("-> ")){

                        //UTILIZADOR ESCOLHEU VIZUALIZAR A CATEGORIA DOS CHOCOLATES
                        case 1:
                            listarProdutos(chocolates);
                            comprarProduto(chocolates);
                            break;

                        //UTILIZADOR ESCOLHEU VIZUALIZAR A CATEGORIA DOS REFRIGERANTES
                        case 2:
                            listarProdutos(refrigerantes);
                            comprarProduto(refrigerantes);
                            break;

                        //UTILIZADOR ESCOLHEU VIZUALIZAR A CATEGORIA DAS SANDES
                        case 3:
                            listarProdutos(sandes);
                            comprarProduto(sandes);
                            break;

                        //SE O UTILIZADOR DECIDIR SAIR SER-LHE-A DADO O SEU DINHEIRO QUE RESTAR NA MAQUINA
                        case 4:
                            if(saldo>0) System.out.println("\nA DEVOLVER " + df.format(saldo) + "€");
                            saldo=0;
                            System.out.println("\nOBRIGADO POR UTILIZAR OS NOSSOS SERVICOS!");
                            return;
                        default:
                            System.out.println("CATEGORIA INVALIDA!");
                    }
                }while(true);

                //VAI VOLTAR AO MENU ANTERIOR
            case 2:
                return;
            default:
                System.out.println("OPCAO INVALIDA!");
        }
    }

    //MENU DE ADMINISTRACAO DA MAQUINA QUE SO PODE SER ACEDIDO POR COLABORADORES AUTENTICADOS
    public void menuColaborador(){
        int escolha;
        System.out.println("BEM VINDO!");
        do {
            System.out.println("\nMENU DE COLABORADOR");
            System.out.println("Que operacao deseja efetuar? ");
            System.out.println("1 - Adicionar produto");
            System.out.println("2 - Retirar produto");
            System.out.println("3 - Consultar valor total de vendas");
            System.out.println("4 - Consultar historico");
            System.out.println("5 - Voltar");
            escolha = Ler.getInt("-> ");
            String s;
            switch (escolha) {

                //ADICIONAR PRODUTOS A MAQUINA
                case 1:
                    s = Ler.getLine("Que produto deseja adicionar (Chocolate, Refrigerante, Sandes)? ");
                    if (s.toLowerCase().contains("choc")) {
                        addProduto(TipoProduto.CHOCOLATE);

                    } else if (s.toLowerCase().contains("refr")) {
                        addProduto(TipoProduto.REFRIGERANTE);

                    } else if (s.toLowerCase().contains("sand")) {
                        addProduto(TipoProduto.SANDES);

                    } else System.out.println("PRODUTO INCOMPATIVEL!");
                    break;

                //RETIRAR PRODUTOS DA MAQUINA
                case 2:
                    s = Ler.getLine("Que produto deseja Remover (Chocolate, Refrigerante, Sandes)? ");
                    if (s.toLowerCase().contains("choc")) {
                        listarProdutos(chocolates);
                        retirarProduto(chocolates);

                    } else if (s.toLowerCase().contains("refr")) {
                        listarProdutos(refrigerantes);
                        retirarProduto(refrigerantes);

                    } else if (s.toLowerCase().contains("sand")) {
                        listarProdutos(sandes);
                        retirarProduto(sandes);

                    } else System.out.println("PRODUTO INCOMPATIVEL!");
                    break;

                //CONSULTAR RECEITAS DA MAQUINA
                case 3:
                    System.out.println("\nATE AO MOMENTO A MAQUINA FATUROU UM TOTAL DE " + df.format(receita) + "€");
                    break;

                //CONSULTAR HISTORICO DE VENDAS DA MAQUINA
                case 4:
                    System.out.println("\nHISTORICO DE VENDAS");
                    listarHistorico();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("OPCAO INVALIDA!");
            }
            //SO VAI SAIR DO LOOP QUANDO O CLIENTE INTRODUZIR 5
        }while (escolha!=5);
    }

    //MENU DE AUTENTICACAO DO COLABORADOR POR PASSWORD
    public void loginColaborador(){
        System.out.println("\nLOGIN COLABORADOR");
        for (int i = 0; i<3 ; i++){
            System.out.println("TEM " + (3-i) + " TENTATIVAS.");
            String pw = Ler.getLine("Introduza a password de colaborador: ");
            if(pw.equals(PASSWORD)){
                System.out.println("ACESSO CONCEDIDO!\n");
                menuColaborador();
                return;
            }else System.out.println("\nPASSWORD ERRADA!");
        }
        System.out.println("ESGOTOU O NUMERO DE TENTATIVAS!");
    }

    //IMPRIME UMA LISTA COM O HISTORICO DE TODOS OS PRODUTOS QUE FORAM COMPRADOS ASSIM COMO A DATA DA TRANSACAO E PRECO
    public void listarHistorico(){

        //VERIFICA SE O HISTORICO ESTÁ VAZIO E FECHA O METODO
        if (historico.isEmpty()){
            System.out.println("HISTORICO VAZIO");
            return;
        }
        //SE O HISTORICO TIVER INFORMACAO VAI LISTAR TODAS AS OCORRENCIAS E DAR OPCAO DE APAGAR
        for (String string : historico){
            System.out.println(string);
        }

        System.out.println("\nDESEJA APAGAR O HISTORICO? ( 1 - SIM | 2 - NAO )");
        if (Ler.getInt("-> ") == 1){
            System.out.println("TEM A CERTEZA QUE DESEJA APAGAR O HISTORICO? ( ESCREVA 'SIM' PARA CONFIRMAR )");
            if (Ler.getLine("-> ").equalsIgnoreCase("SIM")){
                historico.clear();
                System.out.println("HISTORICO APAGADO COM SUCESSO");
            }else System.out.println("A CANCELAR...");
        }
    }

    //IMPRIME A VARIAVEL SALDO DA MAQUINA EM FORMATO MONETARIO
    public void mostrarSaldo(){
        System.out.println("\nSALDO: " + df.format(saldo) + "€");
    }

    //RECEBE UMA LISTA DO TIPO PRODUTO E IRA IMPRIMIR TODOS OS PRODUTOS DA MESMA
    public void listarProdutos(ArrayList<Produto> prod){
        if (prod.isEmpty()){
            System.out.println("\nSEM STOCK");
        }else {
            System.out.println("____________________");
            System.out.println(prod.getFirst().getNome().toUpperCase());
            System.out.println("____________________");
            for (int i=0; i<prod.size();i++){
                System.out.println("id: " + (i+1));
                System.out.println("Marca: " + prod.get(i).getMarca());
                System.out.println("Tipo: " + prod.get(i).getTipo());
                System.out.println("Preco: " + prod.get(i).getPreco()+"€");
                System.out.println("Prazo de validade: " + prod.get(i).getPrazoDeValidade());
                System.out.println("____________________");
            }
        }
    }

    //VAI DAR A ESCOLHER AO UTILIZADOR QUAL O PRODUTO DESEJADO
    public int selecionarProduto(){
        int escolha;
        String input = Ler.getLine("Introduza o id do produto que deseja retirar (C - PARA VOLTAR): ");

        //VERIFICA SE O UTILIZADOR PRESSIONOU A TECLA PARA VOLTAR
        if(input.equalsIgnoreCase("c")){
            System.out.println("A RETROCEDER...");
            return -1;
        }
        else{
            //VAI TENTAR CONVERTER O INPUT PARA INTEIRO E RETROCEDER SE NÂO FOR POSSIVEL
            try {
                escolha = Integer.parseInt(input);
            } catch (NumberFormatException e){
                //SE O INPUT NÂO FOR UM NUMERO OU A LETRA C
                System.out.println("VALOR INVALIDO, A RETROCEDER...");
                return -1;
            }
        }
        //DECREMENTA O NUMERO QUE FOI INTRODUZIDO PARA COINCIDIR COM O FORMATO DO INDICE (0<=)
        escolha-=1;

        if (escolha<0){
            System.out.println("PRODUTO NAO EXISTENTE");
            return -1;
        }

        return escolha;
    }

    //METODO ONDE A COMPRA E PROCESSADA
    public void comprarProduto(ArrayList<Produto> prod){
        //VERIFICA SE NAO EXISTE EM STOCK E FECHA O METODO
        if (prod.isEmpty()) return;

        //MOSTRA O SALDO COLOCADO PELO UTILIZADOR
        mostrarSaldo();

        int escolha = selecionarProduto();

        if(escolha == -1) return;

        //VERIFICA SE A ESCOLHA COINCIDE COM UM PRODUTO EXISTENTE E FECHA O METODO EM CASO CONTRARIO
        if (escolha > prod.size()) {
            System.out.println("PRODUTO NAO EXISTENTE");
            return;
        }

        //PROCESSAMENTO DA COMPRA
        do {
            if(saldo>=prod.get(escolha).getPreco()){
                //IMPRIME O PRODUTO QUE FOI COMPRADO E O TROCO DEVOLVIDO
                System.out.print("\n" + prod.get(escolha).getNome().toUpperCase() + " " + prod.get(escolha).getMarca().toUpperCase() + " COMPRADO COM SUCESSO! ");
                System.out.println("TROCO: " + df.format(prod.get(escolha).calcularTroco(saldo)) + "€");

                //ATUALIZA O QUANTO A MAQUINA JA FATUROU ATE ENTAO
                receita+=prod.get(escolha).getPreco();

                //ESCREVE A TRANSACAO NO HISTORICO
                atualizarHistorico(prod.get(escolha));

                //ATUALIZA O SALDO DA MAQUINA
                saldo=prod.get(escolha).calcularTroco(saldo);

                //REMOVE O PRODUTO COMPRADO DA LISTA DE STOCK
                prod.remove(escolha);
                guardarStock();
                return;

                //SE NAO TIVER SALDO SUFICIENTE VAI PEDIR QUE INTRODUZA MAIS OU DAR OPCAO DE CANCELAR
            }else {
                System.out.println("\nSALDO INSUFICIENTE!");
                System.out.println("FALTA " + (df.format(prod.get(escolha).preco - saldo)) + "€");
                System.out.println("DESEJA INTRODUZIR MAIS SALDO? ( 1 - SIM | 2 - CANCELAR )");
                if (Ler.getInt("->") == 1) addSaldo(); else {
                    System.out.println("COMPRA CANCELADA");
                    return;
                }
            }
        }while(true);


    }

    //METODO PARA REMOVER PRODUTOS DE UMA DADA LISTA DO TIPO PRODUTO
    public void retirarProduto(ArrayList<Produto> prod){

        //VERIFICA SE NAO EXISTE EM STOCK E FECHA O METODO
        if (prod.isEmpty()) return;

        int escolha = selecionarProduto();

        if(escolha == -1) return;

        //VERIFICA SE A ESCOLHA COINCIDE COM UM PRODUTO EXISTENTE E FECHA O METODO EM CASO CONTRARIO
        if (escolha > prod.size()) {
            System.out.println("PRODUTO NAO EXISTENTE");
            return;
        }

        if(prod.get(escolha)!= null){

            //MOSTRA O NOME DO PRODUTO QUE FOI RETIRADO DO STOCK ASSIM COMO A SUA MARCA E PRECO
            System.out.println("\n" + prod.get(escolha).getNome().toUpperCase() + " " + prod.get(escolha).getMarca().toUpperCase() + " RETIRADO COM SUCESSO!");
            prod.remove(escolha);
            guardarStock();
        }else System.out.println("PRODUTO NAO EXISTE");

    }

    public void addProduto(TipoProduto tipo){
        switch (tipo) {
            case CHOCOLATE:
                if (chocolates.size() < MAXCHOC) {
                    chocolates.add(Chocolate.newChocolate());
                    guardarStock();
                } else {
                    System.out.println("\nSTOCK DE CHOCOLATES CHEIO");
                }
                break;

            case REFRIGERANTE:
                if (refrigerantes.size() < MAXREFRI) {
                    refrigerantes.add(Refrigerante.newRefrigerante());
                    guardarStock();
                } else {
                    System.out.println("\nSTOCK DE REFRIGERANTES CHEIO");
                }
                break;

            case SANDES:
                if (sandes.size() < MAXSANDES) {
                    sandes.add(Sandes.newSandes());
                    guardarStock();
                } else {
                    System.out.println("\nSTOCK DE SANDES CHEIO");
                }
                break;

            default:
                System.out.println("TIPO DE PRODUTO INVALIDO");
                break;
        }
    }

    public void addSaldo(){
        saldo += Ler.getDouble("Introduza o montante: ");
    }

    //ESTE METODO VAI ADICIONAR UMA NOVA STRING AO HISTORICO COM A DATA E HORA DA TRANSACAO ASSIM COMO A MARCA E PRECO DO PRODUTO
    public void atualizarHistorico(Produto prod){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        historico.add(dtf.format(now) + " " + prod.getMarca() + " " + prod.getPreco() + "€");
    }

    //ESTE METODO VAI GUARDAR O OBJETO DA MAQUINA DE VENDAS ASSIM COMO TODOS OS SEUS METODOS E ATRIBUTOS EM UM FILE
    public void guardarStock(){
        try (FileOutputStream fileOut = new FileOutputStream("stock.dat");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(this);  // Escreve o objeto no ficheiro
            System.out.println("STOCK ATUALIZADO!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //ESTE METODO VAI ABRIR O FICHEIRO GUARDADO POSTERIORMENTE E ADQUIRIR OS DADOS ARMAZENADOS (LISTAS DE PRODUTOS/HISTORICO/RECEITA)
    public void carregarStock() {
        File file = new File("stock.dat");
        if(file.exists()){
            try (FileInputStream fileIn = new FileInputStream("stock.dat");
                 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

                MaquinaDeVendas maquina = (MaquinaDeVendas) objectIn.readObject();  // Read chocolates list
                chocolates = maquina.chocolates;
                refrigerantes = maquina.refrigerantes;
                sandes = maquina.sandes;
                historico = maquina.historico;
                receita = maquina.receita;
                System.out.println("STOCK CARREGADO!");

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
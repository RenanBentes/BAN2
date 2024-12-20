package main.Classes;

import PetShop.Cliente;
import java.util.Scanner;
import main.Conexao;

public class MenuCliente {
    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n----- Menu de Clientes -----");
            System.out.println("1. Adicionar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Remover Cliente");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    Cliente.adicionarCliente();
                    Conexao.fecharConexao();
                    break;
                case 2:
                    Cliente.listarClientes();
                    Conexao.fecharConexao();
                    break;
                case 3:
                    Cliente.atualizarCliente();
                    Conexao.fecharConexao();
                    break;
                case 4:
                    Cliente.removerCliente();
                    Conexao.fecharConexao();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
package main.Classes;

import PetShop.ServicoRealizado;
import java.util.Scanner;

public class MenuServicoRealizado {
    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n----- Menu de Serviços Realizados -----");
            System.out.println("1. Adicionar Serviço");
            System.out.println("2. Listar Serviços");
            System.out.println("3. Atualizar Serviço");
            System.out.println("4. Remover Serviço");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    ServicoRealizado.adicionarServico();
                    break;
                case 2:
                    ServicoRealizado.listarServicos();
                    break;
                case 3:
                    ServicoRealizado.atualizarServico();
                    break;
                case 4:
                    ServicoRealizado.removerServico();
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

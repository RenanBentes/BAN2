package main.Classes;

import PetShop.Relatorio;
import java.util.Scanner;

public class MenuRelatorio {
    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n----- Menu de Relatórios -----");
            System.out.println("1. Total Pets Por Cliente");
            System.out.println("2. Serviços por Pets");
            System.out.println("3. Receita de Serviços");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    Relatorio.totalPetsPorCliente();

                    break;
                case 2:
                    Relatorio.servicosPorPet();
                    break;
                case 3:
                    Relatorio.somaValoresPorServico();
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
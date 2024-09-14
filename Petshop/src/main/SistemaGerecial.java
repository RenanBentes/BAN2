package main;

import java.util.Scanner;

public class SistemaGerecial {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        menuPrincipal();
    }

    public static void menuPrincipal() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("----- Sistema de Gerenciamento do PetShop -----");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Pets");
            System.out.println("3. Gerenciar Raças");
            System.out.println("4. Gerenciar Serviços Realizados");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    MenuCliente.exibirMenu();
                    break;
                case 2:
                    MenuPet.exibirMenu();
                    break;
                case 3:
                    MenuRaca.exibirMenu();
                    break;
                case 4:
                    MenuServicoRealizado.exibirMenu();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}

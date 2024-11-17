package main.Classes;


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
            System.out.println("1. Gerenciar Clientes \n2. Gerenciar Pets \n3. Gerenciar Raças\n4. Gerenciar Serviços Realizados");
            System.out.println("5. Gerenciar Descrição de Serviços \n0. Sair\nEscolha uma opção:");
            opcao = scanner.nextInt();
            scanner.nextLine();

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
                case 5:
                    MenuDescricaoServico.exibirMenu();
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

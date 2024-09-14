package main;

import petshop.Pet;
import java.util.Scanner;

public class MenuPet {
    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n----- Menu de Pets -----");
            System.out.println("1. Adicionar Pet");
            System.out.println("2. Listar Pets");
            System.out.println("3. Atualizar Pet");
            System.out.println("4. Remover Pet");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    Pet.adicionarPet();
                    break;
                case 2:
                    Pet.listarPets();
                    break;
                case 3:
                    Pet.atualizarPet();
                    break;
                case 4:
                    Pet.removerPet();
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

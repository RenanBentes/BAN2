package main.Classes;

import PetShop.Pet;
import main.Conexao;

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
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    Pet.adicionarPet();
                    Conexao.fecharConexao();
                    break;
                case 2:
                    Pet.listarPets();
                    Conexao.fecharConexao();
                    break;
                case 3:
                    Pet.atualizarPet();
                    Conexao.fecharConexao();
                    break;
                case 4:
                    Pet.removerPet();
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

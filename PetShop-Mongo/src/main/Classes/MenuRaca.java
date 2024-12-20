package main.Classes;

import java.util.Scanner;
import PetShop.Raca;
import main.Conexao;

public class MenuRaca {
    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n----- Menu de Raças -----");
            System.out.println("1. Adicionar Raça");
            System.out.println("2. Listar Raças");
            System.out.println("3. Atualizar Raça");
            System.out.println("4. Remover Raça");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    Raca.adicionarRaca();
                    Conexao.fecharConexao();
                    break;
                case 2:
                    Raca.listarRacas();
                    Conexao.fecharConexao();
                    break;
                case 3:
                    Raca.atualizarRaca();
                    Conexao.fecharConexao();
                    break;
                case 4:
                    Raca.removerRaca();
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
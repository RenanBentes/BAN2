package main;

import petshop.DescricaoServico;
import java.util.Scanner;

public class MenuDescricaoServico {
    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n----- Menu de Descrições de Serviço -----");
            System.out.println("1. Adicionar Descrição de Serviço");
            System.out.println("2. Listar Descrições de Serviço");
            System.out.println("3. Atualizar Descrição de Serviço");
            System.out.println("4. Remover Descrição de Serviço");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    DescricaoServico.adicionarDescricaoServico();
                    break;
                case 2:
                    DescricaoServico.listarDescricoesServico();
                    break;
                case 3:
                    DescricaoServico.atualizarDescricaoServico();
                    break;
                case 4:
                    DescricaoServico.removerDescricaoServico();
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

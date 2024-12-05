Lucas Denoni Zanellato
Renan Bentes de Oliveira
Roger de Azevedo

Trabalho de conclusão de disciplina submetido à Universidade do Estado de Santa Catarina como parte dos requisitos para a obtenção da aprovação em Banco de Dados II.

Professora Dra. Rebeca Schroeder Freitas

1.	Apresentação de Aplicação

O sistema PetShop permite o gerenciamento de operações de um pet shop, podendo cadastrar, listar, atualizar e remover de clientes, pets, raças, serviço realizados e descrição de serviços realizados pelo pet shop e relatórios gerenciais.

2.	Instruções para Compilação e Execução da Aplicação

Detalharemos as instruções necessárias para compilar e executar a aplicação, utilizando o Intellij como IDE e MongoDB como banco de dados.

2.1 Ferramentas utilizadas

- IntelliJ IDEA - IDE para desenvolvimento em Java.
- Java Development Kit (JDK) - A aplicação foi desenvolvida usando o JDK 8 ou superior.
- MongoDB - Banco de dados NoSQL usado para armazenar as informações.

2.2 Execução

Após configurar a classe ‘Conexao’ na IDE, necessário configurar o localhost no MongoDB Compass, que por sua vez está acessível pela porta padrão 27017. A aplicação é executada através do arquivo SistemaGerencial.java.
A Database foi criada utilizando o arquivo compass-connections.json, e as coleções foram feitas usando do import dos arquivos .csv presentes do repositório (caminho: BAN2/PetShop-Mongo/src/main/Tabelas/).
Ao executá-la, o menu principal é exibido, com as seguintes opções: Gerenciar Clientes, Pets, Raças, Serviços Realizados e Descrição de Serviços. Cada uma das categorias possui as opções de adicionar, listar, editar e remover entradas. O menu também possui a opção de Relatórios.

3.	Relatórios

Relatório 1: Total de Pets por Cliente

Este relatório exibe todos os clientes e a quantidade de pets cadastrados em cada um, juntando as tabelas de Pet e Cliente.

Relatório 2: Serviços por Pets

Este relatório exibe a quantidade de serviços realizados para cada pet, juntando as tabelas Pet e ServicoRealizado.

Relatório 3: Receita de Serviços

Este relatório exibe a soma dos valores arrecados por todos os serviços que foram concluídos (Status Concluído), agrupados pela descrição de cada serviço, juntando as tabelas ServicoRealizado e DescricaoServico.

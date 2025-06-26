# PetShop-Mongo

Projeto desenvolvido por:
- Lucas Denoni Zanellato
- Renan Bentes de Oliveira
- Roger de Azevedo

Trabalho de conclusão de disciplina submetido à Universidade do Estado de Santa Catarina (UDESC) como parte dos requisitos para a aprovação em Banco de Dados II.

**Professora:** Dra. Rebeca Schroeder Freitas

---

## Sobre o Projeto

O **PetShop-Mongo** é um sistema de gerenciamento para pet shops, desenvolvido em Java, com persistência de dados utilizando o banco NoSQL MongoDB. O projeto tem como objetivo facilitar a administração de um pet shop, permitindo um controle eficiente de clientes, pets, raças, serviços realizados e descrições de serviços.

O sistema foi projetado para ser didático e modular, servindo tanto como uma solução prática quanto como uma base para estudos em bancos de dados NoSQL e aplicações Java.

### Principais Funcionalidades

- **Gerenciamento de Clientes:** Cadastro, listagem, atualização e remoção.
- **Gerenciamento de Pets:** Cadastro, listagem, atualização e remoção, com associação aos clientes.
- **Gerenciamento de Raças:** Controle das raças de pets cadastradas.
- **Serviços Realizados:** Controle dos serviços executados para cada pet.
- **Descrição de Serviços:** Cadastro dos tipos de serviços oferecidos.
- **Relatórios automatizados:**
  - Total de pets por cliente.
  - Quantidade de serviços realizados por pet.
  - Receita total arrecadada por tipo de serviço concluído.

### Diferenciais do Projeto

- Utilização de MongoDB, demonstrando integração de Java com banco de dados NoSQL.
- Estrutura modular, facilitando manutenção e expansão.
- Importação de dados por arquivos `.csv` para rápida inicialização do banco.
- Interface em menu textual de fácil navegação e uso.

---

## Instruções para Compilação e Execução

### Ferramentas Utilizadas

- **IntelliJ IDEA:** IDE recomendada para desenvolvimento Java.
- **Java Development Kit (JDK):** Versão 22 ou superior.
- **MongoDB:** Banco de dados NoSQL (utilize MongoDB Compass para facilitar a visualização e administração).

### Configuração do Ambiente

1. **Banco de Dados:**
   - Instale o MongoDB e/ou utilize o MongoDB Compass.
   - Configure o acesso padrão em `localhost:27017`.
   - Utilize o arquivo `compass-connections.json` incluído no projeto para criar a database.
   - Importe os arquivos `.csv` localizados em `PetShop-Mongo/src/main/resources` para popular as coleções iniciais.

2. **Aplicação:**
   - Abra o projeto no IntelliJ IDEA.
   - Configure a classe `Conexao` com os dados de conexão do seu MongoDB local.
   - Compile o projeto com o JDK 22+.

### Execução

- Execute a aplicação pela classe principal.
- O menu principal será exibido, permitindo o acesso a todas as funcionalidades do sistema.

---

## Relatórios Disponíveis

- **Total de Pets por Cliente:** Mostra todos os clientes com a quantidade de pets cadastrados.
- **Serviços por Pets:** Mostra a quantidade de serviços realizados para cada pet.
- **Receita de Serviços:** Apresenta a soma dos valores arrecadados por todos os serviços concluídos, agrupados por tipo de serviço.

## Licença

Projeto desenvolvido como parte de atividade acadêmica. Consulte os autores para outros usos.

# 📝 Projeto de Integração Node.js + MongoDB com Docker

Este projeto demonstra como conectar uma aplicação Node.js a um banco de dados MongoDB em container Docker.

## 📋 Arquivos do Projeto

- `docker-compose-mongo.yml` - Configuração do container MongoDB
- `Teste.js` - Aplicação Node.js de exemplo
- `package.json` - Dependências do projeto
- `package-lock.json` - Versões exatas das dependências

## 🚀 Pré-requisitos

- Docker e Docker Compose instalados
- Node.js 16.20.1 ou superior
- NPM ou Yarn

## 🐳 Configuração do MongoDB

O arquivo `docker-compose-mongo.yml` configura um container MongoDB.

**Características:**
- Usuário admin: `User` / Senha: `mongo`
- Persistência de dados via volume Docker (`mongodb_data`)
- Porta 27017 exposta
- Rede bridge personalizada para isolamento
- Reinicialização automática

## ⚙️ Configuração da Aplicação

O projeto está configurado para conectar ao MongoDB com as seguintes configurações:

- **URL de conexão**: `mongodb://User:mongo@localhost:27017`
- **Banco de dados**: `testdb`
- **Coleção**: `Teste`
- **Usuário**: `User`
- **Senha**: `mongo`

## 📦 Dependências

- **mongodb**: ^6.17.0 - Driver oficial do MongoDB para Node.js

### Instalação das Dependências

```bash
npm install
```

## 🎯 Funcionalidades

O script `Teste.js` demonstra as seguintes operações:

1. **Conexão** com o banco MongoDB
2. **Criação** de uma nova coleção
3. **Inserção** de um documento de teste
4. **Consulta** para verificar os dados inseridos
5. **Fechamento** da conexão

### Exemplo de Documento Inserido

```javascript
{
  nome: "Documento de Teste",
  valor: 123.45,
  dataCriacao: new Date(),
  ativo: true,
  tags: ["teste", "javascript", "mongodb"]
}
```

## 🏃‍♂️ Como Executar

### Método: Usando Docker 

1. **Instale as dependências:**
```bash
npm install
```

2. **Inicie o MongoDB:**
```bash
docker-compose -f docker-compose-mongo.yml up -d
```

3. **Execute o script Node.js:**
```bash
node Teste.js
```

## 🔧 Personalização

Para adaptar o projeto às suas necessidades, modifique as configurações no início do arquivo `Teste.js`:

```javascript
const config = {
  url: 'mongodb://seu_usuario:sua_senha@localhost:27017',
  dbName: 'seu_banco_de_dados',
  collectionName: 'sua_colecao'
};
```

## 📝 Estrutura do Projeto

```
├── package.json               # Dependências e configurações do npm
├── package-lock.json          # Lock file das dependências
├── docker-compose-mongo.yml   # Configuração Docker para MongoDB
├── Teste.js                   # Script principal de demonstração
└── README.md                  # Este arquivo
```

## 🛠️ Tecnologias Utilizadas

- **Node.js** - Runtime JavaScript
- **MongoDB** - Banco de dados NoSQL
- **MongoDB Driver** - Driver oficial para Node.js
- **Docker & Docker Compose** - Containerização e orquestração

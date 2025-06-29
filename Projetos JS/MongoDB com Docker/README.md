# 📝 Integração Node.js + MongoDB com Docker

Este projeto demonstra como conectar uma aplicação Node.js a um banco de dados MongoDB executando em um container Docker, com exemplos práticos de criação de coleção, inserção, consulta e atualização de documentos.

## 📁 Arquivos do Projeto

- `docker-compose-mongo.yml` — Configuração do container MongoDB via Docker Compose.
- `Teste.js` — Script Node.js de exemplo com operações CRUD no MongoDB.
- `package.json` / `package-lock.json` — Gerenciamento e bloqueio das dependências do projeto.
- `README.md` — Documentação do projeto.

## 🚀 Pré-requisitos

- Docker e Docker Compose instalados na máquina.
- Node.js 16.20.1 ou superior.
- NPM ou Yarn.

## 🐳 Subindo o MongoDB com Docker

O arquivo `docker-compose-mongo.yml` configura um container MongoDB com as seguintes características:

- **Usuário root:** `User`  
- **Senha root:** `mongo`
- **Persistência:** Volume Docker `mongodb_data`
- **Porta exposta:** 27017
- **Reinício automático:** sempre que necessário

Para iniciar o MongoDB via Docker Compose:

```bash
docker-compose -f docker-compose-mongo.yml up -d
```

## ⚙️ Configuração da Aplicação Node.js

O script `Teste.js` está configurado para conectar ao MongoDB com os parâmetros:

- **URL de conexão:** `mongodb://User:mongo@localhost:27017`
- **Banco de dados:** `testdb`
- **Coleção:** `Teste`

> Para personalizar, edite o objeto `config` no início do arquivo `Teste.js`:

```javascript
const config = {
  url: 'mongodb://User:mongo@localhost:27017',
  dbName: 'testdb',
  collectionName: 'Teste'
};
```

## 📦 Instalação das Dependências

Instale as dependências Node.js do projeto:

```bash
npm install
```

## 🎯 Funcionalidades Demonstradas

O script `Teste.js` executa automaticamente:

1. **Conexão** ao MongoDB.
2. **Criação** da coleção, se não existir.
3. **Inserção** de um documento de teste.
4. **Atualização** do documento inserido.
5. **Consulta** de todos os documentos da coleção.
6. **Consulta filtrada** para documentos com campo `ativo: true`.
7. **Fechamento** seguro da conexão.

### Exemplo de documento inserido

```javascript
{
  nome: "Documento de Teste",
  valor: 123.45,
  dataCriacao: <data atual>,
  ativo: true,
  tags: ["teste", "javascript", "mongodb"],
  versao: "1.0"
}
```

Após atualização, o campo `versao` passa a ser `"1.1"` e um campo `dataAtualizacao` é adicionado.

## 🏃‍♂️ Como Executar o Projeto

1. Instale as dependências:

   ```bash
   npm install
   ```

2. Inicie o MongoDB:

   ```bash
   docker-compose -f docker-compose-mongo.yml up -d
   ```

3. Execute o script Node.js:

   ```bash
   node Teste.js
   ```

## 🛠️ Tecnologias Utilizadas

- **Node.js** — Runtime JavaScript
- **MongoDB** — Banco de dados NoSQL
- **mongodb** — Driver oficial do MongoDB para Node.js
- **Docker & Docker Compose** — Containerização e orquestração

---

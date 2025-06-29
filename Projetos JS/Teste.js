const { MongoClient } = require('mongodb');

// Configurações de conexão (do seu docker-compose)
const config = {
  url: 'mongodb://User:mongo@localhost:27017',
  dbName: 'testdb', // Banco de dados padrão ou pode criar um novo
  collectionName: 'Teste'
};

async function main() {
  // Cria o cliente MongoDB
  const client = new MongoClient(config.url);
  
  try {
    // Conecta ao servidor MongoDB
    console.log('Conectando ao MongoDB...');
    await client.connect();
    console.log('Conexão estabelecida com sucesso!');

    // Acessa o banco de dados
    const db = client.db(config.dbName);

    // Cria a coleção (equivalente a "tabela" no MongoDB)
    console.log(`Criando coleção '${config.collectionName}'...`);
    await db.createCollection(config.collectionName);
    console.log(`Coleção '${config.collectionName}' criada com sucesso!`);

    // Insere um documento de teste
    const testDoc = {
      nome: "Documento de Teste",
      valor: 123.45,
      dataCriacao: new Date(),
      ativo: true,
      tags: ["teste", "javascript", "mongodb"]
    };

    const result = await db.collection(config.collectionName).insertOne(testDoc);
    console.log(`Documento inserido com ID: ${result.insertedId}`);

    // Consulta para verificar
    const docs = await db.collection(config.collectionName).find().toArray();
    console.log('\nDocumentos na coleção:');
    console.log(docs);

  } catch (err) {
    console.error('Erro:', err);
  } finally {
    // Fecha a conexão
    await client.close();
    console.log('\nConexão encerrada.');
  }
}

// Executa o script
main().catch(console.error);
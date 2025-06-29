const { MongoClient } = require('mongodb');

// Configurações de conexão (do seu docker-compose)
const config = {
  url: 'mongodb://User:mongo@localhost:27017',
  dbName: 'testdb', // Banco de dados padrão ou pode criar um novo
  collectionName: 'Teste'
};

async function connectToMongoDB() {
  const client = new MongoClient(config.url);
  
  try {
    console.log('Conectando ao MongoDB...');
    await client.connect();
    
    // Testa a conexão fazendo ping
    await client.db("admin").command({ ping: 1 });
    console.log('Conexão estabelecida com sucesso!');
    
    return client;
  } catch (error) {
    console.error('Erro ao conectar:', error);
    throw error;
  }
}

async function createCollectionIfNotExists(db, collectionName) {
  try {
    // Verifica se a coleção já existe
    const collections = await db.listCollections({ name: collectionName }).toArray();
    
    if (collections.length === 0) {
      console.log(`Criando coleção '${collectionName}'...`);
      await db.createCollection(collectionName);
      console.log(`Coleção '${collectionName}' criada com sucesso!`);
    } else {
      console.log(`Coleção '${collectionName}' já existe.`);
    }
  } catch (error) {
    console.error('Erro ao criar/verificar coleção:', error);
    throw error;
  }
}

async function insertTestDocument(collection) {
  const testDoc = {
    nome: "Documento de Teste",
    valor: 123.45,
    dataCriacao: new Date(),
    ativo: true,
    tags: ["teste", "javascript", "mongodb"],
    versao: "1.0"
  };

  try {
    const result = await collection.insertOne(testDoc);
    console.log(`Documento inserido com ID: ${result.insertedId}`);
    return result.insertedId;
  } catch (error) {
    console.error('Erro ao inserir documento:', error);
    throw error;
  }
}

async function queryDocuments(collection) {
  try {
    const docs = await collection.find().toArray();
    console.log('\nDocumentos na coleção:');
    docs.forEach((doc, index) => {
      console.log(`Documento ${index + 1}:`, JSON.stringify(doc, null, 2));
    });
    return docs;
  } catch (error) {
    console.error('Erro ao consultar documentos:', error);
    throw error;
  }
}

async function updateDocument(collection) {
  try {
    const updateResult = await collection.updateOne(
      { nome: "Documento de Teste" },
      { 
        $set: { 
          dataAtualizacao: new Date(),
          versao: "1.1"
        }
      }
    );
    
    if (updateResult.modifiedCount > 0) {
      console.log('\nDocumento atualizado com sucesso!');
    } else {
      console.log('\nNenhum documento foi atualizado.');
    }
    
    return updateResult;
  } catch (error) {
    console.error('Erro ao atualizar documento:', error);
    throw error;
  }
}

async function main() {
  let client;
  
  try {
    // Conecta ao MongoDB
    client = await connectToMongoDB();
    
    // Acessa o banco de dados
    const db = client.db(config.dbName);
    
    // Cria a coleção se não existir
    await createCollectionIfNotExists(db, config.collectionName);
    
    // Obtém referência da coleção
    const collection = db.collection(config.collectionName);
    
    // Insere um documento de teste
    await insertTestDocument(collection);
    
    // Atualiza o documento
    await updateDocument(collection);
    
    // Consulta para verificar
    await queryDocuments(collection);
    
    // Exemplo de consulta com filtro
    console.log('\n--- Consulta com filtro ---');
    const filteredDocs = await collection.find({ ativo: true }).toArray();
    console.log(`Encontrados ${filteredDocs.length} documentos ativos.`);
    
  } catch (err) {
    console.error('Erro na execução principal:', err);
    process.exit(1);
  } finally {
    // Fecha a conexão
    if (client) {
      await client.close();
      console.log('\nConexão encerrada.');
    }
  }
}

// Executa o script
if (require.main === module) {
  main().catch(console.error);
}

module.exports = {
  connectToMongoDB,
  createCollectionIfNotExists,
  insertTestDocument,
  queryDocuments,
  updateDocument,
  config
};

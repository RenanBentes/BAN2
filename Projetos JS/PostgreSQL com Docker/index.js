// Primeiro, você precisa instalar a biblioteca 'pg'.
// Abra seu terminal e execute: npm install pg
const { Pool } = require('pg');

// Configurações de conexão com o banco de dados.
const connectionString = 'postgres://User:Password@localhost:5432/database';
const pool = new Pool({
  connectionString: connectionString,
});

async function main() {
  let client;
  try {
    // 1. Obter uma conexão do pool
    console.log('Tentando conectar ao banco de dados PostgreSQL...');
    client = await pool.connect();
    console.log('Conexão estabelecida com sucesso!');

    // 2. Criar uma tabela (exemplo) se ela não existir
    console.log('Criando a tabela "usuarios" (se não existir)...');
    await client.query(`
      CREATE TABLE IF NOT EXISTS usuarios (
        id SERIAL PRIMARY KEY,
        nome VARCHAR(100) NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL
      );
    `);
    console.log('Tabela "usuarios" pronta.');

    // 3. Inserir alguns dados (exemplo)
    const nomeUsuario = 'Ana Silva';
    const emailUsuario = 'ana.silva@example.com';
    console.log(`Inserindo o usuário: ${nomeUsuario}`);
    
    // Usamos uma query parametrizada para evitar SQL Injection
    await client.query(
        'INSERT INTO usuarios (nome, email) VALUES ($1, $2) ON CONFLICT (email) DO NOTHING;', 
        [nomeUsuario, emailUsuario]
    );
    console.log('Usuário inserido com sucesso (ou já existia).');

    // 4. Consultar os dados da tabela
    console.log('Consultando todos os usuários...');
    const res = await client.query('SELECT * FROM usuarios ORDER BY id');
    console.log('Usuários encontrados:');
    console.table(res.rows);

  } catch (error) {
    // Tratamento de erros
    console.error('Ocorreu um erro ao interagir com o banco de dados:', error);
  } finally {
    // 5. Liberar a conexão de volta para o pool
    if (client) {
      client.release();
      console.log('Conexão liberada.');
    }
    // 6. Encerrar o pool de conexões
    await pool.end();
    console.log('Pool de conexões encerrado.');
  }
}

main();

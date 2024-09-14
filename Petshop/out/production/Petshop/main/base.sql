-- Tabela Raça
CREATE TABLE Raca (
                      idPetRaca SERIAL PRIMARY KEY,
                      descricao VARCHAR(100) NOT NULL
);
-- Tabela Cliente
CREATE TABLE Cliente (
                         idCliente SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         cpf VARCHAR(14) UNIQUE NOT NULL,
                         telefone VARCHAR(15),
                         email VARCHAR(100),
                         dataCadastro DATE DEFAULT CURRENT_DATE
);
-- Tabela Pet
CREATE TABLE Pet (
                     idPet SERIAL PRIMARY KEY,
                     nome VARCHAR(100) NOT NULL,
                     idade INT,
                     dataNascimento DATE,
                     idPetRaca INT,
                     idCliente INT,
                     dataCadastro DATE DEFAULT CURRENT_DATE,
                     FOREIGN KEY (idPetRaca) REFERENCES Raca(idPetRaca),
                     FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)
);



-- Tabela DescricaoServico
CREATE TABLE DescricaoServico (
                                  idDescricaoServico SERIAL PRIMARY KEY,
                                  ServicoDescricao VARCHAR(255) NOT NULL,
                                  valor DECIMAL(10, 2) NOT NULL
);

-- Tabela ServicoRealizado
CREATE TABLE ServicoRealizado (
                                  idServico SERIAL PRIMARY KEY,
                                  data DATE NOT NULL,
                                  idDescricaoServico INT,
                                  idCliente INT,
                                  idPet INT,
                                  status VARCHAR(20) DEFAULT 'Agendado',
                                  FOREIGN KEY (idDescricaoServico) REFERENCES DescricaoServico(idDescricaoServico),
                                  FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente),
                                  FOREIGN KEY (idPet) REFERENCES Pet(idPet)
);

-- Tabela PetDono para controlar múltiplos donos de um mesmo Pet
CREATE TABLE PetDono (
                         idPet INT,
                         idCliente INT,
                         dataInicio DATE DEFAULT CURRENT_DATE,
                         dataFim DATE,
                         PRIMARY KEY (idPet, idCliente),
                         FOREIGN KEY (idPet) REFERENCES Pet(idPet),
                         FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)
);

-- Tabela intermediária ClienteServico (muitos-para-muitos entre clientes e serviços)
CREATE TABLE ClienteServico (
                                idCliente INT,
                                idServico INT,
                                PRIMARY KEY (idCliente, idServico),
                                FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente),
                                FOREIGN KEY (idServico) REFERENCES ServicoRealizado(idServico)
);

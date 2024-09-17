-- Criação da tabela Cliente
CREATE TABLE Cliente (
    idCliente INT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    telefone VARCHAR(15)[], -- Multivalorado: Array de telefones
    email VARCHAR(50)[]
);

-- Criação da tabela DescricaoServico
CREATE TABLE DescricaoServico (
    idDescricaoServico INT PRIMARY KEY,
    servicoDescricao VARCHAR(50) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL
);

-- Criação da tabela Pet
CREATE TABLE Pet (
    idPet INT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    dataNascimento DATE
);

-- Criação da tabela PetDono (Relacionamento entre Pet e Cliente)
CREATE TABLE PetDono (
    idCliente INT,
    idPet INT,w
    PRIMARY KEY (idCliente, idPet),
    FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente),
    FOREIGN KEY (idPet) REFERENCES Pet(idPet)
);

-- Criação da tabela Raca
CREATE TABLE Raca (
    idPetRaca INT PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL
);

-- Criação da tabela ServicoRealizado
CREATE TABLE ServicoRealizado (
    idServico INT PRIMARY KEY,
    idDescricaoServico INT NOT NULL,
    idPet INT NOT NULL,
    data DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (idDescricaoServico) REFERENCES DescricaoServico(idDescricaoServico),
    FOREIGN KEY (idPet) REFERENCES Pet(idPet)
);

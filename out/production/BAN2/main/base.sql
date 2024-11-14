
CREATE TABLE Cliente (
    idCliente INT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    telefone VARCHAR(15)[],
    email VARCHAR(50)[]
);

CREATE TABLE Raca (
    idPetRaca INT PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL
);

CREATE TABLE DescricaoServico (
    idDescricaoServico INT PRIMARY KEY,
    servicoDescricao VARCHAR(50) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    idPetRaca INT,
    FOREIGN KEY (idPetRaca) REFERENCES Raca(idPetRaca)
);

CREATE TABLE Pet (
    idPet INT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    idPetRaca INT,
    dataNascimento DATE,
    FOREIGN KEY (idPetRaca) REFERENCES Raca(idPetRaca)
);

CREATE TABLE PetDono (
    idCliente INT,
    idPet INT,
    PRIMARY KEY (idCliente, idPet),
    FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente),
    FOREIGN KEY (idPet) REFERENCES Pet(idPet)
);

CREATE TABLE ServicoRealizado (
    idServico INT PRIMARY KEY,
    idDescricaoServico INT NOT NULL,
    idPet INT NOT NULL,
    data DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (idDescricaoServico) REFERENCES DescricaoServico(idDescricaoServico),
    FOREIGN KEY (idPet) REFERENCES Pet(idPet)
);

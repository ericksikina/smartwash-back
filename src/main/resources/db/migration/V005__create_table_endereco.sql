CREATE TABLE endereco(
    id UUID PRIMARY KEY NOT NULL,
    rua VARCHAR(50) NOT NULL,
    bairro VARCHAR(50) NOT NULL,
    numero VARCHAR(4) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    complemento VARCHAR(80)
);
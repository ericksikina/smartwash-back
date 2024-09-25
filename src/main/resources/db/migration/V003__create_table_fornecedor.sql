CREATE TABLE fornecedor(
    id UUID PRIMARY KEY NOT NULL,
    descricao VARCHAR(80) NOT NULL,
    telefone VARCHAR(16) NOT NULL,
    cnpj VARCHAR(18) NOT NULL,
    status VARCHAR(1) NOT NULL
);
CREATE TABLE servico(
    id UUID PRIMARY KEY NOT NULL,
    descricao VARCHAR(50) NOT NULL,
    preco DECIMAL NOT NULL,
    status VARCHAR(1) NOT NULL
);
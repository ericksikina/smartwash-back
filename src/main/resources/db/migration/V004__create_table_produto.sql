CREATE TABLE produto(
    id UUID PRIMARY KEY NOT NULL,
    descricao VARCHAR(70) NOT NULL,
    quantidade_estoque INTEGER NOT NULL,
    estoque_minimo INTEGER NOT NULL,
    status VARCHAR(1) NOT NULL
);
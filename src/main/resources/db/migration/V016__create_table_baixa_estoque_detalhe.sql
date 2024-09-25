CREATE TABLE baixa_estoque_detalhe(
    id UUID PRIMARY KEY,
    id_baixa_estoque UUID NOT NULL,
    id_produto UUID NOT NULL,
    quantidade INTEGER NOT NULL,

    FOREIGN KEY(id_baixa_estoque) REFERENCES baixa_estoque(id),
    FOREIGN KEY(id_produto) REFERENCES produto(id)
);
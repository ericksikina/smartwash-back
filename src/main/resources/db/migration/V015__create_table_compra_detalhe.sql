CREATE TABLE compra_detalhe(
    id UUID PRIMARY KEY,
    id_produto UUID NOT NULL,
    id_compra UUID NOT NULL,
    quantidade INTEGER NOT NULL,
    preco_unitario DECIMAL NOT NULL,

    FOREIGN KEY(id_produto) REFERENCES produto(id),
    FOREIGN KEY(id_compra) REFERENCES compra(id)
);
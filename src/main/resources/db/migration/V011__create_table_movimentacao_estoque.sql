CREATE TABLE movimentacao_estoque(
    id UUID PRIMARY KEY NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    tipo_movimentacao VARCHAR(2) NOT NULL,
    id_compra UUID,
    id_baixa_estoque UUID,

    FOREIGN KEY(id_compra) REFERENCES compra(id),
    FOREIGN KEY(id_baixa_estoque) REFERENCES baixa_estoque(id)
);
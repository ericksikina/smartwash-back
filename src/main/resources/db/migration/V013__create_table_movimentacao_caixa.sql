CREATE TABLE movimentacao_caixa(
    id UUID PRIMARY KEY NOT NULL,
    tipo_movimentacao VARCHAR(1) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    id_pedido UUID,
    id_compra UUID,

    FOREIGN KEY(id_pedido) REFERENCES pedido(id),
    FOREIGN KEY(id_compra) REFERENCES compra(id)
);
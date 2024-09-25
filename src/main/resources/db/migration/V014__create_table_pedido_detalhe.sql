CREATE TABLE pedido_detalhe(
    id UUID PRIMARY KEY,
    id_servico UUID NOT NULL,
    id_pedido UUID NOT NULL,
    quantidade INTEGER NOT NULL,

    FOREIGN KEY(id_servico) REFERENCES servico(id),
    FOREIGN KEY(id_pedido) REFERENCES pedido(id)
);
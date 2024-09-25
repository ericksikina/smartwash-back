CREATE TABLE compra(
    id UUID PRIMARY KEY NOT NULL,
    valor_total DECIMAL NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    id_fornecedor UUID NOT NULL,
    id_funcionario UUID NOT NULL,

    FOREIGN KEY(id_fornecedor) REFERENCES fornecedor(id),
    FOREIGN KEY(id_funcionario) REFERENCES funcionario(id)
);
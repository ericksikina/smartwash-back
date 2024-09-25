CREATE TABLE pedido(
    id UUID PRIMARY KEY NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    valor_total DECIMAL NOT NULL,
    status VARCHAR(1) NOT NULL,
    id_funcionario UUID NOT NULL,
    id_cliente UUID NOT NULL,
    id_pagamento UUID,

    FOREIGN KEY(id_funcionario) REFERENCES funcionario(id),
    FOREIGN KEY(id_cliente) REFERENCES cliente(id),
    FOREIGN KEY(id_pagamento) REFERENCES pagamento(id)
);
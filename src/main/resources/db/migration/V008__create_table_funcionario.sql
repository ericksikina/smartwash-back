CREATE TABLE funcionario(
    id UUID PRIMARY KEY NOT NULL,
    nome VARCHAR(70) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    celular VARCHAR(14) NOT NULL,
    salario DECIMAL NOT NULL,
    data_contratacao DATE NOT NULL,
    status VARCHAR(1) NOT NULL,
    id_endereco UUID NOT NULL,
    id_auth UUID NOT NULL,

    FOREIGN KEY(id_endereco) REFERENCES endereco(id),
    FOREIGN KEY(id_auth) REFERENCES auth(id)
);
CREATE TABLE cliente(
    id UUID PRIMARY KEY NOT NULL,
    nome VARCHAR(70) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    celular VARCHAR(14) NOT NULL,
    email VARCHAR(40) NOT NULL,
    deseja_ser_notificado VARCHAR(1) NOT NULL,
    status VARCHAR(1) NOT NULL,
    id_endereco UUID NOT NULL,

    FOREIGN KEY(id_endereco) REFERENCES endereco(id)
);
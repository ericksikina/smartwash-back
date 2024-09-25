CREATE TABLE baixa_estoque(
  id UUID PRIMARY KEY,
  data_hora TIMESTAMP NOT NULL,
  id_funcionario UUID NOT NULL,

  FOREIGN KEY(id_funcionario) REFERENCES funcionario(id)
);
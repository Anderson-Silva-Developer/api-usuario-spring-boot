CREATE TABLE usuario
(
    id    uuid         NOT NULL DEFAULT gen_random_uuid(),
    nome  varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    senha varchar(100) NOT NULL,
    role varchar(100) NOT NULL,
    CONSTRAINT usuario_pk PRIMARY KEY (id)
);


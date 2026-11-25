CREATE TABLE registros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    acceso VARCHAR(10) NOT NULL,
    clasificacion VARCHAR(30) NOT NULL,
    rango_etario VARCHAR(30) NOT NULL,
    tipo_compania VARCHAR(30) NOT NULL,
    compras_otra_tienda BOOLEAN NOT NULL DEFAULT FALSE,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    observador VARCHAR(150) NOT NULL
);
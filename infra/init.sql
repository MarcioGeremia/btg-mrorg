USE `btgdb`;

CREATE TABLE IF NOT EXISTS clientes (codigoCliente INT PRIMARY KEY);

CREATE TABLE IF NOT EXISTS pedidos (
  codigoPedido INT PRIMARY KEY,
  codigoCliente INT NOT NULL,
  FOREIGN KEY (codigoCliente) REFERENCES clientes(codigoCliente),
  INDEX idx_codigo_pedido (codigoPedido)
);

CREATE TABLE IF NOT EXISTS itensPedido (
  codigoItem INT AUTO_INCREMENT PRIMARY KEY,
  codigoPedido INT NOT NULL,
  produto VARCHAR(100) NOT NULL,
  quantidade INT NOT NULL,
  preco DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (codigoPedido) REFERENCES pedidos(codigoPedido)
);
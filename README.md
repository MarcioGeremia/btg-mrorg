# Guia Rápido da Aplicação de Pedidos

Este documento fornece as instruções necessárias para configurar e utilizar a aplicação de processamento de pedidos.

## 📋 Pré-requisitos

Antes de começar, certifique-se de que você tem as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:

-   [Docker](https://docs.docker.com/get-docker/)
-   [Docker Compose](https://docs.docker.com/compose/install/)

## 🚀 Rodando a Aplicação

Siga os passos abaixo para iniciar a infraestrutura necessária para a aplicação.

1.  **Clone o repositório** (se aplicável):
    ```bash
    git clone https://github.com/MarcioGeremia/btg-mrorg.git
    cd btg-mrorg
    ```

2.  **Inicie os contêineres:**
    Navegue até a pasta `infra` e execute o comando abaixo para iniciar o RabbitMQ e outros serviços necessários.
    ```bash
    cd infra
    docker-compose up -d
    ```
    Isso irá baixar as imagens e iniciar os contêineres em segundo plano (`-d`).

## ⚙️ Como Usar

A aplicação funciona recebendo pedidos através de uma fila no RabbitMQ. Após o processamento, os dados podem ser consultados via API.

### 1. Inserindo um Pedido na Fila

Para simular a criação de um novo pedido, você deve publicá-lo manually na fila do RabbitMQ.

1.  Acesse a interface de gerenciamento do RabbitMQ no seu navegador:
    [**http://localhost:15672/**](http://localhost:15672/)

2.  Faça login com as credenciais padrão:
    -   **Usuário:** `admin`
    -   **Senha:** `admin`

3.  No menu superior, clique na aba **"Queues"** (Filas).

4.  Na lista de filas, encontre e clique em **`lista_pedidos`**.

5.  Dentro da página da fila, localize a seção **"Publish message"**.

6.  No campo **"Payload"**, insira o JSON do pedido que você deseja enviar.

    **Exemplo de JSON:**
    ```json
    {
      "codigoPedido": 1001,
      "codigoCliente": 1,
      "itens": [
        {
          "produto": "lápis",
          "quantidade": 100,
          "preco": 1.10
        },
        {
          "produto": "caderno",
          "quantidade": 10,
          "preco": 1.00
        }
      ]
    }
    ```

7.  Clique no botão **"Publish message"** para enviar o pedido para a fila.

### 2. Consultando os Endpoints da API

Uma vez que o pedido tenha sido consumido e processado, você pode usar os seguintes endpoints para consultar as informações.

#### A. Consultar o Valor Total por Pedido

Retorna o valor total de um pedido específico com base no seu código.

```bash
curl --request GET \
  --url http://localhost:8081/api/pedidos/{codigoPedido}/valor-total
```

#### B. Listar Pedidos por Cliente

Retorna uma lista de todos os pedidos associados a um determinado codigoCliente.

```Bash
curl --request GET \
  --url http://localhost:8081/api/pedidos/cliente/{codigoCliente}
```

#### C. Consultar Quantidade de Pedidos por Cliente

Retorna o número total de pedidos realizados por um cliente específico.

```bash
curl --request GET \
  --url http://localhost:8081/api/pedidos/cliente/{codigoCliente}/quantidade
```

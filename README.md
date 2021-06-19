
## REST API com Spring

![img](/src/main/resources/static/images/store2.gif)
    
A aplicação permite o gerenciamento de produtos do estoque de uma loja. 

Algumas caracteristicas:
* A API usa o Spring Security com JWT para realizar autorização e autenticação.
* Somente usuarios registrados por um ADMIN podem utilizar a aplicação.
* Após a autorização os usuários podem adicionar produtos ao seu carrinho de compras. 
* A API possui documentação no Swagger.


### Pré-requisitos

A aplicação necessita do SqlServer com um banco de dados criado chamado Shop.

### Instalação

1. Baixe o repositório.
   ```sh
   git clone https://github.com/MarcusMiguel/SpringAPI.git
   ```
2.  A configuração da conecção com o banco precisa ser feita no arquivo application.properties. Exemplo:
	  ```sh
	  spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=Shop
	  spring.datasource.username=sa
	  spring.datasource.password=SqlServer2017!
	  ```
	 
## Uso

Com a aplicação em execução a documentação Swagger pode ser acessada em: 
http://localhost:8080/swagger-ui.html

Clonar a aplicação no GIT

Utilizar o PostgresSql

Executar o script "Create_Database.sql" no PostgresSql no que se encontra no resources/db

No arquivo application.properties o username e o password estão:

spring.datasource.username=postgres
spring.datasource.password=postgres

Caso a instancia no postgress tenha sido criado com usuario ou senha diferentes, favor alterar para o funcionamento da aplicação.

Para rodar o projeto:

* Por linha de comando: Na pasta base do projeto, utilizar o comando ./mvnw spring-boot:run no Windows,
    ou mvn spring-boot:run no linux para rodar a aplicação

* Na IDE: Executar direto pela CampanhaApplication.java

Assim que a aplicação é iniciada o hibernate recria as tabelas, e tem um loader para a criação de 5 times para testes.

Com a aplicação iniciada, é possivel acessar a documentação do Swagger no endereço http://localhost:8080/api/swagger-ui.html

----------------------------------------------------------------------------------------------------------------------------------------

Ponderações sobre os exercicios

Para a campanha foram feitos todos os endpoints descritos, e o "/campanhas-vigencia-prorrogada", para o cliente saber os programas
que tiveram suas datas postergadas. E os dados de retorno tambem retornam os dias prorrogados.

No cliente conforme solicitado, foi criado apenas um endpoint, no qual inclui e associa as campanhas existentes,
e após incluido se disparado com um email já existente, associa as campanhas novas.
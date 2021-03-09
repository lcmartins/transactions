# Como usar a api

### Documentação de referência

#### regras de negócio:
    * o numero da conta é único
    * um cliente pode ter mais de uma conta
    * uma transferência pode ser feita entre duas contas independente do dono
    * uma transferência sempre gera uma transação mesmo em caso de 
    falha na trasnsferência (requisitos)
    * máximo de 1000.00 por trasnferência
    * a conta de origem precisa ter saldo suficiente para fazer a trasnferência
    
#### tecnologias:
    * java 11
    * kotlin
    * springboot
    * swagger
    * h2
    * jpa
    * mockk
    * junit
    
#### como usar:
* existe uma collection na raiz do projeto que pode ser utilizada para 
fazer os requests que foi criada via insomnia
  * collection: Insomnia_2021-03-09.json
    
* docker
    * estando na raiz do projeto
    * rodar:
        * docker-compose build && docker-compose up -d
    * acessar:
        * após o passo rodar terminar
        * abrir http://localhost:9091/swagger-ui/ para ver a documentação da api
        * pelo swagger ui também pode ser gerada a collection com todos os endpoints existentes
    * banco de dados:
        * foi utilizado o H2 como banco de dados in-memory
            * para acessar se console, após subir a aplicação acessar:
            * http://localhost:9091/h2-console/
                * JDBC URL: jdbc:h2:mem:transactions
                * username: sa
                * password: h2password
* local
    * estando na raiz do projeto
    * rodar:
        * ./gradlew bootRun
    * acessar:
        * após o passo rodar terminar
        * abrir http://localhost:8080/swagger-ui/ para ver a documentação da api
        * pelo swagger ui também pode ser gerada a collection com todos os endpoints existentes
    * banco de dados:
        * foi utilizado o H2 como banco de dados in-memory
            * para acessar se console, após subir a aplicação acessar:
            * http://localhost:8080/h2-console/
                * JDBC URL: jdbc:h2:mem:transactions
                * username: sa
                * password: h2password
    
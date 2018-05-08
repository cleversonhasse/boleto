<<<<<<< HEAD
#Boleto

O objetivo é construir uma API REST para geração de boletos que será consumido por um módulo de um sistema de gestão financeira de microempresas.

## Pré-Requisitos
* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3](https://maven.apache.org)
* [Banco de dados H2](http://www.h2database.com/html/main.html)

## Tecnologias, Ferramentas, Bibliotecas e Frameworks Utilizados

* Spring Boot (última versão): Framework usada no processo de configuração e publicação de nossa aplicação. Util na requisições HTTP, na forma como se comunica com o banco de dados, podendo dar segurança ao sistema e na realização de testes. Foi utilizado alguns componentes, entre eles o Web, DevTools, JDBC, Test e JPA.
* H2 Database (última versão): Banco de Dados escrito em Java que roda na memória de forma rápida e útil para testes em ambientes locais.
* Flyway (última versão): Controle de versão do banco de dados, como a criação de tabela e inserção de registros no banco de dados, executando ao subir o sistema.
automaticamente (extremamente valioso em ambientes em que há um CI/DevOps bem estruturado).
* Mockito (versão 1.10.19): Framework que possibilita a criação de objetos usados nos testes unitários. Ele interage com o banco de dados e nos fornece dados através do comportamento do sistema, podendo simular uma falha ou um item não encontrado.
* JavaDoc: Documentação do código feito por comentários nas classes. 
no código-fonte.
* Apache Commons Lang 3 (última versão): Commons Lang estende a API java.lang padrão com métodos de manipulação de string, métodos numéricos básicos, reflexo de objetos, criação e serialização e propriedades de System.
* Apache Commons Collections 4 (4.0): Biblioteca util na manipulação de coleções, listas e mapas.

## Requisitos Funcionais do Sistema

* Criar boleto
* Listar boletos
* Ver detalhes
* Pagar um boleto
* Cancelar um boleto

## Instalação

Faça o clone deste projeto com git clone https://github.com/cleversonhasse/boleto.git
## Executando a Aplicação
Existem várias maneiras de executar um aplicativo Spring Boot em sua máquina local. Uma maneira é executar o método `main` na classe `com.contaazul.boleto.BoletoApplication` do seu IDE.

Alternativamente, você pode usar o [plugin Spring Boot Maven](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) da seguinte forma:

```shell
mvn spring-boot:run
```

## Populando o Banco de Dados

Ao executar a própria aplicação vai inicializar automaticamente o banco de dados H2 que é um banco em memória escrito em Java. 
Através do Flyway, outra tecnologia incorporada ao sistema, ele já vai criar a tabela e popular os registros ao executar a aplicação automaticamente. 
Caso seja dado manutenção ao código futuramente e queira adicionar mais tabelas e registros, o Flyway executa automaticamente também ao subir a aplicação.

## Navegando pela API

Não é um pré-requisito, mas foi utilizado a ferramenta Postman para poder fazer as requisições e assim visualizar os retornos de uma forma melhor, essa ferramenta embora não tenha sido muito explorada nesse projeto também auxilia na documentação e nos testes de requisições, podendo organizar seus testes em pastas, criar coleções e requests.

- [Postman](https://www.getpostman.com/apps)

### Lista de Boletos

Tendo a aplicação rodando, através do método GET pode ser acessado a URL:

``http://localhost:8080/rest/bankslips``

Ele retorna uma lista de todos os boletos em json:

```json
[
    {
        "id": "c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89",
        "due_date": "2018-02-01",
        "total_in_cents": 200000,
        "customer": "Zaphod Company",
        "status": "PENDING"
    },
    {
        "id": "84e8adbf-1a14-403b-ad73-d78ae19b59bf",
        "due_date": "2018-01-01",
        "total_in_cents": 100000,
        "customer": "Ford Prefect Company",
        "status": "PAID"
    }
```


### Criar Boletos

URL de acesso para criação de Boletos com método POST:

``http://localhost:8080/rest/bankslips``

No body devemos passar os seguintes campos que seram salvos no banco de dados caso execute com sucesso:

|Campo|Tipo|Formato|
|---|---|---|
|due_date|date|AAAA-MM-DD|
|total_in_cents|int32|valor em centavos|
|customer|string||
|status|string|PENDING, PAID, CANCELED|

Exemplo de como deve ficar:

```json
{
	"due_date":"2018-02-21",
	"total_in_cents":"600000",
	"customer":"Cash Company",
	"status":"PENDING"
}
```

Todos os campos são obrigatórios, gerando mensagem de erro caso não tenha sido informado algum campo ou esteja no formato errado.

Exemplo de retorno com algumas das mensagens de erro:

```json
{
    "error": "422",
    "message": "Invalid bankslip provided.The possible reasons are:",
    "errorList": [
        "Status must not be null, choice between PENDING, PAID or CANCELED",
        "Customer name must not be null nor empty",
        "Total in cents must be a positive number, greater than 0",
        "Due date must not be null, insert some date in format yyyy-MM-dd"
    ]
}
```

#### Detalhes de um Boleto

URL de acesso através do método GET para visualizar os detalhes de um boleto:

``http://localhost:8080/rest/bankslips/{id}``

Devemos passar no lugar do {id} o id do boleto que devemos consultar.
Caso tenha encontrado com sucesso, mostra os detalhes do boleto seguindo a seguinte regra:

* Até 10 dias: Multa de 0,5% (Juros Simples)
* Acima de 10 dias: Multa de 1% (Juros Simples)

|Campo|Tipo|Formato|
|---|---|---|
|id|uuid||
|due_date|date|AAAA-MM-DD|
|total_in_cents|int32|valor em centavos|
|customer|string||
|fine|int32|multa em centavos|
|status|string|PENDING, PAID, CANCELED|

Exemplo do retorno:

```json
{
	"id":"c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89",
	"due_date":"2018-01-01",
	"total_in_cents":"100000",
	"customer":"Ford Prefect Company",
	"fine":"1000",
	"status":"PENDING"
}
```

### Pagar um Boletos

URL de acesso através do método PUT para pagar um boleto:

``http://localhost:8080/rest/bankslips/{id}/pay``

Devemos passar no lugar do {id} o id do boleto que queremos pagar. 
Ao executar com sucesso o status do boleto deve ter sido alterado para PAID.

Exemplo do retorno:

```json
{
	"id":"c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89",
	"status":"PAID"
}
```

### Cancelar um Boleto

URL de acesso através do método DELETE para cancelar um boleto:

``http://localhost:8080/rest/bankslips/{id}/cancel``

Devemos passar no lugar do {id} o id do boleto que queremos cancelar. 
Ao executar com sucesso o status do boleto deve ter sido alterado para CANCELED.

Exemplo do retorno:

```json
{
	"id":"c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89",
	"status":"CANCELED"
}
```

## Log da Aplicação

O log da aplicação pode ser visto pelo console ou através do arquivo `boleto.log` na raiz do projeto ou caminho de execução. O log ajuda a verificar passos executados dentro da aplicação, assim como possíveis erros ocorridos dando mais detalhes sobre o que pode ter causado o erro.
=======
# boleto

Desenvolvimento do projeto boleto utilizando-se da linguagem JAVA
>>>>>>> branch 'master' of https://github.com/cleversonhasse/boleto

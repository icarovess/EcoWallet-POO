# EcoWallet - Gerenciador Financeiro

EcoWallet é uma aplicação desktop desenvolvida em Java com JavaFX que permite gerenciar finanças pessoais, registrando receitas e despesas com categorização e persistência local de dados.

## Funcionalidades

- Registro de receitas e despesas
- Categorização por tipo: ALIMENTACAO, TRANSPORTE, LAZER, SAUDE, MORADIA, EDUCACAO, OUTROS
- Cálculo automático de saldo total
- Persistência local em JSON (salvo em `~/ecowallet_dados.json`)
- Remoção de transações
- Interface com tabela colorida por tipo de transação

## Tecnologias

- Java 21
- JavaFX 21
- Gson 2.10.1
- Maven 3.8+

## Estrutura do Projeto
```bash
EcoWallet-POO/
├── src/
│   └── main/
│       ├── java/
│       │   ├── app/
│       │   │   └── Main.java
│       │   │
│       │   ├── controller/
│       │   │   └── FinanceiroController.java
│       │   │
│       │   ├── factory/
│       │   │   └── TransacaoFactory.java
│       │   │
│       │   ├── model/
│       │   │   ├── Categoria.java
│       │   │   ├── Despesa.java
│       │   │   ├── Receita.java
│       │   │   └── Transacao.java
│       │   │
│       │   └── service/
│       │       └── GerenciadorFinancas.java
│       │
│       └── resources/
│           └── view/
│               ├── PrincipalView.fxml
│               └── style.css
├── dados/
│   └── ecowallet_dados.json
│
├── module-info.java
├── pom.xml
├── LICENSE
└── README.md
```

## Pré-requisitos

- JDK 21+
- Maven 3.8+

## Como Executar

```bash
cd EcoWallet-POO
mvn clean javafx:run
```

## Persistência de Dados

As transações são salvas automaticamente em formato JSON no diretório do usuário:

- `EcoWallet-POO/dados/ecowallet_dados.json`

O arquivo é criado automaticamente na primeira execução.
Os dados são carregados automaticamente ao iniciar a aplicação.

## Padrões de Projeto Utilizados

- **Factory**: `TransacaoFactory` centraliza a criação de objetos `Receita` e `Despesa`
- **Herança e Polimorfismo**: `Transacao` é abstrata, com `getValorparaSaldo()` implementado diferente em cada subclasse
- **Separação de responsabilidades**: model / controller / service / factory

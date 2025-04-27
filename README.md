# Simple Bank Account API

A Spring Boot RESTful API for managing bank accounts with functionalities like account creation, deposits, withdrawals, balance inquiry, and transaction history.

## Objective

Develop a simple and robust bank account management API as part of a Spring Boot challenge.

## Features

- **Account Management**
  - Create new bank accounts.
  - Retrieve account details using the account number.
- **Transactions**
  - Deposit money into an account.
  - Withdraw money from an account (with balance validation).
  - Check account balance.
- **Transaction History**
  - Record and retrieve deposit and withdrawal transactions.
- **Error Handling**
  - Proper HTTP status codes and descriptive error messages.
  - Exception handling for invalid inputs.
- **Database**
  - Spring Data JPA with a PostgreSQL database.

## Technologies

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven

## API Endpoints

| Method | Endpoint | Description |
|:------:|:--------:|:-----------:|
| POST | `/accounts` | Create a new account |
| GET | `/accounts/{accountNumber}` | Retrieve account details |
| POST | `/transactions/deposit` | Deposit money |
| POST | `/transactions/withdraw` | Withdraw money |
| GET | `/accounts/{accountNumber}/balance` | Get account balance |
| GET | `/accounts/{accountNumber}/transactions` | Get transaction history |

## Requirements

- Each account must have:
  - A unique account number
  - An account holder’s name
  - An initial non-negative balance
- Withdrawal cannot exceed available balance.
- All transactions (deposit and withdrawal) must be recorded and linked to an account.

## Bonus Features (Optional)

- Pagination for transaction history
- Support for multiple currencies
- Basic authentication for account access

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+

### Running the Application

```bash
git clone https://github.com/your-username/simple-bank-account-api.git
cd simple-bank-account-api
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

## License

This project is licensed under the MIT License.


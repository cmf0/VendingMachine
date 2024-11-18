# MaquinaDeVendas

This project simulates a vending machine that manages sales of chocolates, soft drinks (refrigerants), and sandwiches (sandes). It includes user access with different roles: customers can make purchases, while employees (collaborators) can manage inventory, view sales, and clear transaction history. The application also handles balance transactions, maintains stock levels, and saves data persistently in files.

## Features

- **Customer Mode:**
  - **Purchase Products:** Customers can deposit money into the machine and select items from three categories: chocolates, soft drinks, and sandwiches.
  - **View Stock:** Customers can browse available products and see detailed information (name, brand, price, expiration date).
  - **Cancel Purchase:** Customers can cancel the purchase at any point and receive their remaining balance.

- **Employee Mode (Admin Authentication):**
  - **Product Management:** Employees can add or remove items from the stock of chocolates, soft drinks, and sandwiches.
  - **Sales Reports:** View the total earnings (revenue) of the vending machine.
  - **Transaction History:** View and optionally clear the history of all purchases made in the machine.
  - **Authentication:** Employees must log in using a secure password (Base64 encoded).

- **Data Persistence:** The machine saves its state (inventory, sales history, balance) in a file (`stock.dat`), allowing it to persist between program runs.

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/maquinavendas.git
    ```
2. Compile and run the project using your preferred Java IDE or via the terminal:
    ```bash
    javac Main.java
    java Main
    ```

## Usage

1. **Start the Machine**: 
   - The machine initializes by loading the stock data from the `stock.dat` file, if available.
   - If no stock data exists, the machine starts with empty inventory.

2. **Customer Operations**:
   - Choose the "1" option to enter money into the machine and browse available products.
   - Select a product by ID and proceed with the purchase if there is enough balance.
   - If the balance is insufficient, the machine will prompt for additional money.

3. **Employee Operations**:
   - Employees can log in by entering a password (predefined in the code).
   - Once authenticated, employees can manage inventory, view sales reports, or clear transaction history.

4. **Saving and Loading Stock**:
   - The vending machine state is automatically saved to `stock.dat` after each transaction.
   - The state is reloaded from `stock.dat` when the machine is started.

## Project Structure

- **MaquinaDeVendas.java**: Main class that contains the core functionality of the vending machine.
  - Handles customer interactions, product purchases, and stock management.
  - Manages employee authentication and inventory updates.
  
- **Produto.java**: Represents a product in the vending machine (e.g., `Chocolate`, `Refrigerante`, `Sandes`).
  - Contains details such as name, price, and expiration date.

- **Ler.java**: Utility class for handling user input (reading integers, doubles, and strings).

## Requirements

- Java 8 or higher
- Basic understanding of Java programming concepts (classes, objects, collections)

## Dependencies

This project does not rely on external libraries but makes use of standard Java libraries for file I/O, object serialization, and date-time manipulation.

## Example Workflow

1. When the machine starts, the customer will choose to make a purchase or configure the machine.
2. The customer deposits money, browses available products, and makes a purchase.
3. The machine processes the transaction and updates its balance and stock.
4. An employee logs in to manage the inventory and view sales data.

## Authors

- **Cláudio Fonte**
- **Diogo Samuel**

## Acknowledgments

- Developed as part of the CITEFORMA 2024 training program.

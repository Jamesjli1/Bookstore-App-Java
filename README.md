# Bookstore Application (Java)

**Course:** COE 528 - Winter 2026  
**School:** Toronto Metropolitan University  
**Project:** Bookstore Application  
**Language:** Java  
**Framework:** Java Swing  
**IDE:** Apache NetBeans  
**Design Pattern:** State Design Pattern  

---

## Objectives

- Design and implement a **Java-based bookstore management application** using object-oriented programming (OOP).
- Build a graphical user interface (GUI) using **Java Swing**.
- Implement a modular system consisting of:
  - **Book and Customer Classes** for managing application data
  - **State Design Pattern** for customer membership levels
  - **Inventory Management** for adding, viewing, and removing books
  - **Customer Management** for registration and account management
  - **File-Based Persistence** for storing books and customer information
- Implement a customer loyalty system supporting point accumulation, redemption, and membership status changes.

---

## System Overview

The application provides a single-window graphical interface supporting two types of users:

- **Owner:** Manages book inventory and customer accounts.
- **Customer:** Browses and purchases books, earns loyalty points, and redeems points for discounts.

The application uses **Java Swing's CardLayout** to switch between screens within a single window.

Customer membership is managed using the **State Design Pattern**, allowing automatic transitions between Silver and Gold membership based on accumulated points.

Application data is loaded from text files at startup and saved when the application closes.

---

## Features

### Owner Features
- Log in using owner credentials.
- Add, view, and delete books from inventory.
- Register and remove customer accounts.
- View customer information and loyalty points.

### Customer Features
- Log in using registered customer credentials.
- Browse and select multiple books for purchase.
- Purchase books and earn loyalty points.
- Redeem accumulated points for discounts.
- View current points and membership status.

### Loyalty System
- **Silver:** Fewer than 1,000 points.
- **Gold:** 1,000 points or more.
- Earn **10 points per $1 CAD** spent.
- Redeem **100 points for a $1 CAD** discount.
- Membership status updates automatically when points change.

---

## Repository Structure

    src/bookstore/
    ├── Book.java              # Book information and pricing
    ├── BookStoreFrame.java    # Java Swing GUI and application navigation
    ├── Customer.java          # Customer accounts, points, and membership state
    ├── CustomerState.java     # State Design Pattern interface
    ├── SilverState.java       # Silver membership state
    ├── GoldState.java         # Gold membership state
    ├── StoreData.java         # Book and customer data management
    ├── DataManager.java       # File loading and saving operations
    └── Main.java              # Application entry point

    nbproject/                  # NetBeans project configuration
    books.txt                   # Book inventory data
    customers.txt               # Customer account data
    build.xml                   # Ant build configuration
    manifest.mf                 # Application manifest
    .gitignore                  # Excluded generated and local files

---

## Running the Application

1. Install **Java JDK 17** and Apache NetBeans.
2. Clone or download the repository.
3. Open the project folder in NetBeans.
4. Run `Main.java` or use the NetBeans **Run Project** command.
5. Log in as the owner or a registered customer to use the application.

The application uses `books.txt` and `customers.txt` in the project working directory to maintain data between sessions.

---

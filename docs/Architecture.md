# Library Management System
## System Architecture Document

**Version:** 1.0  
**Language:** Java 25  
**Application Type:** Console-Based  
**Team Size:** 3 Developers  
**Storage:** In-Memory  
**Project Focus:** Object-Oriented Programming, Custom Data Structures and Algorithms

---

## 1. Project Overview

The Library Management System is a console-based Java application designed to manage the daily operations of a library.

The primary objective is to develop a functional library management system while demonstrating a practical understanding of Object-Oriented Programming (OOP), Data Structures and Algorithms (DSA).

Unlike conventional projects that rely on Java's built-in Collections Framework, this project will implement its core data structures from scratch.

The application will support book management, member management, borrowing and returning, reservations, transaction history, undo operations and library analytics.

## 2. Project Objectives

- Apply encapsulation, inheritance, abstraction and polymorphism.
- Implement custom data structures without relying on Java Collections.
- Apply searching and sorting algorithms to real-world operations.
- Understand time and space complexity.
- Implement exception handling and input validation.
- Develop modular, maintainable and reusable code.
- Practice collaborative software development using Git and GitHub.

## 3. Technology Stack

| Technology | Purpose |
|---|---|
| Java 25 | Primary programming language |
| VS code | Development environment |
| Git | Version control |
| GitHub | Repository and collaboration |
| Java LocalDate | Date management |
| Java Scanner | Console input |

No graphical interface or database will be used in version 1.0.

## 4. System Scope

### 4.1 Book Management

The system will support:

- Adding new books.
- Updating book information.
- Deleting books.
- Searching by ISBN.
- Searching by title.
- Searching by author.
- Displaying all books.
- Sorting books by title, author and publication year.
- Tracking total and available copies.

Every book must have a unique ISBN.

### 4.2 Member Management

The system will support:

- Registering new members.
- Updating member information.
- Searching by member ID.
- Displaying member details.
- Deactivating members.
- Tracking membership status.
- Tracking active borrowing status.

Each member may have only one active borrowing transaction at a time.

### 4.3 Borrowing and Returning

The system will support:

- Issuing available books.
- Validating member eligibility.
- Preventing multiple simultaneous borrowings by one member.
- Generating due dates.
- Processing book returns.
- Updating available copies.
- Calculating overdue fines.

### 4.4 Reservation Management

The system will support:

- Reserving unavailable books.
- Maintaining a separate FIFO waiting queue for each ISBN.
- Cancelling reservations.
- Processing the next eligible reservation when a copy is returned.
- Preventing duplicate active reservations by the same member for the same book.

### 4.5 Transaction History

The system will record:

- Transaction ID.
- Member ID.
- ISBN.
- Issue date.
- Due date.
- Return date.
- Transaction status.
- Fine amount.

Transaction history will be maintained chronologically.

### 4.6 Undo Operations

The system will support undoing selected catalog operations:

- Adding a book.
- Updating a book.
- Deleting a book.

Undo will use a custom Stack and reversible operation records.

Borrowing, returning and financial transactions will not be undoable in version 1.0.

### 4.7 Reports and Analytics

The system will generate:

- Most borrowed books.
- Most active members.
- Currently borrowed books.
- Overdue books.
- Available and borrowed copy counts.
- Total fines collected.
- Transaction summaries.

## 5. System Architecture

The application will follow a layered architecture.

### 5.1 Application Layer

Responsible for application startup and navigation.

Main components:

- Main
- ConsoleMenu
- BookMenu
- MemberMenu
- BorrowMenu
- ReportMenu

This layer handles user interaction and delegates operations to services.

### 5.2 Service Layer

Contains business logic and coordinates data structures.

| Service | Responsibility |
|---|---|
| BookService | Book management and indexing |
| MemberService | Member registration and management |
| BorrowService | Issuing and returning books |
| ReservationService | Reservation queue management |
| TransactionService | Transaction recording and history |
| ReportService | Reports and analytics |
| UndoService | Reversing supported catalog operations |

### 5.3 Model Layer

Contains the application's main entities.

| Model | Description |
|---|---|
| Person | Abstract base class for people |
| Member | Library member |
| Librarian | Library administrator |
| Book | Book information and copy availability |
| Transaction | Borrowing and returning record |
| Reservation | Book reservation record |

### 5.4 Data Structure Layer

Contains all custom data structure implementations.

- DynamicArray
- SinglyLinkedList
- CircularQueue
- CustomStack
- BinarySearchTree
- HashTable

### 5.5 Utility and Exception Layers

Utilities:

- InputValidator
- DateUtility
- IDGenerator

Custom exceptions:

- BookNotFoundException
- MemberNotFoundException
- BookUnavailableException
- BorrowLimitExceededException
- ReservationException

## 6. Proposed Package Structure

```text
src/
└── library/
    ├── Main.java
    │
    ├── models/
    │   ├── Person.java
    │   ├── Member.java
    │   ├── Librarian.java
    │   ├── Book.java
    │   ├── Transaction.java
    │   └── Reservation.java
    │
    ├── datastructures/
    │   ├── DynamicArray.java
    │   ├── SinglyLinkedList.java
    │   ├── CircularQueue.java
    │   ├── CustomStack.java
    │   ├── BinarySearchTree.java
    │   └── HashTable.java
    │
    ├── services/
    │   ├── BookService.java
    │   ├── MemberService.java
    │   ├── BorrowService.java
    │   ├── ReservationService.java
    │   ├── TransactionService.java
    │   ├── ReportService.java
    │   └── UndoService.java
    │
    ├── interfaces/
    │   ├── Displayable.java
    │   └── Searchable.java
    │
    ├── exceptions/
    │   ├── BookNotFoundException.java
    │   ├── MemberNotFoundException.java
    │   ├── BookUnavailableException.java
    │   ├── BorrowLimitExceededException.java
    │   └── ReservationException.java
    │
    ├── utilities/
    │   ├── InputValidator.java
    │   ├── DateUtility.java
    │   └── IDGenerator.java
    │
    └── ui/
        ├── ConsoleMenu.java
        ├── BookMenu.java
        ├── MemberMenu.java
        ├── BorrowMenu.java
        └── ReportMenu.java
```

## 7. Object-Oriented Design

### 7.1 Encapsulation

All model attributes will be private. State changes will happen through controlled methods with validation.

### 7.2 Inheritance

Person will be an abstract base class.

```text
          Person
          /    \
      Member  Librarian
```

Member and Librarian will inherit common person attributes.

### 7.3 Abstraction

Interfaces will define common behaviors where useful.

Examples:

- Displayable
- Searchable

Abstract classes may be introduced when shared behavior genuinely exists.

### 7.4 Polymorphism

Method overriding will be used for appropriate specialized behavior, such as displaying different person profiles and applying role-specific operations.

## 8. Data Ownership and Indexing

The DynamicArray will be the primary storage owner for books and members.

Secondary indexes will reference the same objects rather than maintaining duplicate copies.

| Data | Primary storage | Secondary index |
|---|---|---|
| Books | DynamicArray | HashTable by ISBN |
| Books | DynamicArray | BST by title |
| Members | DynamicArray | HashTable by member ID |
| Transactions | SinglyLinkedList | Traversal/filtering |
| Reservations | CircularQueue per ISBN | HashTable by ISBN |
| Undo records | CustomStack | None |

The service layer is responsible for maintaining consistency between primary storage and secondary indexes.

## 9. Major System Workflows

### 9.1 Borrowing Workflow

1. Receive member ID.
2. Find the member using the HashTable.
3. Validate membership status.
4. Check whether the member already has an active borrowing transaction.
5. Receive ISBN.
6. Retrieve the book through the ISBN HashTable.
7. Check book availability.
8. Check whether an eligible reservation has priority.
9. Create a borrowing transaction.
10. Decrease available copies.
11. Record the transaction in the SinglyLinkedList.
12. Display confirmation and due date.

### 9.2 Returning Workflow

1. Receive member ID and ISBN.
2. Verify the member's active borrowing record.
3. Verify the book being returned.
4. Calculate overdue days.
5. Calculate the fine.
6. Close the active transaction.
7. Increase available copies.
8. Check the reservation queue for that ISBN.
9. Process the next eligible reservation.
10. Display the return confirmation.

### 9.3 Reservation Workflow

1. Receive member ID and ISBN.
2. Validate member and book.
3. Check whether the member already has an active reservation for that book.
4. Check whether the book is unavailable.
5. Retrieve or create the book's reservation queue.
6. Enqueue the reservation.
7. Display its confirmation.

### 9.4 Undo Workflow

1. Receive an undo request.
2. Check whether the Stack is empty.
3. Pop the most recent undoable operation.
4. Execute its reverse operation.
5. Restore affected indexes.
6. Display the result.

## 10. Business Rules

1. ISBN must be unique.
2. Member IDs must be unique.
3. A member may have at most one active borrowing.
4. Inactive members cannot borrow books.
5. A book with zero available copies cannot be borrowed directly.
6. A book with active borrowing transactions cannot be deleted.
7. A book with pending reservations cannot be deleted without resolving those reservations.
8. Reservation queues follow FIFO.
9. Duplicate active reservations are not permitted.
10. Available copies cannot exceed total copies.
11. Available copies cannot become negative.
12. Transaction history must remain intact.
13. Undo applies only to supported catalog operations.
14. A reserved copy must be allocated to the next eligible member before general borrowing.

## 11. Fine Calculation

Fine calculation will use a configurable daily fine rate.

```text
Fine = Overdue Days × Daily Fine Rate
```

Java's LocalDate API will be used to calculate overdue days.

## 12. Error Handling

The system must handle:

- Invalid menu selections.
- Invalid IDs and ISBNs.
- Duplicate records.
- Missing records.
- Empty data structures.
- Invalid indexes.
- Full queues.
- Stack underflow.
- Hash collisions.
- Invalid dates.
- Invalid copy counts.
- Unauthorized or invalid operations.

Custom exceptions will be used where they improve clarity.

## 13. Technical Constraints

- Java 25.
- Console-based application.
- No GUI framework.
- No database in version 1.0.
- In-memory storage only.
- No Java Collections Framework for core data storage.
- No built-in sorting algorithms for core sorting functionality.
- All six data structures must be implemented manually.
- Java standard language and utility features are allowed where appropriate.
- All core operations must be tested.

## 14. Project Development Strategy

The project will be developed in seven milestones:

1. Project foundation and GitHub setup.
2. Independent implementation of custom data structures.
3. Models and book/member management.
4. Borrowing, returning and reservations.
5. Undo, sorting and analytics.
6. Integration and testing.
7. Documentation and final release.

## 15. Version 1.0 Completion Criteria

The project will be considered complete when:

- All six custom data structures work independently.
- All required application modules are functional.
- Data structure integration is consistent.
- Borrowing and returning workflows work correctly.
- Reservation queues maintain FIFO behavior.
- Supported undo operations restore the correct state.
- Searching and sorting algorithms work correctly.
- Important edge cases are handled.
- Code is reviewed and merged through GitHub.
- Documentation is complete.

---

**End of System Architecture Document**
# Library Management System
## Custom Data Structures and Algorithms Specification

**Version:** 1.0  
**Language:** Java 25  
**Project:** Library Management System  
**Team Size:** 3 Developers

---

## 1. Purpose

This document defines the custom data structures and algorithms that will power the Library Management System.

The primary objective is to implement each structure independently, understand its internal working, analyze its complexity and integrate it into an actual application feature.

Java's Collections Framework will not be used for the core implementations.

Every data structure must be implemented from scratch using arrays, custom nodes, references and appropriate algorithms.

## 2. Team Responsibilities

| Developer | Assigned Data Structures |
|---|---|
| Sheeraz Ahmed | Binary Search Tree, Hash Table |
| Zuhair Ahmed | Circular Queue, Custom Stack |
| Zaheer Ahmed | Dynamic Array, Singly Linked List |

All members are responsible for testing and documenting their own implementations.

Sheeraz will review and coordinate the integration of all six structures.

---

# 3. Dynamic Array

**Assigned to:** Zaheer Ahmed

### 3.1 Purpose

The Dynamic Array will provide resizable indexed storage for the system's books and members.

Unlike a fixed-size array, it will automatically increase its capacity when additional elements are inserted.

### 3.2 Internal Representation

```java
public class DynamicArray<T> {

    private T[] elements;
    private int size;
    private int capacity;

}
```

The implementation will use an internal array and maintain separate logical size and allocated capacity.

### 3.3 Required Methods

| Method | Responsibility |
|---|---|
| `add(T element)` | Append an element |
| `get(int index)` | Retrieve an element |
| `set(int index, T element)` | Replace an element |
| `remove(int index)` | Remove an element |
| `size()` | Return current size |
| `isEmpty()` | Check whether empty |
| `contains(T element)` | Check whether an element exists |
| `clear()` | Remove all elements |

### 3.4 Required Behavior

- Initial capacity management.
- Automatic resizing.
- Element shifting after removal.
- Index validation.
- Null handling policy.
- Capacity management.

### 3.5 Complexity Expectations

| Operation | Expected Complexity |
|---|---|
| Access | O(1) |
| Update | O(1) |
| Append | O(1) amortized |
| Append during resizing | O(n) |
| Insert at arbitrary index | O(n) |
| Remove at arbitrary index | O(n) |
| Linear search | O(n) |

### 3.6 Application Integration

Used by:

- BookService.
- MemberService.

The Dynamic Array will own the primary book and member records.

### 3.7 Testing Requirements

- Adding to an empty array.
- Adding beyond initial capacity.
- Resizing multiple times.
- Removing the first element.
- Removing the last element.
- Removing a middle element.
- Accessing an invalid index.
- Removing from an empty array.

---

# 4. Singly Linked List

**Assigned to:** Zaheer Ahmed

### 4.1 Purpose

The Singly Linked List will maintain the library's transaction history.

Every borrowing and returning operation will generate a transaction record.

### 4.2 Internal Representation

```java
private static class Node<T> {

    T data;
    Node<T> next;

}
```

The list will maintain:

```java
private Node<T> head;
private Node<T> tail;
private int size;
```

### 4.3 Required Methods

| Method | Responsibility |
|---|---|
| `add(T data)` | Append a node |
| `addFirst(T data)` | Insert at beginning |
| `get(int index)` | Retrieve an element |
| `remove(int index)` | Remove an element |
| `removeFirst()` | Remove the first node |
| `removeLast()` | Remove the last node |
| `size()` | Return list size |
| `isEmpty()` | Check whether empty |
| `display()` | Traverse and display |

### 4.4 Required Behavior

- Maintain head and tail references.
- Correctly update links during deletion.
- Handle empty and single-node lists.
- Preserve chronological transaction order.
- Support traversal without exposing internal nodes.

### 4.5 Complexity Expectations

| Operation | Expected Complexity |
|---|---|
| Insert at beginning | O(1) |
| Insert at end | O(1) with tail |
| Access by index | O(n) |
| Search | O(n) |
| Remove first | O(1) |
| Remove last | O(n) |
| Traversal | O(n) |

### 4.6 Application Integration

Used by TransactionService.

Transaction records will be appended chronologically.

Historical filtering by member ID or ISBN will use traversal.

### 4.7 Testing Requirements

- Insert into an empty list.
- Insert at beginning and end.
- Delete head.
- Delete tail.
- Delete middle node.
- Delete from an empty list.
- Handle a single-node list.
- Verify correct tail references.

---

# 5. Circular Queue

**Assigned to:** Zuhair Ahmed

### 5.1 Purpose

The Circular Queue will manage reservation waiting lists.

Each book will have its own independent queue.

The FIFO principle ensures that the earliest eligible reservation is processed first.

### 5.2 Internal Representation

```java
public class CircularQueue<T> {

    private T[] elements;
    private int front;
    private int rear;
    private int size;
    private int capacity;

}
```

### 5.3 Required Methods

| Method | Responsibility |
|---|---|
| `enqueue(T element)` | Add at rear |
| `dequeue()` | Remove from front |
| `peek()` | View front element |
| `isEmpty()` | Check whether empty |
| `isFull()` | Check whether full |
| `size()` | Return current size |
| `display()` | Display queue elements |

### 5.4 Required Behavior

- Circular indexing.
- Wraparound after reaching the final array index.
- Correct front and rear updates.
- Overflow and underflow handling.
- Explicit capacity management.
- No element loss during resizing, if dynamic resizing is implemented.

### 5.5 Complexity Expectations

| Operation | Expected Complexity |
|---|---|
| Enqueue | O(1) amortized |
| Dequeue | O(1) |
| Peek | O(1) |
| isEmpty | O(1) |
| isFull | O(1) |
| Display | O(n) |

If the queue uses a fixed capacity, enqueue is O(1), but must handle overflow.

### 5.6 Application Integration

Used by ReservationService.

A HashTable will map ISBN values to their corresponding CircularQueue instances.

Conceptually:

```text
ISBN-001 → Queue A → Member 1 → Member 2
ISBN-002 → Queue B → Member 3 → Member 4
ISBN-003 → Queue C → Member 5
```

### 5.7 Testing Requirements

- Enqueue into empty queue.
- Dequeue from queue.
- Enqueue until full.
- Handle overflow.
- Handle underflow.
- Verify wraparound.
- Verify FIFO order.
- Test repeated enqueue/dequeue cycles.

---

# 6. Custom Stack

**Assigned to:** Zuhair Ahmed

### 6.1 Purpose

The Stack will implement undo functionality for selected catalog operations.

It will follow LIFO behavior.

The latest supported catalog operation will be reversed first.

### 6.2 Internal Representation

The Stack will use a custom linked-node implementation.

```java
public class CustomStack<T> {

    private Node<T> top;
    private int size;

}
```

### 6.3 Required Methods

| Method | Responsibility |
|---|---|
| `push(T data)` | Add element to top |
| `pop()` | Remove and return top |
| `peek()` | View top |
| `isEmpty()` | Check whether empty |
| `size()` | Return stack size |
| `clear()` | Clear stack |

### 6.4 Required Behavior

- Maintain top reference.
- Handle empty stack.
- Support generic elements.
- Correctly update size.
- Avoid exposing internal nodes.

### 6.5 Complexity Expectations

| Operation | Expected Complexity |
|---|---|
| Push | O(1) |
| Pop | O(1) |
| Peek | O(1) |
| isEmpty | O(1) |
| Size | O(1) |

### 6.6 Application Integration

Used by UndoService.

The Stack will store reversible operation objects or commands.

The undo mechanism must preserve the previous state of modified records.

Supported operations:

1. Add book → Undo removes the added book.
2. Update book → Undo restores previous values.
3. Delete book → Undo restores the deleted book and its indexes.

A suitable design may introduce an `UndoableOperation` interface with an `undo()` method.

### 6.7 Testing Requirements

- Push multiple elements.
- Pop elements in LIFO order.
- Peek without removal.
- Pop from empty stack.
- Push after emptying the stack.
- Verify size after operations.

---

# 7. Binary Search Tree

**Assigned to:** Sheeraz Ahmed

### 7.1 Purpose

The Binary Search Tree will provide an ordered index of books based on their titles.

It will support title searching and alphabetical traversal.

### 7.2 Internal Representation

```java
public class BinarySearchTree<T> {

    private Node<T> root;

}
```

Each node will contain:

```java
private static class Node<T> {

    String key;
    T value;

    Node<T> left;
    Node<T> right;

}
```

### 7.3 Required Methods

| Method | Responsibility |
|---|---|
| `insert(String key, T value)` | Insert a new key |
| `search(String key)` | Search for a key |
| `delete(String key)` | Delete a key |
| `contains(String key)` | Check key existence |
| `inOrderTraversal()` | Alphabetical traversal |
| `preOrderTraversal()` | Pre-order traversal |
| `postOrderTraversal()` | Post-order traversal |
| `height()` | Calculate tree height |
| `isEmpty()` | Check whether empty |

### 7.4 Duplicate Title Handling

Different books may share the same title.

The tree must support duplicate title keys without losing book records.

Recommended approach:

Each title node maintains a custom list of ISBN references for books sharing that title.

The tree's primary key will use a consistent case-insensitive comparison policy.

### 7.5 Required Behavior

- Correct insertion.
- Searching.
- Deletion of leaf nodes.
- Deletion of nodes with one child.
- Deletion of nodes with two children.
- Duplicate title handling.
- Alphabetical traversal.
- Case-insensitive title matching.

### 7.6 Complexity Expectations

| Operation | Average | Worst |
|---|---:|---:|
| Insert | O(log n) | O(n) |
| Search | O(log n) | O(n) |
| Delete | O(log n) | O(n) |
| Traversal | O(n) | O(n) |

This is an ordinary, unbalanced BST. It is not guaranteed to remain balanced.

### 7.7 Application Integration

Used by BookService.

The BST will maintain an index of book titles.

In-order traversal will generate an alphabetical listing.

The Dynamic Array remains the primary owner of book records.

### 7.8 Testing Requirements

- Insert into empty tree.
- Insert ascending and descending keys.
- Search existing and missing keys.
- Delete leaf node.
- Delete node with one child.
- Delete node with two children.
- Delete root.
- Handle duplicate titles.
- Verify in-order traversal.

---

# 8. Hash Table

**Assigned to:** Sheeraz Ahmed

### 8.1 Purpose

The Hash Table will provide efficient key-based lookups for books, members and reservation queues.

### 8.2 Internal Representation

```java
public class HashTable<K, V> {

    private Node<K, V>[] buckets;

    private int size;
    private int capacity;

    private static class Node<K, V> {

        K key;
        V value;
        Node<K, V> next;

    }

}
```

### 8.3 Collision Handling

Separate chaining will be used.

Each bucket will contain a linked chain of nodes.

If two keys produce the same index, both entries will be stored in that bucket's chain.

### 8.4 Required Methods

| Method | Responsibility |
|---|---|
| `put(K key, V value)` | Insert or update an entry |
| `get(K key)` | Retrieve a value |
| `remove(K key)` | Remove an entry |
| `containsKey(K key)` | Check key existence |
| `size()` | Return entry count |
| `isEmpty()` | Check whether empty |
| `resize()` | Expand the bucket array |
| `rehash()` | Recalculate bucket positions |

### 8.5 Required Behavior

- Custom hash index calculation.
- Collision handling.
- Key equality checking.
- Updating existing values.
- Dynamic resizing.
- Rehashing all existing entries.
- Null-key policy.
- Correct size management.

A configurable load factor will determine when resizing occurs.

Recommended initial load factor: 0.75.

### 8.6 Complexity Expectations

| Operation | Average | Worst |
|---|---:|---:|
| Insert | O(1) amortized | O(n) |
| Search | O(1) | O(n) |
| Delete | O(1) | O(n) |
| Contains | O(1) | O(n) |
| Resize and rehash | O(n) | O(n) |

### 8.7 Application Integration

The Hash Table will maintain:

| Key | Value |
|---|---|
| ISBN | Book reference |
| Member ID | Member reference |
| ISBN | Reservation Queue reference |

Separate HashTable instances will be used for these different mappings.

### 8.8 Testing Requirements

- Insert into empty table.
- Retrieve existing keys.
- Search missing keys.
- Handle collisions.
- Update existing keys.
- Remove existing keys.
- Remove missing keys.
- Trigger resizing.
- Verify rehashing.
- Test duplicate keys.
- Test null-key handling.

---

# 9. Searching and Sorting Algorithms

The project will also implement searching and sorting algorithms manually.

| Algorithm | Application | Assigned Developer |
|---|---|---|
| Linear Search | General record search | Zaheer |
| Binary Search | Search sorted arrays | Sheeraz |
| Bubble Sort | Basic sorting demonstration | Zaheer |
| Insertion Sort | Book sorting | Zuhair |
| Merge Sort | Efficient book sorting | Sheeraz |
| BST Traversal | Alphabetical title listing | Sheeraz |
| Hashing | Fast key-based lookup | Sheeraz |

All algorithms must be implemented without relying on Java's built-in sorting methods.

### Important requirements

- Binary Search must only operate on sorted input.
- Sorting must not accidentally corrupt the primary book storage.
- Sorting should use a temporary array when appropriate.
- Comparisons should be consistent and well-defined.
- Complexity should be documented and tested.

---

# 10. Integration Rules

These rules are mandatory for all developers.

### 10.1 Primary Data Ownership

The Dynamic Array owns the actual Book and Member records.

The Hash Table and BST hold references to those objects.

They must not maintain independent duplicate records.

### 10.2 Index Consistency

Whenever a book is added:

1. Add it to the Dynamic Array.
2. Insert its ISBN reference into the Hash Table.
3. Insert its title and ISBN reference into the BST.

Whenever a book is deleted:

1. Validate that deletion is permitted.
2. Remove it from primary storage.
3. Remove its ISBN entry from the Hash Table.
4. Remove its ISBN reference from the BST.
5. Remove the title node if no books remain under that title.

### 10.3 Reservation Consistency

Each ISBN must map to the correct reservation queue.

A returned book must be allocated to the next eligible reservation before being made generally available.

### 10.4 Transaction Consistency

Every successful borrowing and return must create an appropriate transaction history record.

Historical records must not be deleted during normal catalog operations.

### 10.5 Undo Consistency

Undo operations must restore all affected data and indexes.

A failed undo must not leave the system partially modified.

---

# 11. Coding Standards

All developers must follow these conventions:

- Use meaningful class and variable names.
- Keep attributes private.
- Use appropriate access modifiers.
- Use generics where useful.
- Use custom Node classes for linked structures.
- Avoid unnecessary static variables.
- Validate method arguments.
- Handle exceptional cases.
- Document public methods.
- Avoid duplicating business logic.
- Keep data structure implementations independent from application menus.

### Java Collections Restriction

The following must not be used as substitutes for our custom implementations:

- ArrayList
- LinkedList
- Stack
- Queue / ArrayDeque
- HashMap
- HashSet
- TreeMap
- TreeSet

Java arrays and standard language features are permitted.

---

# 12. Testing and Evaluation

Every developer must provide independent tests for their assigned data structures.

Testing must cover:

1. Normal operations.
2. Empty structures.
3. Boundary conditions.
4. Invalid input.
5. Duplicate data.
6. Overflow and underflow where applicable.
7. Large input.
8. Integration with services.

Each developer must also document:

- How the data structure works.
- Why it was selected.
- Its internal implementation.
- Its time complexity.
- Its space complexity.
- Its edge cases.
- Its application integration.

---

# 13. Definition of Done

A data structure is considered complete only when:

- Its implementation is functional.
- Its required methods are implemented.
- Edge cases are handled.
- Independent tests pass.
- Complexity analysis is documented.
- Code follows project conventions.
- A Pull Request is submitted.
- The implementation is reviewed and approved.

---

**End of Custom Data Structures and Algorithms Specification**
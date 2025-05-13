# Budgeting App 

## Overview
This is a Java-based Personal Finance Management System that helps users track their income, expenses, budgets, and set financial reminders. The program features user registration/login and data persistence through serialization.

## Files Included

1. `Main.java` - The main application file containing:
   - User authentication (Login/SignUp)
   - Income tracking (TrackIncome)
   - Budget management (Budget)
   - Expense tracking (Expense)
   - Reminder system (Reminder)
   - Data serialization (DataContainer)

## Features

### User Management
- Registration with credential validation
- Login with authentication
- Unique user IDs

### Financial Tracking
- Income tracking with source, amount and date
- Expense recording with category and payment method
- Budget creation with spending limits
- Future date validation for all financial entries

### Reminder System
- Set reminders with titles, dates and times
- Future time validation
- Notification status tracking

### Data Management
- All data is serialized for persistence
- DataContainer class handles all storage
- Supports saving/loading of complete application state

## Tools Used

### Development Tools
- Java Development Kit (JDK) 8 or later
- Any Java IDE (Eclipse, IntelliJ, VS Code, etc.)
- Java Serialization API for data persistence

### Libraries
- Java Standard Libraries:
  - java.io for serialization
  - java.time for date/time handling
  - java.util for collections and utilities

### Data Storage
- Uses Java Object Serialization to save data to file
- Single DataContainer class manages all application data
- All data is stored in memory during runtime

## How to Use
1. Compile and run Main.java
2. Register as a new user or login
3. Use the menu to:
   - Track income
   - Set budgets
   - Record expenses
   - Set reminders
4. All data is automatically saved when program exits

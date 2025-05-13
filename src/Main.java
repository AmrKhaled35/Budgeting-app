import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This is the main application class that coordinates all logic.
 */
public class Main {
    /** The ID of the currently logged-in user. -1 means no user is logged in. */
    static int currentUser = -1;

    /** Indicates whether a user is registered. */
    static boolean registered = false;

    /**
     * Inner class representing the login functionality.
     * Implements Serializable to allow object serialization.
     */
    public class Login implements Serializable {

        /** The username of the login. */
        String username;

        /** The password of the login. */
        String password;

        /**
         * Constructs a Login object with the specified username and password.
         *
         * @param username the user's username
         * @param password the user's password
         */
        public Login(String username, String password) {
            this.username = username;
            this.password = password;
        }

        /**
         * Verifies the provided credentials against a list of registered users.
         *
         * @param username the username to verify
         * @param password the password to verify
         * @param Signups  the list of registered users
         * @return true if credentials match a registered user; false otherwise
         */
        public static boolean verifyCredentials(String username, String password, Vector<SignUp> Signups) {
            for (int i = 0; i < Signups.size(); i++) {
                if (username.equals(Signups.get(i).username) && password.equals(Signups.get(i).password)) {
                    currentUser = Signups.get(i).UserId;
                    return true;
                }
            }
            return false;
        }

        /**
         * Attempts to log in with the provided credentials.
         * Displays success or failure messages accordingly.
         *
         * @param username the username to log in with
         * @param password the password to log in with
         * @param Signups  the list of registered users
         */
        public static void login(String username, String password, Vector<SignUp> Signups) {
            if (verifyCredentials(username, password, Signups)) {
                success();
            } else {
                fail();
            }
        }

        /**
         * Prints a success message to the console upon successful login.
         */
        public static void success() {
            System.out.println("Login successful");
        }

        /**
         * Prints a failure message to the console if login fails.
         */
        public static void fail() {
            System.out.println("Login failed: invalid credentials");
        }
    }

    /**
     * Handles user registration including credential validation.
     */
    static public class SignUp implements Serializable {

        /** Unique identifier for the user. */
        int UserId;

        /** Username of the user. */
        String username;

        /** Email address of the user. */
        String email;

        /** Password of the user. */
        String password;

        /**
         * Constructs a SignUp object with the specified user details.
         *
         * @param UserId   the user's unique ID
         * @param username the user's chosen username
         * @param password the user's chosen password
         * @param email    the user's email address
         */
        public SignUp(int UserId, String username, String password, String email) {
            this.UserId = UserId;
            this.username = username;
            this.password = password;
            this.email = email;
        }

        /**
         * Verifies the validity of user credentials.
         *
         * @param username the username to verify
         * @param password the password to verify
         * @param email    the email to verify
         * @return true if credentials are valid; false otherwise
         */
        public static boolean verifyCredentials(String username, String password, String email) {
            return username.length() >= 3 && password.length() >= 8 && email.contains("@") && email.contains(".");
        }

        /**
         * Attempts to register the user by validating credentials.
         */
        public void register() {
            if (verifyCredentials(this.username, this.password, this.email)) {
                success();
                registered = true;
            } else {
                fail();
            }
        }

        /** Displays success message after successful registration. */
        public static void success() {
            System.out.println("Registration successful");
        }

        /** Displays failure message if registration fails. */
        public static void fail() {
            System.out.println("Registration failed: invalid credentials");
        }
    }

    /**
     * Tracks income entries for users including creation, validation, and updates.
     */
    static public class TrackIncome implements Serializable {

        /** ID of the user this income entry belongs to. */
        int UserId;

        /** Source of the income. */
        String source;

        /** Amount of the income. */
        double amount;

        /** Date associated with the income (expected format: yyyy-MM-dd). */
        String date;

        /**
         * Constructs a TrackIncome object with given parameters.
         *
         * @param UserId the user's ID
         * @param source the income source
         * @param amount the income amount
         * @param date   the date of the income entry
         */
        public TrackIncome(int UserId, String source, double amount, String date) {
            this.UserId = UserId;
            this.source = source;
            this.amount = amount;
            this.date = date;
        }

        /**
         * Validates the income data (especially the date format and logic).
         *
         * @param source the income source
         * @param amount the income amount
         * @param date   the date of the income entry
         * @return true if the income date is valid (future date); false otherwise
         */
        public static boolean verifyIncome(String source, double amount, String date) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                java.time.LocalDate end = java.time.LocalDate.parse(date, formatter);
                java.time.LocalDate now = java.time.LocalDate.now();
                return end.isAfter(now);
            } catch (Exception e) {
                return false;
            }
        }

        /**
         * Saves the income data if valid.
         *
         * @param source the income source
         * @param amount the income amount
         * @param date   the date of the income
         */
        public static void saveIncome(String source, double amount, String date) {
            if (verifyIncome(source, amount, date)) {
                System.out.println("Income saved successfully:");
            } else {
                System.out.println("Invalid income details. Income not saved.");
            }
        }

        /** Displays the income entry details. */
        public void displayIncome() {
            System.out.println("Source: " + source);
            System.out.println("Amount: " + amount);
            System.out.println("Date: " + date);
            System.out.println("==================");
        }

        /**
         * Updates a selected field (source, amount, or date) of the income entry at a
         * given index.
         *
         * @param i             the index of the income entry to update
         * @param Track_Incomes the list of all income entries
         */
        public void updateIncome(int i, Vector<TrackIncome> Track_Incomes) {
            Scanner scan = new Scanner(System.in);

            System.out.println("choose what you want to update :");
            Track_Incomes.get(i).displayIncome();
            System.out.println("1 - source");
            System.out.println("2 - amount");
            System.out.println("3 - date");
            System.out.print("Enter your choice: ");
            int choice = scan.nextInt();

            while (choice < 1 || choice > 3) {
                System.out.println("Invalid choice");
                System.out.println("choose what you want to update :");
                choice = scan.nextInt();
            }

            if (choice == 1) {
                System.out.println("enter source: ");
                String source = scan.next();
                Track_Incomes.get(i).source = source;
            }

            if (choice == 2) {
                double amount;
                while (true) {
                    System.out.print("Enter amount: ");
                    amount = scan.nextDouble();
                    if (amount > 0) {
                        break;
                    } else {
                        System.out.println("Invalid amount. Amount must be greater than 0.");
                    }
                }
                Track_Incomes.get(i).amount = amount;
            }

            if (choice == 3) {
                String date;
                while (true) {
                    System.out.print("Enter date (in this format yyyy-MM-dd): ");
                    date = scan.next();
                    if (Track_Incomes.get(i).verifyIncome(source, amount, date)) {
                        break;
                    } else {
                        System.out.println("Invalid date. Please enter a valid date in this format: yyyy-MM-dd.");
                    }
                }
                Track_Incomes.get(i).date = date;
            }
        }
    }

    /**
     * Represents a user's budget configuration including total budget,
     * expense limit, and active date range.
     */
    static public class Budget implements Serializable {

        /** The user ID associated with the budget. */
        int userId;

        /** The total budget amount available. */
        double totalAmount;

        /** The maximum allowed expense within the budget period. */
        double expenseLimit;

        /** The start date of the budget (format: yyyy-MM-dd). */
        String startDate;

        /** The end date of the budget (format: yyyy-MM-dd). */
        String endDate;

        /**
         * Constructs a Budget object with specified details.
         *
         * @param userId       the ID of the user
         * @param totalAmount  the total budget amount
         * @param expenseLimit the maximum expense allowed
         * @param startDate    the start date of the budget
         * @param endDate      the end date of the budget
         */
        public Budget(int userId, double totalAmount, double expenseLimit, String startDate, String endDate) {
            this.userId = userId;
            this.totalAmount = totalAmount;
            this.expenseLimit = expenseLimit;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        /**
         * Verifies if the budget's end date is a future date.
         *
         * @return true if the end date is valid and in the future, false otherwise
         */
        public boolean verifyBudget() {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                java.time.LocalDate end = java.time.LocalDate.parse(endDate, formatter);
                java.time.LocalDate now = java.time.LocalDate.now();
                return end.isAfter(now);
            } catch (Exception e) {
                return false;
            }
        }

        /**
         * Displays the details of the budget to the console.
         */
        public void displayBudget() {
            System.out.println("budget: " + totalAmount);
            System.out.println("expenseLimit: " + expenseLimit);
            System.out.println("startDate: " + startDate);
            System.out.println("endDate: " + endDate);
            System.out.println("====================");
        }

        /**
         * Allows updating of a budget field (amount, expense limit, or end date) by
         * user input.
         *
         * @param i       the index of the budget entry in the list
         * @param Budgets the list of budget entries
         */
        public void updateBudget(int i, Vector<Budget> Budgets) {
            Scanner scan = new Scanner(System.in);

            System.out.println("choose what you want to update :");
            Budgets.get(i).displayBudget();
            System.out.println("1 - budget");
            System.out.println("2 - expenseLimit");
            System.out.println("3 - endDate");
            System.out.print("Enter your choice: ");
            int choice = scan.nextInt();

            while (choice < 1 || choice > 3) {
                System.out.println("Invalid choice");
                System.out.println("choose what you want to update :");
                choice = scan.nextInt();
            }

            if (choice == 1) {
                double budget;
                while (true) {
                    System.out.print("Enter a budget: ");
                    budget = scan.nextInt();
                    if (budget > 0) {
                        break;
                    } else {
                        System.out.println("Invalid budget. Please enter a valid budget.");
                    }
                }
                Budgets.get(i).totalAmount = budget;
            }

            if (choice == 2) {
                double expenseLimit;
                while (true) {
                    System.out.print("Enter expense limit: ");
                    expenseLimit = scan.nextInt();
                    if (expenseLimit > 0 && expenseLimit <= totalAmount) {
                        break;
                    } else {
                        System.out.println(
                                "Invalid expense limit. expense limit must be greater than 0 and less than or equal to budget.");
                    }
                }
                Budgets.get(i).expenseLimit = expenseLimit;
            }

            if (choice == 3) {
                String endDate;
                while (true) {
                    System.out.print("Enter end date (in this format: yyyy-MM-dd): ");
                    endDate = scan.next();
                    Budgets.get(i).endDate = endDate;
                    if (Budgets.get(i).verifyBudget()) {
                        break;
                    } else {
                        System.out.println("Invalid date. Please enter a valid date (in future).");
                    }
                }
            }
        }
    }

    /**
     * Represents a reminder set by a user with a title, date, time, and status for
     * notification.
     */
    static public class Reminder implements Serializable {

        /** Static ID for reminders (could be used for auto-increment if needed). */
        static int ReminderId;

        /** ID of the user to whom this reminder belongs. */
        int UserId;

        /** Title of the reminder. */
        String Title;

        /** Date of the reminder (format: yyyy-MM-dd). */
        String ReminderDate;

        /** Time of the reminder (format: HH:mm). */
        String ReminderTime;

        /** Indicates whether the reminder notification has been sent. */
        boolean notificationSend;

        /**
         * Constructs a new Reminder with specified user ID, title, date, and time.
         *
         * @param UserId       the ID of the user
         * @param Title        the title of the reminder
         * @param reminderDate the date for the reminder
         * @param reminderTime the time for the reminder
         */
        public Reminder(int UserId, String Title, String reminderDate, String reminderTime) {
            this.UserId = UserId;
            this.Title = Title;
            this.ReminderDate = reminderDate;
            this.ReminderTime = reminderTime;
        }

        /**
         * Validates whether the reminder's date and time is in the future.
         *
         * @return true if the reminder is set for a future time, false otherwise
         */
        public boolean validateReminder() {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime reminderDateTime = LocalDateTime.parse(ReminderDate + " " + ReminderTime, formatter);
                LocalDateTime now = LocalDateTime.now();
                return reminderDateTime.isAfter(now);
            } catch (Exception e) {
                return false;
            }
        }

        /**
         * Displays the reminder's details in the console.
         */
        public void viewRemiders() {
            System.out.println("title: " + Title);
            System.out.println("reminder date: " + ReminderDate);
            System.out.println("reminder time: " + ReminderTime);
            System.out.println("=========================");
        }

        /**
         * Allows the user to edit a specific reminder by choosing to update the title
         * or the date and time.
         *
         * @param i         the index of the reminder in the list
         * @param Reminders the list of all reminders
         */
        public void editReminder(int i, Vector<Reminder> Reminders) {
            Scanner scan = new Scanner(System.in);

            System.out.println("choose what you want to update: ");
            Reminders.get(i).viewRemiders();
            System.out.println("1 - title");
            System.out.println("2 - reminder date and time");
            System.out.print("Enter your choice: ");
            int choice = scan.nextInt();

            while (choice < 1 || choice > 2) {
                System.out.println("Invalid choice");
                System.out.print("choose what you want to update: ");
                choice = scan.nextInt();
            }

            if (choice == 1) {
                System.out.print("Enter title: ");
                String title = scan.next();
                Reminders.get(i).Title = title;
            }

            if (choice == 2) {
                while (true) {
                    System.out.print("Enter date and time (in this format: yyyy-MM-dd HH:mm): ");
                    String date = scan.next();
                    String time = scan.next();
                    Reminders.get(i).ReminderDate = date;
                    Reminders.get(i).ReminderTime = time;
                    if (Reminders.get(i).validateReminder()) {
                        break;
                    } else {
                        System.out.println("Invalid date and time. Please enter a valid date and time (in future).");
                    }
                }
            }
        }
    }

    /**
     * Represents an expense entry made by a user including amount, category, date,
     * and payment method.
     */
    static public class Expense implements Serializable {

        /** ID of the user who recorded this expense. */
        int UserId;

        /** Amount spent in the expense. */
        double amount;

        /** Category of the expense (e.g., food, transport, etc.). */
        String category;

        /** Date of the expense (format: yyyy-MM-dd). */
        String date;

        /** Method of payment used (e.g., Cash, Fawry, Credit Card, e-wallet). */
        String paymentmethod;

        /**
         * Constructs a new Expense object with specified details.
         *
         * @param UserId        the ID of the user
         * @param amount        the amount spent
         * @param category      the expense category
         * @param date          the date of the expense
         * @param paymentmethod the payment method used
         */
        public Expense(int UserId, double amount, String category, String date, String paymentmethod) {
            this.UserId = UserId;
            this.amount = amount;
            this.category = category;
            this.date = date;
            this.paymentmethod = paymentmethod;
        }

        /**
         * Validates whether the expense date is in the future.
         *
         * @return true if the date is valid and in the future, false otherwise
         */
        public boolean validateExpense() {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                java.time.LocalDate end = java.time.LocalDate.parse(date, formatter);
                java.time.LocalDate now = java.time.LocalDate.now();
                return end.isAfter(now);
            } catch (Exception e) {
                return false;
            }
        }

        /**
         * Displays a message confirming the expense has been saved.
         */
        public static void save_Expense() {
            System.out.println("Expense saved successfully");
        }

        /**
         * Displays all the details of this expense entry.
         */
        public void Display_Expense() {
            System.out.println("amount: " + amount);
            System.out.println("category:" + category);
            System.out.println("date: " + date);
            System.out.println("paymentmethod: " + paymentmethod);
            System.out.println("====================");
        }

        /**
         * Allows editing of a specific expense in a given list.
         * User can update amount, category, date, or payment method.
         *
         * @param i        the index of the expense in the list
         * @param Expenses the list of all expenses
         */
        public void Edit_Expense(int i, Vector<Expense> Expenses) {
            Scanner scan = new Scanner(System.in);

            System.out.println("choose what you want to update :");
            Expenses.get(i).Display_Expense();
            System.out.println("1 - amount");
            System.out.println("2 - category");
            System.out.println("3 - date");
            System.out.println("4 - paymentmethod");
            System.out.print("Enter your choice: ");
            int choice = scan.nextInt();

            while (choice < 1 || choice > 4) {
                System.out.println("Invalid choice");
                System.out.print("choose what you want to update: ");
                choice = scan.nextInt();
            }

            if (choice == 1) {
                double amount;
                while (true) {
                    System.out.print("Enter amount: ");
                    amount = scan.nextInt();
                    if (amount > 0) {
                        break;
                    } else {
                        System.out.println("Invalid amount. Amount must be greater than 0.");
                    }
                }
                Expenses.get(i).amount = amount;
            }

            if (choice == 2) {
                System.out.print("Enter category: ");
                String category = scan.next();
                Expenses.get(i).category = category;
            }

            if (choice == 3) {
                String paymentmethod;
                while (true) {
                    System.out.print("Enter payment method: ");
                    paymentmethod = scan.next();
                    if (paymentmethod.toLowerCase().equals("cash") || paymentmethod.toLowerCase().equals("fawry")
                            || paymentmethod.toLowerCase().equals("credit card")
                            || paymentmethod.toLowerCase().equals("e-wallet")) {
                        break;
                    } else {
                        System.out.println(
                                "Invalid payment method. Please enter a valid payment method (Cash, Fawry, Credit Card, e-wallet).");
                    }
                }
                Expenses.get(i).paymentmethod = paymentmethod;
            }

            if (choice == 4) {
                String date;
                while (true) {
                    System.out.print("Enter date (in this format: yyyy-MM-dd): ");
                    date = scan.next();
                    Expenses.get(i).date = date;
                    if (Expenses.get(i).validateExpense()) {
                        break;
                    } else {
                        System.out.println("Invalid date. Please enter a valid date (in future).");
                    }
                }
            }
        }
    }

    /**
     * A container class that holds all the application data in serialized form.
     * Used for saving and loading persistent data (e.g., to/from a file).
     */
    static class DataContainer implements Serializable {

        /** Serialization identifier for version control. */
        private static final long serialVersionUID = 1L;

        /** List of all registered users. */
        Vector<SignUp> SignUps;

        /** List of all budgets created by users. */
        Vector<Budget> Budgets;

        /** List of all reminders set by users. */
        Vector<Reminder> Reminders;

        /** List of all expenses recorded by users. */
        Vector<Expense> Expenses;

        /** List of all income records tracked by users. */
        Vector<TrackIncome> Track_Incomes;

        /**
         * Constructs a new DataContainer with the given application data.
         *
         * @param SignUps       the list of user registrations
         * @param Budgets       the list of budgets
         * @param reminders     the list of reminders
         * @param expenses      the list of expenses
         * @param Track_Incomes the list of income records
         */
        public DataContainer(Vector<SignUp> SignUps, Vector<Budget> Budgets, Vector<Reminder> reminders,
                Vector<Expense> expenses, Vector<TrackIncome> Track_Incomes) {
            this.SignUps = SignUps;
            this.Budgets = Budgets;
            this.Reminders = reminders;
            this.Expenses = expenses;
            this.Track_Incomes = Track_Incomes;
        }
    }
    /**
     * Entry point of the application.
     * @param args Command-line arguments
     */
    static public void main(String[] args) {
        String filename = "multi_lists.dat";

        // Initialize empty lists
        Vector<SignUp> SignUps = new Vector<>();
        Vector<Budget> Budgets = new Vector<>();
        Vector<Reminder> Reminders = new Vector<>();
        Vector<Expense> Expenses = new Vector<>();
        Vector<TrackIncome> Track_Incomes = new Vector<>();

        // ===== READ EXISTING DATA =====
        File file = new File(filename);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(filename))) {

                DataContainer loaded = (DataContainer) ois.readObject();
                SignUps = loaded.SignUps;
                Budgets = loaded.Budgets;
                Reminders = loaded.Reminders;
                Expenses = loaded.Expenses;
                Track_Incomes = loaded.Track_Incomes;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        int choice;
        Scanner scan = new Scanner(System.in);
        while (currentUser == -1) {
            System.out.println("Hello, Sir!");
            System.out.println("1 - Sign up");
            System.out.println("2 - Log in");
            System.out.print("Enter your choice: ");
            choice = scan.nextInt();
            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scan.next();
                System.out.print("Enter email: ");
                String email = scan.next();
                System.out.print("Enter password: ");
                String password = scan.next();
                SignUp signUp = new SignUp(SignUps.size(), username, password, email);
                signUp.register();
                if (!registered)
                    continue;
                SignUps.add(signUp);
                System.out.println("====================");
                System.out.println("Log in your account");
                System.out.println("====================");
                System.out.print("Enter username: ");
                username = scan.next();
                System.out.print("Enter password: ");
                password = scan.next();
                Login.login(username, password, SignUps);
            } else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = scan.next();
                System.out.print("Enter password: ");
                String password = scan.next();
                Login.login(username, password, SignUps);
            }
        }
        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1. Incomes");
            System.out.println("2. Budgets");
            System.out.println("3. Reminders");
            System.out.println("4. Expenses");
            System.out.println("5. Save and Exit");
            System.out.println("6. Exit without Saving");
            System.out.print("Enter your choice: ");
            choice = scan.nextInt();
            while (choice < 1 || choice > 6) {
                System.out.println("Invalid choice. Please enter a valid choice.");
                System.out.print("Enter your choice: ");
                choice = scan.nextInt();
            }
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
            String formattedDateTime = currentDateTime.format(formatter);
            if (choice == 1) {
                System.out.println("Choose an option");
                System.out.println("1. view incomes");
                System.out.println("2. add an income");
                System.out.println("3. update an income");
                System.out.println("4. delete an income ");
                System.out.println("5. Back to main menu");
                System.out.print("Enter your choice: ");
                choice = scan.nextInt();
                while (choice < 1 || choice > 5) {
                    System.out.println("Invalid option. Please enter a valid option.");
                    System.out.print("Enter your choice: ");
                    choice = scan.nextInt();
                }
                if (choice != 2 && choice != 5 && Track_Incomes.size() == 0) {
                    System.out.println("No incomes found. Please add an income.");
                    continue;
                }
                if (choice == 1) {
                    for (int i = 0; i < Track_Incomes.size(); i++) {
                        if (Track_Incomes.get(i).UserId == currentUser) {
                            Track_Incomes.get(i).displayIncome();
                        }
                    }
                }
                if (choice == 2) {
                    System.out.print("Enter source: ");
                    String source = scan.next();
                    double amount;
                    while (true) {
                        System.out.print("Enter amount: ");
                        amount = scan.nextDouble();
                        if (amount > 0) {
                            break;
                        } else {
                            System.out.println("Invalid amount. Amount must be greater than 0.");
                        }
                    }
                    TrackIncome trackIncome;
                    String date;
                    while (true) {
                        System.out.print("Enter date (in this format yyyy-MM-dd): ");
                        date = scan.next();
                        trackIncome = new TrackIncome(currentUser, source, amount, date);
                        if (trackIncome.verifyIncome(source, amount, date)) {
                            break;
                        } else {
                            System.out.println("Invalid date. Please enter a valid date in this format: yyyy-MM-dd.");
                        }

                    }
                    Track_Incomes.add(trackIncome);
                    trackIncome.saveIncome(source, amount, date);
                }
                if (choice == 3) {
                    int cnt = 0;
                    for (int i = 0; i < Track_Incomes.size(); i++) {
                        if (Track_Incomes.get(i).UserId == currentUser) {
                            System.out.println(++cnt + " - ");
                            Track_Incomes.get(i).displayIncome();
                        }
                    }
                    System.out.println("choose the income you want to update :");
                    int idx = scan.nextInt();
                    while (idx < 1 || idx > cnt) {
                        System.out.println("Invalid choice");
                        System.out.println("choose the income you want to update :");
                        idx = scan.nextInt();
                    }
                    cnt = 0;
                    for (int i = 0; i < Track_Incomes.size(); i++) {
                        if (Track_Incomes.get(i).UserId == currentUser) {
                            if (++cnt == idx) {
                                Track_Incomes.get(i).updateIncome(i, Track_Incomes);
                                break;
                            }
                        }
                    }
                }
                if (choice == 4) {
                    int cnt = 0;
                    for (int i = 0; i < Track_Incomes.size(); i++) {
                        if (Track_Incomes.get(i).UserId == currentUser) {
                            System.out.println(++cnt + " - ");
                            Track_Incomes.get(i).displayIncome();
                        }
                    }
                    System.out.println("choose the income you want to delete :");
                    int idx = scan.nextInt();
                    while (idx < 1 || idx > cnt) {
                        System.out.println("Invalid choice");
                        System.out.println("choose the income you want to delete :");
                        idx = scan.nextInt();
                    }
                    cnt = 0;
                    for (int i = 0; i < Track_Incomes.size(); i++) {
                        if (Track_Incomes.get(i).UserId == currentUser) {
                            if (++cnt == idx) {
                                Track_Incomes.remove(i);
                                break;
                            }
                        }
                    }
                }
            }

            else if (choice == 2) {
                System.out.println("Choose an option");
                System.out.println("1. view Budgets");
                System.out.println("2. add a budget");
                System.out.println("3. update a budget");
                System.out.println("4. delete a budget ");
                System.out.println("5. Back to main menu");
                System.out.print("Enter your choice: ");
                choice = scan.nextInt();
                while (choice < 1 || choice > 5) {
                    System.out.println("Invalid option. Please enter a valid option.");
                    System.out.print("Enter your choice: ");
                    choice = scan.nextInt();
                }
                if (choice != 2 && choice != 5 && Budgets.size() == 0) {
                    System.out.println("No budgets found. Please add a budget.");
                    continue;
                }
                if (choice == 1) {
                    for (int i = 0; i < Budgets.size(); i++) {
                        if (Budgets.get(i).userId == currentUser) {
                            Budgets.get(i).displayBudget();
                        }
                    }
                }
                if (choice == 2) {
                    double budget;
                    while (true) {
                        System.out.print("Enter a budget: ");
                        budget = scan.nextInt();
                        if (budget > 0) {
                            break;
                        } else {
                            System.out.println("Invalid budget. Please enter a valid budget.");
                        }
                    }
                    double expenseLimit;
                    while (true) {
                        System.out.print("Enter expense limit: ");
                        expenseLimit = scan.nextInt();
                        if (expenseLimit > 0 && expenseLimit <= budget) {
                            break;
                        } else {
                            System.out.println(
                                    "Invalid expense limit. expense limit must be greater than 0 and less than or equal to budget.");
                        }
                    }
                    String startDate = formattedDateTime;
                    System.out.println("Start date: " + startDate);
                    String endDate;
                    Budget budget1;
                    while (true) {
                        System.out.print("Enter end date (in this format: yyyy-MM-dd): ");
                        endDate = scan.next();
                        budget1 = new Budget(currentUser, budget, expenseLimit, startDate, endDate);
                        if (budget1.verifyBudget()) {
                            break;
                        } else {
                            System.out.println("Invalid date. Please enter a valid date (in future).");
                        }
                    }
                    Budgets.add(budget1);
                    System.out.println("Budget set successfully");
                }
                if (choice == 3) {
                    int cnt = 0;
                    for (int i = 0; i < Budgets.size(); i++) {
                        if (Budgets.get(i).userId == currentUser) {
                            System.out.println(++cnt + " - ");
                            Budgets.get(i).displayBudget();
                        }
                    }
                    System.out.println("choose the budget you want to update :");
                    int idx = scan.nextInt();
                    while (idx < 1 || idx > cnt) {
                        System.out.println("Invalid choice");
                        System.out.println("choose the budget you want to update :");
                        idx = scan.nextInt();
                    }
                    cnt = 0;
                    for (int i = 0; i < Budgets.size(); i++) {
                        if (Budgets.get(i).userId == currentUser) {
                            if (++cnt == idx) {
                                Budgets.get(i).updateBudget(i, Budgets);
                                break;
                            }
                        }
                    }
                }
                if (choice == 4) {
                    int cnt = 0;
                    for (int i = 0; i < Budgets.size(); i++) {
                        if (Budgets.get(i).userId == currentUser) {
                            System.out.println(++cnt + " - ");
                            Budgets.get(i).displayBudget();
                        }
                    }
                    System.out.println("choose the income you want to delete :");
                    int idx = scan.nextInt();
                    while (idx < 1 || idx > cnt) {
                        System.out.println("Invalid choice");
                        System.out.println("choose the income you want to delete :");
                        idx = scan.nextInt();
                    }
                    cnt = 0;
                    for (int i = 0; i < Budgets.size(); i++) {
                        if (Budgets.get(i).userId == currentUser) {
                            if (++cnt == idx) {
                                Budgets.remove(i);
                                break;
                            }
                        }
                    }
                }

            }

            else if (choice == 3) {

                System.out.println("Choose an option");
                System.out.println("1. view reminders");
                System.out.println("2. add a reminder");
                System.out.println("3. update a reminder");
                System.out.println("4. delete a reminder");
                System.out.println("5. Back to main menu");
                System.out.print("Enter your choice: ");
                choice = scan.nextInt();
                while (choice < 1 || choice > 5) {
                    System.out.println("Invalid option. Please enter a valid option.");
                    System.out.print("Enter your choice: ");
                    choice = scan.nextInt();
                }
                if (choice != 2 && choice != 5 && Reminders.size() == 0) {
                    System.out.println("No reminders found. Please add a reminder.");
                    continue;
                }
                if (choice == 1) {
                    for (int i = 0; i < Reminders.size(); i++) {
                        if (Reminders.get(i).UserId == currentUser) {
                            Reminders.get(i).viewRemiders();
                        }
                    }
                }
                if (choice == 2) {
                    System.out.print("Enter title: ");
                    String title = scan.next();
                    Reminder reminder;
                    while (true) {
                        System.out.print("Enter date and time (in this format: yyyy-MM-dd HH:mm): ");
                        String date = scan.next();
                        String time = scan.next();
                        reminder = new Reminder(currentUser, title, date, time);
                        if (reminder.validateReminder()) {
                            break;
                        } else {
                            System.out
                                    .println("Invalid date and time. Please enter a valid date and time (in future).");
                        }
                    }
                    Reminders.add(reminder);
                    System.out.println("Reminder set successfully");
                }
                if (choice == 3) {
                    int cnt = 0;
                    for (int i = 0; i < Reminders.size(); i++) {
                        if (Reminders.get(i).UserId == currentUser) {
                            System.out.println(++cnt + " - ");
                            Reminders.get(i).viewRemiders();
                        }
                    }
                    System.out.print("choose the reminder you want to update: ");
                    int idx = scan.nextInt();
                    while (idx < 1 || idx > cnt) {
                        System.out.println("Invalid choice");
                        System.out.print("choose the reminder you want to update: ");
                        idx = scan.nextInt();
                    }
                    cnt = 0;
                    for (int i = 0; i < Reminders.size(); i++) {
                        if (Reminders.get(i).UserId == currentUser) {
                            if (++cnt == idx) {
                                Reminders.get(i).editReminder(i, Reminders);
                                break;
                            }
                        }
                    }
                }
                if (choice == 4) {
                    int cnt = 0;
                    for (int i = 0; i < Reminders.size(); i++) {
                        if (Reminders.get(i).UserId == currentUser) {
                            System.out.println(++cnt + " - ");
                            Reminders.get(i).viewRemiders();
                        }
                    }
                    System.out.print("choose the income you want to delete: ");
                    int idx = scan.nextInt();
                    while (idx < 1 || idx > cnt) {
                        System.out.println("Invalid choice");
                        System.out.print("choose the income you want to delete: ");
                        idx = scan.nextInt();
                    }
                    cnt = 0;
                    for (int i = 0; i < Reminders.size(); i++) {
                        if (Reminders.get(i).UserId == currentUser) {
                            if (++cnt == idx) {
                                Reminders.remove(i);
                                break;
                            }
                        }
                    }
                }

            }

            else if (choice == 4) {
                System.out.println("Choose an option");
                System.out.println("1. view Expenses");
                System.out.println("2. add an expense");
                System.out.println("3. update an expense");
                System.out.println("4. delete an expense");
                System.out.println("5. Back to main menu");
                System.out.print("Enter your choice: ");
                choice = scan.nextInt();
                while (choice < 1 || choice > 5) {
                    System.out.println("Invalid option. Please enter a valid option.");
                    System.out.print("Enter your choice: ");
                    choice = scan.nextInt();
                }
                if (choice != 2 && choice != 5 && Expenses.size() == 0) {
                    System.out.println("No expenses found. Please add an expense.");
                    continue;
                }
                if (choice == 1) {
                    for (int i = 0; i < Expenses.size(); i++) {
                        if (Expenses.get(i).UserId == currentUser) {
                            Expenses.get(i).Display_Expense();
                        }
                    }
                }
                if (choice == 2) {
                    double amount;
                    while (true) {
                        System.out.print("Enter amount: ");
                        amount = scan.nextInt();
                        if (amount > 0) {
                            break;
                        } else {
                            System.out.println("Invalid amount. Amount must be greater than 0.");
                        }
                    }
                    System.out.print("Enter category: ");
                    String category = scan.next();
                    String paymentmethod;
                    while (true) {
                        System.out.print("Enter payment method: ");
                        paymentmethod = scan.next();
                        if (paymentmethod.toLowerCase().equals("cash") || paymentmethod.toLowerCase().equals("fawry")
                                || paymentmethod.toLowerCase().equals("credit card")
                                || paymentmethod.toLowerCase().equals("e-wallet")) {
                            break;
                        } else {
                            System.out.println(
                                    "Invalid payment method. Please enter a valid payment method(Cash, Fawry, Credit Card, e-wallet).");
                        }

                    }

                    String date;
                    Expense expense;
                    while (true) {
                        System.out.print("Enter date (in this format: yyyy-MM-dd): ");
                        date = scan.next();
                        expense = new Expense(currentUser, amount, category, date, paymentmethod);
                        if (expense.validateExpense()) {
                            break;
                        } else {
                            System.out.println("Invalid date. Please enter a valid date (in future).");
                        }
                    }
                    Expenses.add(expense);
                    Expense.save_Expense();
                }
                if (choice == 3) {
                    int cnt = 0;
                    for (int i = 0; i < Expenses.size(); i++) {
                        if (Expenses.get(i).UserId == currentUser) {
                            System.out.println(++cnt + " - ");
                            Expenses.get(i).Display_Expense();
                        }
                    }
                    System.out.print("choose the expense you want to update: ");
                    int idx = scan.nextInt();
                    while (idx < 1 || idx > cnt) {
                        System.out.println("Invalid choice");
                        System.out.print("choose the expense you want to update: ");
                        idx = scan.nextInt();
                    }
                    cnt = 0;
                    for (int i = 0; i < Expenses.size(); i++) {
                        if (Expenses.get(i).UserId == currentUser) {
                            if (++cnt == idx) {
                                Expenses.get(i).Edit_Expense(i, Expenses);
                                break;
                            }
                        }
                    }
                }
                if (choice == 4) {
                    int cnt = 0;
                    for (int i = 0; i < Expenses.size(); i++) {
                        if (Expenses.get(i).UserId == currentUser) {
                            System.out.println(++cnt + " - ");
                            Expenses.get(i).Display_Expense();
                        }
                    }
                    System.out.print("choose the expense you want to delete: ");
                    int idx = scan.nextInt();
                    while (idx < 1 || idx > cnt) {
                        System.out.println("Invalid choice");
                        System.out.print("choose the expense you want to delete: ");
                        idx = scan.nextInt();
                    }
                    cnt = 0;
                    for (int i = 0; i < Expenses.size(); i++) {
                        if (Expenses.get(i).UserId == currentUser) {
                            if (++cnt == idx) {
                                Expenses.remove(i);
                                break;
                            }
                        }
                    }
                }
            }

            else if (choice == 5) {
                break;
            }

            else if (choice == 6) {
                return;
            }
        }
        // ===== WRITE UPDATED DATA =====
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {

            oos.writeObject(new DataContainer(SignUps, Budgets, Reminders, Expenses, Track_Incomes));
            System.out.println("All lists saved successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

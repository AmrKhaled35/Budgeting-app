import java.io.*;
import java.util.*;
import java.util.Vector;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Main {
    static int currentUser = -1 ;
    static boolean registered = false;
    public class Login implements Serializable{


        String email;
        String password;
        public Login(String email, String password) {
            this.email = email;
            this.password = password;
        }
        static boolean verifyCredentials(String username, String password, Vector<SignUp>Signups) {
            for(int i = 0 ; i < Signups.size() ; i++)
            {
                if(username.equals(Signups.get(i).username) && password.equals(Signups.get(i).password))
                {
                    currentUser = Signups.get(i).UserId ;
                    return true;
                }
            }
            return false;
        }
        static void login(String email, String password, Vector<SignUp>Signups) {
            if (verifyCredentials(email, password, Signups)) {
                success();
            } else {
                fail();
            }
        }
        static void success() {
            System.out.println("Login successful");
        }
        static void fail() {
            System.out.println("Login failed: invalid credentials");
        }
    }



    static public class SignUp implements Serializable  {

        int UserId;
        String username;
        String email;
        String password;

        public SignUp(int UserId,String username, String password, String email) {
            this.UserId = UserId;
            this.username = username;
            this.password = password;
            this.email = email;
        }

        static boolean verifyCredentials(String username, String password, String email) {
            return username.length() >= 3 && password.length() >= 8 && email.contains("@") && email.contains(".");
        }

        void register() {
            if (verifyCredentials(this.username, this.password, this.email)) {
                success();
                registered = true;
            } else {
                fail();
            }
        }


        static void success() {
            System.out.println("Registration successful");
        }

        static void fail() {
            System.out.println("Registration failed: invalid credentials");
        }
    }

    static public class TrackIncome implements Serializable {
        int UserId;
        String source;
        double amount;
        String date;

        public TrackIncome(int UserId,String source, double amount, String date) {
            this.UserId = UserId;
            this.source = source;
            this.amount = amount;
            this.date = date;
        }

        static boolean verifyIncome(String source, double amount, String date) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                java.time.LocalDate end = java.time.LocalDate.parse(date, formatter);
                java.time.LocalDate now = java.time.LocalDate.now();
                return end.isAfter(now);
            } catch (Exception e) {
                return false;
            }
        }


        static void saveIncome(String source, double amount, String date) {
            if (verifyIncome(source, amount, date)) {
                System.out.println("Income saved successfully:");
                displayIncome(source, amount, date);
            } else {
                System.out.println("Invalid income details. Income not saved.");
            }
        }

        static void displayIncome(String source, double amount, String date) {
            System.out.println("Source: " + source);
            System.out.println("Amount: " + amount);
            System.out.println("Date: " + date);
        }
    }

    static public class Budget implements Serializable  {

        int userId;
        double totalAmount;
        double expenseLimit;
        String startDate;
        String endDate;

        public Budget(int userId, double totalAmount,double expenseLimit, String startDate, String endDate){
            this.userId = userId;
            this.totalAmount = totalAmount;
            this.expenseLimit = expenseLimit;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        boolean verifyBudget(){
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                java.time.LocalDate end = java.time.LocalDate.parse(endDate, formatter);
                java.time.LocalDate now = java.time.LocalDate.now();
                return end.isAfter(now);
            } catch (Exception e) {
                return false;
            }
        }

    }


    static public class Reminder implements Serializable {

        static int ReminderId;
        int UserId;
        String Title;
        String ReminderDate;
        String ReminderTime;
        boolean notificationSend;

        public Reminder(int UserId, String Title,String reminderDate, String reminderTime){
            this.UserId = UserId;
            this.Title = Title;
            this.ReminderDate = reminderDate;
            this.ReminderTime = reminderTime;
        }

        boolean validateReminder() {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime reminderDateTime = LocalDateTime.parse(ReminderDate + " " + ReminderTime, formatter);
                LocalDateTime now = LocalDateTime.now();
                return reminderDateTime.isAfter(now);
            } catch (Exception e) {
                return false;
            }
        }


        static void viewRemiders(){

        }

        static void editReminder(){

        }

    }



    static public class Expense implements Serializable {
        int UserId;
        double amount;
        String category;
        String date;
        String paymentmethod;

        public Expense(int UserId, double amount, String category, String date, String paymentmethod){
            this.UserId = UserId;
            this.amount = amount;
            this.category = category;
            this.date = date;
            this.paymentmethod = paymentmethod;
        }

        boolean validateExpense(){
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                java.time.LocalDate end = java.time.LocalDate.parse(date, formatter);
                java.time.LocalDate now = java.time.LocalDate.now();
                return end.isAfter(now);
            } catch (Exception e) {
                return false;
            }
        }

        static void save_Expense(){
            System.out.println("Expense saved successfully");
        }
        static void Display_Expense(){

        }

        static void Edit_Expense(){

        }

        static void Track_Expense(){

        }
    }


    static class DataContainer implements Serializable {
        private static final long serialVersionUID = 1L;
        Vector<SignUp> SignUps;
        Vector<Budget> Budgets;
        Vector<Reminder> Reminders;
        Vector<Expense> Expenses;
        Vector<TrackIncome> Track_Incomes;

        public DataContainer(Vector<SignUp> SignUps, Vector<Budget> Budgets, Vector<Reminder> reminders, Vector<Expense> expenses, Vector<TrackIncome> Track_Incomes) {
            this.SignUps = SignUps;
            this.Budgets = Budgets;
            this.Reminders = reminders;
            this.Expenses = expenses;
            this.Track_Incomes = Track_Incomes;
        }
    }

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
                Reminders = loaded.Reminders ;
                Expenses = loaded.Expenses;
                Track_Incomes = loaded.Track_Incomes;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        int choice ;
        Scanner scan = new Scanner(System.in);
        while(currentUser==-1){
            System.out.println("Hello, Sir!");
            System.out.println("1 - Sign up");
            System.out.println("2 - Log in");
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
                if(!registered)
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
        System.out.println("What would you like to do?");
        System.out.println("1. Track income");
        System.out.println("2. Set budget");
        System.out.println("3. Set reminder");
        System.out.println("4. Track expense");
        System.out.print("Enter your choice: ");
        choice = scan.nextInt();
        choice += 2;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
        String formattedDateTime = currentDateTime.format(formatter);
        if(choice == 3){
            System.out.print("Enter source: ");
            String source = scan.next();
            double amount;
            while(true){
                System.out.print("Enter amount: ");
                amount = scan.nextDouble();
                if(amount > 0){
                    break;
                }
                else{
                    System.out.println("Invalid amount. Amount must be greater than 0.");
                }
            }
            String date;
            TrackIncome trackIncome;
            while (true) {
                System.out.print("Enter date (in this format yyyy-MM-dd): ");
                date = scan.next();
                trackIncome = new TrackIncome(currentUser,source, amount, date);
                if(trackIncome.verifyIncome(source, amount, date)){
                    break;
                }
                else{
                    System.out.println("Invalid date. Please enter a valid date in this format: yyyy-MM-dd.");
                }

            }
            Track_Incomes.add(trackIncome);
            trackIncome.saveIncome(source, amount, date);
        }

        if(choice == 4){

            double budget;
            while (true) {
                System.out.print("Enter a budget: ");
                budget = scan.nextInt();
                if(budget > 0){
                    break;
                }
                else{
                    System.out.println("Invalid budget. Please enter a valid budget.");
                }
            }
            double expenseLimit;
            while(true){
                System.out.print("Enter expense limit: ");
                expenseLimit = scan.nextInt();
                if(expenseLimit > 0 && expenseLimit <= budget){
                    break;
                }
                else{
                    System.out.println("Invalid expense limit. expense limit must be greater than 0 and less than or equal to budget.");
                }
            }
            String startDate = formattedDateTime;
            System.out.println("Start date: " + startDate);
            String endDate;
            Budget budget1;
            while (true) {
                System.out.print("Enter end date (in this format: yyyy-MM-dd): ");
                endDate = scan.next();
                budget1 = new Budget(currentUser,budget, expenseLimit, startDate, endDate);
                if(budget1.verifyBudget()){
                    break;
                }
                else{
                    System.out.println("Invalid date. Please enter a valid date (in future).");
                }
            }
            Budgets.add(budget1);
            System.out.println("Budget set successfully");

        }

        if(choice == 5){
            System.out.print("Enter title: ");
            String title = scan.next();
            Reminder reminder;
            while (true) {
                System.out.print("Enter date and time (in this format: yyyy-MM-dd HH:mm): ");
                String date = scan.next();
                String time = scan.next();
                reminder = new Reminder(currentUser,title, date, time);
                if(reminder.validateReminder()){
                    break;
                }
                else{
                    System.out.println("Invalid date and time. Please enter a valid date and time (in future).");
                }
            }
            Reminders.add(reminder) ;
            System.out.println("Reminder set successfully");

        }

        if(choice == 6){
            double amount;
            while(true){
                System.out.print("Enter amount: ");
                amount = scan.nextInt();
                if(amount > 0){
                    break;
                }
                else{
                    System.out.println("Invalid amount. Amount must be greater than 0.");
                }
            }
            System.out.print("Enter category: ");
            String category = scan.next();
            String paymentmethod;
            while (true) {
                System.out.print("Enter payment method: ");
                paymentmethod = scan.next();
                if(paymentmethod.toLowerCase().equals("cash") || paymentmethod.toLowerCase().equals("fawry")
                        || paymentmethod.toLowerCase().equals("credit card")
                        || paymentmethod.toLowerCase().equals("e-wallet")){
                    break;
                }
                else{
                    System.out.println("Invalid payment method. Please enter a valid payment method(Cash, Fawry, Credit Card, e-wallet).");
                }

            }

            String date;
            Expense expense;
            while(true){
                System.out.print("Enter date (in this format: yyyy-MM-dd): ");
                date = scan.next();
                expense = new Expense(currentUser, amount, category, date, paymentmethod);
                if(expense.validateExpense()){
                    break;
                }
                else{
                    System.out.println("Invalid date. Please enter a valid date (in future).");
                }
            }
            Expenses.add(expense) ;
            Expense.save_Expense();
        }

        // ===== WRITE UPDATED DATA =====
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {

            oos.writeObject(new DataContainer(SignUps, Budgets, Reminders, Expenses, Track_Incomes));
            System.out.println("All lists saved successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ===== READ BACK TO VERIFY =====
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {

            DataContainer loaded = (DataContainer) ois.readObject();

            System.out.println("\nLoaded SignUps:");
            loaded.SignUps.forEach(System.out::println);

            System.out.println("\nLoaded Budgets:");
            loaded.Budgets.forEach(System.out::println);

            System.out.println("\nLoaded Reminders:");
            loaded.Reminders.forEach(System.out::println);

            System.out.println("\nLoaded Expenses:");
            loaded.Expenses.forEach(System.out::println);

            System.out.println("\nLoaded Track_Incomes:");
            loaded.Track_Incomes.forEach(System.out::println);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}



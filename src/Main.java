import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {


    public class Budget {

        int userId;
        double expenseLimit;
        String startDate;
        String endDate;

        static void setBudget(int totalAmount, int expenseLimit, String startDate, String endDate){

        }

        static void verfiyBudget(){

        }

    }


    public class Reminder{

        static int ReminderId;
        int UserId;
        String Title;
        String ReminderDate;
        String ReminderTime;
        boolean notificationSend;

        void setReminder(int userId, String reminderDate, String reminderTime){
            UserId = userId;
            ReminderDate = reminderDate;
            ReminderTime = reminderTime;
            Title = "Reminder";

        }

        void validateReminder(){
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Format it as a string (optional)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

        }

        static void viewRemiders(){

        }

        static void editReminder(){

        }

    }



    public class Expense{
        int amount;
        String category;
        String date;
        String paymentmethod;

        static void save_Expense(){

        }
        static void Display_Expense(){

        }

        static void Edit_Expense(){    

        }

        static void Track_Expense(){
            
        }
    }

   public static void main(String[] args) {
        
        System.out.println("Hello, Sir!");
        System.out.println("What would you like to do?");
        System.out.println("1. Set Budget");
        System.out.println("2. Verify Budget");
        System.out.println("3. Set Reminder");
        System.out.println("4. View Reminders");
        System.out.println("5. Edit Reminder");
        System.out.println("6. Save Expense");
        System.out.println("7. Display Expense");
        System.out.println("8. Edit Expense");
        System.out.println("9. Track Expense");
        System.out.print("Enter your choice: ");
        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        if(choice == 1){

        }

    }
}
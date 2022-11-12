package restaurant;

import people.Customer;
import people.Person;
import people.Staff;
import reservation.Reservation;
import till.Login;
import till.Product;
import till.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Driver {
    private Scanner in;
    private static Restaurant restaurant;
    
    public void menuForDriver() {
        in = new Scanner(System.in);
    }
    
    public void run() throws FileNotFoundException {
        // This updates the retaurant object
        bootUp();
        
        boolean more = true;
        
        while (more) {
            System.out.println("L)ogin  S)ign up  Q)uit");
            String command = in.nextLine().toUpperCase();
            
            
            //If login
            
            if (command.equals("L")) {
                System.out.println("Enter User ID");
                String id = in.nextLine();
                System.out.println("Enter Password : ");
                String password = in.nextLine();
                
                if (!restaurant.getLogin(id, password)) {
                    System.out.println("Invalid credentials");
                } else {
                    // Once logged in, allow the person to have a access to certain options based on their level of access
                    loginSuccesful(id);
                }
                
                
            } else if (command.equals("S")) {
                // Signs up the person, create new person object and add it to the arraylist of people.
                signUp();
                
            } else if (command.equals("Q")) {
                restaurant.save();
                System.out.println("Shutting Down");
                System.exit(0);
            }
        }
    }
    public static void bootUp() throws FileNotFoundException {
        CSVReader resFile = new CSVReader(new File("src/data/reservations.csv"));
        CSVReader tablesFile = new CSVReader(new File("src/data/tables.csv"));
        CSVReader staffFile = new CSVReader(new File("src/data/people.csv"));
        CSVReader productsFile = new CSVReader(new File("src/data/products.csv"));
        
        ArrayList<Table> tables = new ArrayList<>();
        tablesFile.getValues().forEach(line -> {
            tables.add(new Table(Integer.parseInt(line[0]), Integer.parseInt(line[1])));
        });
        
        ArrayList<Reservation> res = new ArrayList<>();
        resFile.getValues().forEach(line -> {
            String[] table = tablesFile.get(line[0], "tableNumber").split(",");
            res.add(new Reservation(
                new Table(Integer.parseInt(table[0]), Integer.parseInt(table[1])), 
                LocalDateTime.parse(line[1], DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm")),
                Duration.between(LocalTime.MIN, LocalTime.parse(line[2]))
            ));
        });
        
        ArrayList<Staff> staff = new ArrayList<>();
        staffFile.getValues().forEach(line -> {
            int id = Character.getNumericValue(line[3].charAt(0));
            if (id > 1 && id != 9)
                staff.add(new Staff(line[0], line[1], line[2], line[3]));
        });

        ArrayList<Product> products = new ArrayList<>();
        productsFile.getValues().forEach(line -> {
            ArrayList<String> alergies = new ArrayList<>(Arrays.asList(line[3].split(";")));
            try {
                products.add(new Product(line[0], line[1], Double.parseDouble(line[2]), alergies));
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }
        });
        
        restaurant = new Restaurant(res, tables, staff, products);
    }
    
    private Object getChoice(ArrayList<Object> choices) {
        if (choices.size() == 0) return null;
        while (true) {
            char c = 'A';
            for (Object choice : choices) {
                System.out.println(c + ") " + choice);
                c++;
            }
            String input = in.nextLine();
            int n = input.toUpperCase().charAt(0) - 'A';
            if (0 <= n && n < choices.size())
                return choices.get(n);
        }
    }
    
    public void loginSuccesful(String id) {
        int integer = Character.getNumericValue(id.charAt(0));
    }
    
    public void signUp() {
        System.out.println("Enter full name");
        String name = in.nextLine();
        System.out.println("Enter Email");
        String email = in.nextLine().toLowerCase();
        System.out.println("Enter Phone Number");
        String phoneNumber = in.nextLine();
        Customer bob = new Customer(name,phoneNumber,email,"1",0);
        
        restaurant.addPerson(bob);
        System.out.println("Your User ID : ");
        System.out.println(bob.getId());
        System.out.println("Enter A New Password : ");
        String password = in.nextLine();
        
        restaurant.addLogins();
        System.out.println("Sign Up Complete");
        
    }
    
    public void loginOwner() {
        // Owner has access: create and delete table, view profit, add staff, add and remove order. pay
        
    }

    public void loginStaff(){
        // Staff will have access to: create and remove table, add and remove order, make and cancel reservation, pay

    }

    public void loginCustomer(){
        // Customer: Make and cancel reservation, pay

    }

    /*
     * Reservation option will give option to make booking and delete
     * It is literally copy and paste of the Appointment work from last lab
     *
     * Create and remove table is kind of like view, remove and add products
     *
     * Add and remove order is the same, it will be applied on top ofa table
     *
     * Pay wraps it up
     *
     * You need to make a method that handles each of these
     *
     */

    @Override
    public String toString() {
        return "AppointmentMenu{" +
        "in=" + in +
        '}';
    }
}


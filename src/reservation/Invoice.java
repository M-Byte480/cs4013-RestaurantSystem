package reservation;

import people.*;
import till.*;
import restaurant.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Invoice {
    private Reservation reservation;
    private Customer customer;
    private ArrayList<Product> products;
    private double total;
    private int id;
    private static int uniqueID;

    /**
     * Creates an invoice Object
     *
     * @param reservation
     * @throws IOException
     */
    public Invoice(Reservation reservation) throws IOException {
        this.reservation = reservation;                     // collects details of reservation
        this.customer = reservation.getCust();                               // contains details of the person who booked
        this.products = reservation.getTable().getProducts();   // gets an arrayList of all the products on the table
        this.total = reservation.getTable().getTotal();
        this.id = uniqueID++;
        sendInvoice();
    }
    public Invoice(Reservation reservation, Customer customer, ArrayList<Product> products, double total, int id) {
        this.reservation = reservation;
        this.customer = customer;
        this.products = products;
        this.total = total;
        this.id = id;
    }

    /**
     * Sets the uniqueID state
     * @param uniqueID
     */
    public static void setUniqueID(int uniqueID) {
        Invoice.uniqueID = uniqueID;
    }

    /**
     * Gets the ID state
     * @return uniqueID current state
     */
    public static int getUniqueID() {
        return uniqueID;
    }

    public double getTotal() {
        return total;
    }

    /**
     * Gets the latest uniqueID in the file and then it sets the uniqueID state from last usage
     * @throws IOException
     */
    public static void getLatestID() throws IOException {
        if(getUniqueID() == 0){
            setUniqueID(1);
            return;
        }
        CSVReader invoiceReader = new CSVReader(new File("/src/data/invoices.csv"));
        String[] allID = invoiceReader.getAllArray("id");
        setUniqueID(Integer.parseInt(allID[allID.length - 1]));
        invoiceReader.close();
    }
    public String[] customerDetailsToStringArr () {
        // name, phoneNumber, timeOfBooking, TimeOfSending, products
        ArrayList<String> invoice = new ArrayList<>();
        invoice.add(customer.getName());
        invoice.add(customer.getPhoneNumber());
        invoice.add(reservation.getTime().toString());
        invoice.add(CSVReader.getTimeNow());
        invoice.add(reservation.getProducts().toString());
        String[] custDetails = new String[4];
        for (int i = 0; i < custDetails.length; i++) {
            custDetails[i] = invoice.get(i);
        }
        return custDetails;
    }

    /**
     * Adds the invoice to invoice.csv file using the structure:
     * name, address, contactDetail, reservationTime, products, netTotal
     * @throws IOException
     */
    public void sendInvoice() throws IOException {
        CSVReader writeToLog = new CSVReader(new File("src/data/log.csv"));           // Create a writer to logs
        CSVReader writeToInvoices = new CSVReader(new File("src/data/invoices.csv")); // Create a writer to invoices

        // Write to the files
        writeToLog.addDataToFile(new String[] {CSVReader.getTimeNow(), reservation.getTable().getStaff(), "Sent away invoice"});
        writeToInvoices.addDataToFile(new String[]{customer.getName(), customer.getEmail(), reservation.getTime().toString(), reservation.getTable().toString(), String.valueOf(total), String.valueOf(getUniqueID())});

        // End the utils
        writeToInvoices.close();
        writeToLog.close();
    }

    /**
     * Formats the CSV file into a readable state in terminal
     * @return
     */
    public String format(){
        ArrayList<LineItem> items = new ArrayList<>();
        StringBuilder toReturn = new StringBuilder();
        toReturn.append(customer.getEmail()).append("\n")
                .append(customer).append("\n");

        for (LineItem l : Table.convertToLineItems(products)) {
            toReturn.append(l.toString()).append("=".repeat(48));
        }

        return toReturn.toString();
    }
    public String toString() {
        StringBuilder prodString = new StringBuilder();
        for (Product product : products) {
            prodString.append(product.getName()).append(";");
        }
        prodString.deleteCharAt(prodString.length()-1);

        return String.format("%s,%s,%s,%s,%s", 
            customer.toString().replace(",", ";"),
            reservation.toString().replace(",", ";"),
            prodString,
            total,
            id
        );
    }
}

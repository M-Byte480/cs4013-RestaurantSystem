package restaurant;

import reservation.Invoice;
import reservation.Reservation;
import till.Product;
import till.Table;
import people.Person;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Restaurant extends Yum {
    private double profit;
    private ArrayList<Reservation> reservations;
    private ArrayList<Table> tables;
    private HashMap<String, Person> people;
    private ArrayList<Product> products;
    private ArrayList<Invoice> invoices;

    public Restaurant(ArrayList<Reservation> reservations, ArrayList<Table> tables, HashMap<String, Person> people, ArrayList<Product> products, ArrayList<Invoice> invoices) {
        this.reservations = reservations;
        this.tables = tables;
        this.people = people;
        this.products = products;
        this.invoices = invoices;
    }
    
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
    public boolean addReservation(Reservation reservation) {
        boolean isBooked = false;
        for (Reservation res : reservations) {
            if (res.overlaps(reservation)) isBooked = true;
        }
        
        if (isBooked) return false;
        this.reservations.add(reservation);
        return true;
    }
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    public void addProduct(Product prod) {
        products.add(prod);
    }
    public void removeProduct(Product prod) {
        products.remove(prod);
    }

    public ArrayList<Table> getTables() {
        return tables;
    }
    public void addTable(Table table) {
        tables.add(table);
    }
    public void removeTable(Table table) {
        // make sure table doesnt have a reservation
        tables.remove(table);
    }

    public Collection<Person> getPeople() {
        return people.values();
    }
    public void addPerson(Person person) {
        people.put(person.getId(), person);
    }
    public Person getPerson(String id) {
        return people.get(id);
    }
    public void removePerson(Person person) {
        people.remove(person.getId());
    }

    public double getProfit() {
        return profit;
    }
    public void addProfit(double profit) {
        this.profit += profit;
    }
    
    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }
    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }
    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }
    public void removeInvoice(Invoice invoice) {
        invoices.remove(invoice);
    }

    public ArrayList<Table> getTablesBetweenTime(LocalDateTime timeStart, LocalDateTime timeEnd) {
        ArrayList<Table> tempTables = new ArrayList<>();
        for (Reservation res : reservations) {
            if ((res.getTime().isAfter(timeStart)) && (res.getTime().isBefore(timeEnd))) continue;
            else if ((res.getLength().isAfter(timeStart)) && (res.getLength().isBefore(timeEnd))) continue;
            tempTables.add(res.getTable());
        }
        return tempTables;
    }
    public double getProfitBetweenTime(LocalDateTime timeStart, LocalDateTime timeEnd) {
        double profit = 0;
        for (Invoice invoice : invoices) {
            
        }
        return profit;
    }

    public void save() {
        CSVReader resFile = new CSVReader(new File("src/data/reservations.csv"), false);
        CSVReader tablesFile = new CSVReader(new File("src/data/tables.csv"), false);
        CSVReader staffFile = new CSVReader(new File("src/data/people.csv"), false);
        CSVReader productsFile = new CSVReader(new File("src/data/products.csv"), false);
        CSVReader invoiceFile = new CSVReader(new File("src/data/invoices.csv"), false);

        reservations.forEach(res -> {
            resFile.addDataToSystem(res.toString());
        });
        tables.forEach(table -> {
            tablesFile.addDataToSystem(table.toString());
        });
        people.forEach((id, staff) -> {
            staffFile.addDataToSystem(staff.toString());
        });
        products.forEach(prod -> {
            productsFile.addDataToSystem(prod.toString());
        });
        invoices.forEach(invoice -> {
            invoiceFile.addDataToSystem(invoice.toString());
        });

        resFile.saveToCSV();
        tablesFile.saveToCSV();
        staffFile.saveToCSV();
        productsFile.saveToCSV();
        invoiceFile.saveToCSV();
    }

}

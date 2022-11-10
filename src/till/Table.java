package till;

import reservation.Invoice;
import reservation.LineItem;
import reservation.Reservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Table {
    private int tableNumber;
    private int seats;
    private Reservation booking;
    private ArrayList<Product> productsOnTable;
    private Till till;

    public Table(int tableNumber, int seats) {
        this.tableNumber = tableNumber;
        this.seats = seats;
    }



    //adding product to "order"
    public void addProduct(Table x, Product pick) {

        productsOnTable.add(pick);

    }

    //deleting product from "order"
    public void deleteProduct(Product delete) {

        productsOnTable.add(delete);
    }

    // Converts table to lineItem arrayList for invoices
    // Milan
    public static ArrayList<LineItem> convertToLineItems(ArrayList<Product> products){



    public double getTotal(){
        double sum = 0;
        for (Product p : productsOnTable) {
            sum += p.getCost();

        }
        return sum ;
    }

    public void closeTable() {
        this.booking = null;

    }

    //closing/deleting booking
    //check if table is vacant
    public void deleteTable(Table table) {
        productsOnTable.remove(table);

    }

    public ArrayList<Product> getProducts() {
        return productsOnTable;
    }

    public String getStaff() {
        return getStaff();
    }
    public static ArrayList<LineItem> convertToLineItems(ArrayList<Product> products) {
        ArrayList<LineItem> items = new ArrayList<>();
        HashMap<Product, Integer> occurences = new HashMap<>();
        Integer count = 0;
        for (Product p : products) {
            count = occurences.get(p);
            if(count == null){
                occurences.put(p, 1);
            }else{
                occurences.replace(p, count + 1);
            count = occurences.get(p.getName());
            if (count == null) {
                occurences.put(p, 1);
            } else {
                occurences.replace(p, count + 1);
            }
        }

        for (HashMap.Entry<Product, Integer> item :
                occurences.entrySet()) {
            items.add(new LineItem(item.getKey().getName(), item.getValue(), item.getKey().getCost()));
        }

        return items;
    }



    public void closeTable() throws IOException {
        Invoice invoice = new Invoice(this.booking);
        invoice.sendInvoice();
        this.booking = null;
        this.productsOnTable = null;
    }

    //closing/deleting booking
    //check if table is vacant
    public void deleteTable(Table table) {
        productsOnTable.remove(table);

    public String returnForawrdSlaah (){
        for (Product p : productsOnTable) {
            St
            p.toString();
        }
    }

    public void getMenu(Table t) throws IOException {
        Menu menu = new Menu();
        menu.run(t);
    }

    public ArrayList<Product> getProducts() {
        return productsOnTable;
    }
}

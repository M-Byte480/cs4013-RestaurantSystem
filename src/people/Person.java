package people;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Person {
    private String address;
    private String name;
    private String phoneNumber;
    private String email;
    private String id;
    private static ArrayList<Person> people;
    private static int uniqueID;


    public Person(String name, String address, String phoneNumber, String email, String id){
        this.address = address;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = uniqueIdGenerator(id);
    }

    public String uniqueIdGenerator(String id){
        uniqueID++;
        this.id = id + String.format("%07d", uniqueID);
        return this.id;
    }

    public void addPerson(Person person){
        people.add(person);
    }
    public void removePerson(Person person){
        people.remove(person);
    }

    public void getGroupPeople(String idIndicator){

    }

    public Person getPerson(String id){
        for (Person person :
                people ) {
            if(person.id.equals(id)){
                return person;
            }
        }
        return null;
    }

    @Override
    public String toString() {
         return "Person: \n" +
                 "name = '" + name + "'\n" +
                "address = '" + address + "'\n" +
                "phoneNumber = '" + phoneNumber + "'\n" +
                "email = '" + email + "'\n";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static ArrayList<Person> getPeople() {
        return people;
    }

    public static void setPeople(ArrayList<Person> people) {
        Person.people = people;
    }

    public static int getUniqueID() {
        return uniqueID;
    }

    public static void setUniqueID(int uniqueID) {
        Person.uniqueID = uniqueID;
    }
}

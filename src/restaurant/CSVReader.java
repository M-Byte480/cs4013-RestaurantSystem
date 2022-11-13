// Milan: Last edit 28/10

package restaurant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CSVReader {
    private File file;
    private Scanner scanner;
    private ArrayList<String[]> values;
    private String[] dataFields;
    /**
     * Create a Utility object, by passing the File name or path
     *
     * @param file File you want to read from
     * @throws FileNotFoundException Throws error if its not found
     */
    public CSVReader(File file, boolean toRead) throws FileNotFoundException {
        scanner = new Scanner(file);
        dataFields = scanner.nextLine().split(",");
        this.file = file;
        this.values = new ArrayList<>();
        if(toRead) read();
    }



    /**
     * Read file into memory
     *
     * @throws FileNotFoundException
     */
    private void read() throws FileNotFoundException {
        while (scanner.hasNextLine()) {
            values.add(scanner.nextLine().split(","));
        }
    }

    /**
     * Once you done adding data, you must save to file
     *
     * @throws IOException
     */
    public void save() throws IOException {
        FileWriter fileWriter = new FileWriter(this.file, false);
        StringBuilder toFile = new StringBuilder();

        for (String[] line : values) {
            toFile.append(String.join(",", line)).append("\n");
        }

        fileWriter.write(toFile.toString());
        fileWriter.close();
    }

    /**
     * Add a String array, where each element matches the data fields
     *
     * @param data
     */
    public void addData(String[] data) {
        this.values.add(data);
    }

    /**
     * Add a comma seperated String
     *
     * @param data
     */
    public void addData(String data) {
        addData(data.split(","));
    }

    /**
     * Write directly to the file. this does not require the read() in method.
     *
     * @param data
     * @throws IOException
     */
    public void addDataToFile(String[] data) throws IOException {
        addDataToFile(String.join(",", data));
    }

    public void addDataToFile(String data) throws IOException {
        FileWriter fileWriter = new FileWriter(this.file, true);
        fileWriter.write(data + '\n');
        fileWriter.close();
    }

    /**
     * Close restaurant.Util
     */
    public void close() {
        this.scanner.close();
    }

    /**
     * It requires to read in the file before you can edit it.
     *
     * @param dataField
     * @param value
     * @throws IOException
     */
    public void remove(String dataField, String value) {
        String[] dataFieldValues = values.get(0).clone();
        int index = -1;
        for (int i = 0; i < dataFieldValues.length; i++) {
            if (dataField.equals(dataFieldValues[i])) {
                index = i;
            }
        }
        if (index == -1) {
            System.out.println("There was no dataField found");
            return;
        }

        for (String[] line :
                values) {
            if (line[index].equals(value)) {
                values.remove(line);
            }
        }
    }

    /**
     * Get every line where dataField = value;
     * @param value
     * @param dataField
     * @return
     */
    public String get(String value, String dataField) {
        StringBuilder dataLinesString = new StringBuilder();
        String[] dataFieldValues = this.dataFields;

        int index = -1;

        for (int i = 0; i < dataFieldValues.length; i++) {
            if (dataFieldValues[i].equals(dataField)) {
                index = i;
            }
        }

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i)[index].equals(value)) {
                dataLinesString.append(String.join(",", values.get(i))).append('\n');
            }
        }

        return dataLinesString.toString();
    }

    /**
     * Returns a combination of corresponding parallel arrays of Values to DataFieldName
     * @param fields
     * @param dataFields
     * @return
     */
    public String getCombinations(String[] fields, String[] dataFields){
        int size = dataFields.length;

        if(size != fields.length){
            return null;
        }

        String[] dataFieldValues = values.get(0).clone();
        int[] indexes = new int[size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < values.get(0).length; j++) {
                if (dataFields[i].equals(dataFieldValues[j])){
                    indexes[i] = j;
                }
            }
        }
        StringBuilder everything = new StringBuilder();
        boolean incorrect = true;
        for (int line = 1; line < values.size(); line++) {
            String[] temp = values.get(line);

            for (int element : indexes) {
                if(!temp[element].equals(fields[element])){
                    incorrect = false;
                }
            }


            if(incorrect){
                everything.append(line).append(") ");
                everything.append(String.join(", ", values.get(line)));
                everything.append("\n");
            }
            incorrect = true;
        }

        return everything.toString();
    }

    /**
     * Returns a string of every line of the data Field with its corresponding line number
     * @param dataField
     * @return
     */


    public String[] getAllValues(String dataField) {
        ArrayList<String> everything = new ArrayList<>();
        String[] dataFieldValues = values.get(0).clone();
        int index = -1;
        for (int i = 0; i < dataFieldValues.length; i++) {
            if (dataFieldValues[i].equals(dataField)) {
                index = i;
            }
        }
        if (index == -1) {
            System.out.println("Failed to find the Data Field");
            return null;
        }
        for (int i = 1; i < values.size() - 1; i++) {
            everything.add(values.get(i)[index]);
        }

        return everything.toArray(new String[0]);
    }

    /**
     * Counts the number of Instances of the value existing in certain datafield in the csv
     * @param value to check
     * @param datafield where the value exists
     * @return int with respect to the number of values existing in the dataset
     */
    public int count(String value, String datafield){
        return get(value, datafield).split(value).length - 1;
    }


    public static String getTimeNow(){
        return (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date());
    }

    public ArrayList<String[]> getValues() {
        return values;
    }

}

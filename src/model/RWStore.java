package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RWStore implements IReadWriteFiles {

    private final String fileName;

    public RWStore(String fileName) {
        this.fileName = fileName;
    }

    public List<Toy> readItems() throws IOException {
        List<Toy> toys = new ArrayList<>();

        BufferedReader csvReader = new BufferedReader(new FileReader(this.fileName));
        String csvLine;
        while ((csvLine = csvReader.readLine()) != null) {
            String[] data = csvLine.split(", ");
            toys.add(new Toy(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2])));
        }
        csvReader.close();

        return toys;
    }

    @Override
    public void saveItems(List<?> list) throws IOException {
        List<Toy> listToWrite = new ArrayList<>();

        if (list instanceof ArrayList<?>) {
            for (Object obj : list){
                listToWrite.add((Toy) obj);
            }
        }

        FileWriter csvWriter = new FileWriter(this.fileName);

        for (Toy toy : listToWrite) {
            csvWriter.append(Integer.valueOf(toy.getId()).toString() + ", ");
            csvWriter.append(toy.getName() + ", ");
            csvWriter.append(Integer.valueOf(toy.getQuantity()).toString());
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }
}

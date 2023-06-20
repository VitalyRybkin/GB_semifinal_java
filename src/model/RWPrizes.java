package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RWPrizes implements IReadWriteFiles {

    private final String fileName;

    public RWPrizes(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Prize> readItems() throws IOException {
        List<Prize> prizes = new ArrayList<>();

        BufferedReader csvReader = new BufferedReader(new FileReader(this.fileName));
        String csvLine;
        while ((csvLine = csvReader.readLine()) != null) {
            String[] data = csvLine.split(", ");
            prizes.add(new Prize(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3])));
        }
        csvReader.close();

        return prizes;
    }

    @Override
    public void saveItems(List<?> list) throws IOException {
        List<Prize> listToWrite = new ArrayList<>();

        if (list instanceof ArrayList<?>) {
            for (Object obj : list){
                listToWrite.add((Prize) obj);
            }
        }

        FileWriter csvWriter = new FileWriter(this.fileName);

        for (Prize prize : listToWrite) {
            csvWriter.append(Integer.valueOf(prize.getId()).toString() + ", ");
            csvWriter.append(prize.getName() + ", ");
            csvWriter.append(prize.getQuantity() + ", ");
            csvWriter.append(Integer.valueOf(prize.getWeight()).toString());
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }


}

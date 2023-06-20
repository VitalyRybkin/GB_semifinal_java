package view;

import controller.UserController;
import model.Prize;
import model.Toy;

import java.io.IOException;
import java.util.*;

public class Store {

    private final UserController userController;

    public Store(UserController userController) {
        this.userController = userController;
    }


    public void runStore() throws IOException {

        while (true) {
            System.out.print("Type command for store ");
            System.out.print(java.util.Arrays.asList(StoreCommands.values()));
            System.out.print(": ");

            Scanner scan = new Scanner(System.in);
            String userChoice = scan.next().strip();

            switch (userChoice.toLowerCase()) {
                case "add" -> createStoreItem(this.userController.getStoreList());
                case "print" -> userController.printStore();
                case "delete" -> deleteStoreItem(scan, this.userController.getStoreList());
                case "draw" -> prizeDraw();
                case "prizes" -> {
                    PrizeSettings prizeSettings = new PrizeSettings(this.userController);
                    prizeSettings.runPrizeSettings();
                }
                case "exit" -> {
                    this.userController.saveStoreList();
                    System.exit(0);
                }
                default -> System.out.println("Not a command!");
            }
        }
    }

    private void prizeDraw() throws IOException {
        LinkedHashMap<Integer, Integer> weightsDict = new LinkedHashMap<>();

        int weightsSum = 0;
        for (Prize item : userController.getPrizeList()) {
            weightsSum += item.getWeight();
            weightsDict.put(item.getId(), weightsSum);
        }

        double random = Math.random() * weightsSum;
        int countWeight = 0;
        int prizeID = 0;
        for (Map.Entry<Integer, Integer> entry : weightsDict.entrySet()) {
            countWeight += entry.getValue();
            if (countWeight >= random){
                prizeID = entry.getKey();
                break;
            }
        }

        for (Prize item : userController.getPrizeList()) {
            if (prizeID == item.getId()) {
                System.out.println("Your prize: ");
                System.out.println(item + "\n");
                userController.deletePrizeItem(item);
                userController.savePrizeList();
                userController.setQuantity(item);
                break;
            }
        }
    }

    private void deleteStoreItem(Scanner scan, List<Toy> storeList) throws IOException {
        userController.printStore();
        int ID;
        while (true) {
            boolean IDisInList = false;
            System.out.print("Choose an ID to delete item: ");
            String userChoice = scan.next();
            try {
                ID = Integer.parseInt(userChoice);
                for (Toy toy : storeList) {
                    if (toy.getId() == ID) {
                        IDisInList = true;
                        break;
                    }
                }
                if (!IDisInList) {
                    System.out.println("Toy is not in list! Try again!");
                }
                else break;

            } catch (NumberFormatException ex) {
                System.out.println("Wrong input! Try again!");
            }
        }

        for (Toy toy : storeList) {
            if (ID == toy.getId()) {
                userController.deleteToyItem(toy);
                break;
            }
        }

        for (Prize prize : userController.getPrizeList()) {
            if (ID == prize.getId()) {
                userController.deletePrizeItem(prize);
                userController.savePrizeList();
                break;
            }
        }
    }

    private void createStoreItem(List<Toy> storeList) {
        int id = storeList.get(storeList.size() - 1).getId();
        String name = prompt("Add name: ");
        boolean toyInList = false;
        for (Toy toy: storeList) {
            if (toy.getName().equals(name)){
                toyInList = true;
                break;
            }
        }
        if (toyInList) System.out.println("This toy is already in toys list!");
        else {
            String qty = prompt("Add quantity: ");
            userController.setStoreList(new Toy(id + 1, name, Integer.parseInt(qty)));
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

}

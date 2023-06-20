package view;

import controller.UserController;
import model.Prize;
import model.Toy;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PrizeSettings {

    private final UserController userController;

    public PrizeSettings(UserController userController) {
        this.userController = userController;
    }

    private static int getUsersChoice(Scanner scan, HashMap<Integer, Prize> output_dict) {
        int menuItem;
        while (true) {
            System.out.print("Choose a prize ('exit' - for exit): ");
            String prize_num = scan.next();
            if (prize_num.equalsIgnoreCase("exit")) {
                menuItem = 0;
                break;
            }

            try {
                menuItem = Integer.parseInt(prize_num);
                if (output_dict.get(menuItem) == null) {
                    System.out.println("Out of prizes list! Try again!");
                } else break;
            } catch (NumberFormatException ex) {
                System.out.println("Wrong input! Try again!");
            }
        }
        return menuItem;
    }

    void runPrizeSettings() throws IOException {
        Scanner scan = new Scanner(System.in);
        int usersChoice;
        while (true) {
            HashMap<Integer, Prize> foundItemsDict;
            System.out.print("Type command for prizes list ");
            System.out.print(Arrays.asList(PrizesCommands.values()));
            System.out.print(": ");

            String user_choice = scan.next().strip();

            switch (user_choice.toLowerCase()) {
                case "add" -> this.addPrizeItem(scan);
                case "print" -> this.printPrizes();
                case "delete" -> {
                    foundItemsDict = this.searchForItems(scan);
                    if (foundItemsDict == null) break;
                    usersChoice = getUsersChoice(scan, foundItemsDict);
                    if (usersChoice == 0) break;
                    Prize prizeToRemove = foundItemsDict.get(usersChoice);

                    this.userController.deletePrizeItem(prizeToRemove);
                }
                case "freq" -> {
                    foundItemsDict = searchForItems(scan);
                    if (foundItemsDict == null) break;
                    usersChoice = getUsersChoice(scan, foundItemsDict);
                    if (usersChoice == 0) break;
                    Prize new_prize_freq = foundItemsDict.get(usersChoice);

                    System.out.print("Enter new frequency for '" + new_prize_freq.getName() + "': ");
                    int new_freq = scan.nextInt();
                    new_prize_freq.setWeight(new_freq);
                }
                case "exit" -> {
                    this.userController.savePrizeList();
                    return;
                }
                default -> System.out.println("Not a command!");
            }
        }
    }

    private void addPrizeItem(Scanner scan) {
        boolean toyInList = false;
        while (true) {
            for (Toy toy : this.userController.getStoreList()) {
                System.out.println(toy);
            }

            System.out.print("Choose toy ID to add to prizes list: ");
            int toyID = scan.nextInt();
            System.out.print("Add weight for draw: ");
            int weight_to_add = scan.nextInt();

            Prize toyToAdd = null;
            for (Toy toy : this.userController.getStoreList()) {
                if (toy.getId() == toyID) {
                    toyToAdd = new Prize(toy.getId(), toy.getName(), toy.getQuantity(), weight_to_add);
                    break;
                }
            }

            if (toyToAdd == null) {
                System.out.println("Error on adding toy - no such toy in store! Try again!");
            } else {
                for (Prize prize : this.userController.getPrizeList()) {
                    if (prize.getId() == toyToAdd.getId()) {
                        toyInList = true;
                        break;
                    }
                }
                if (toyInList) {
                    System.out.println("This toy is already in prize list!");
                } else {
                    this.userController.setPrizeList(toyToAdd);
                    System.out.println("New toy " + toyToAdd.getName() + " added to prizes list:");
                    this.printPrizes();
                }
                break;
            }
        }
    }

    private HashMap<Integer, Prize> searchForItems(Scanner scan) {
        HashMap<Integer, Prize> output_dict = new HashMap<>();

        System.out.print("Type the name of prize (toy) or a text prizename contains ('exit' - for exit): ");
        String prize_containing_text = scan.next().strip();


        if (prize_containing_text.equalsIgnoreCase("exit")) return null;

        int menu_item = 1;
        for (Prize prize : this.userController.getPrizeList()) {
            if (prize.getName().contains(prize_containing_text)) {
                output_dict.put(menu_item, prize);
                menu_item++;
            }
        }

        if (output_dict.isEmpty()) {
            System.out.println("No prizes with similar name!");
            return null;
        }

        for (Map.Entry<Integer, Prize> entry : output_dict.entrySet()) {
            System.out.printf(entry.getKey() +
                            " - NAME: %-10s QTY: %-5d WEIGHT: %d",
                    entry.getValue().getName(),
                    entry.getValue().getQuantity(),
                    entry.getValue().getWeight());
            System.out.println();
        }
        return output_dict;
    }

    void printPrizes() {
        int freqsSum = 0;
        if (this.userController.getPrizeList().isEmpty()) System.out.println("Prize list is empty!");
        else {
            for (Prize item : this.userController.getPrizeList()) {
                freqsSum += item.getWeight();
            }
            for (Prize prize : this.userController.getPrizeList()) {
                String output = String.format("%.2f", (double) prize.getWeight() / freqsSum);
                System.out.println(prize + " (freq. " + output + "%)");
            }
        }
    }

}


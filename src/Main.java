import controller.UserController;
import model.*;
import view.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        RWPrizesFactory rwPrizes = new RWPrizesFactory("prizes.csv");
        RWStoreFactory rwToys = new RWStoreFactory("store.csv");

        UserController userOperations = new UserController(rwPrizes, rwToys);

        Store store = new Store(userOperations);

        store.runStore();
    }

}
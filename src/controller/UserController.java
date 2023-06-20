package controller;

import model.*;

import java.io.IOException;
import java.util.List;

public class UserController {

    private final RWPrizesFactory rwPrizes;
    private final RWStoreFactory rwStore;

    private final List<Toy> storeList;
    private final List<Prize> prizeList;


    public UserController(RWPrizesFactory rwPrizes, RWStoreFactory rwStore) throws IOException {
        this.rwPrizes = rwPrizes;
        this.rwStore = rwStore;
        this.storeList = this.loadStoreList();
        this.prizeList = this.loadPrizeList();
    }

    private RWPrizes getRwPrizes() {
        return this.rwPrizes.createRepositoryOperations();
    }

    private RWStore getRwStore() {
        return this.rwStore.createRepositoryOperations();
    }

    private List<Prize> loadPrizeList() throws IOException {
        return this.getRwPrizes().readItems();
    }

    private List<Toy> loadStoreList() throws IOException {
        return this.getRwStore().readItems();
    }

    public List<Toy> getStoreList() {
        return this.storeList;
    }

    public List<Prize> getPrizeList(){
        return this.prizeList;
    }

    public void saveStoreList() throws IOException {
        this.getRwStore().saveItems(this.storeList);
    }

    public void savePrizeList() throws IOException {
        this.getRwPrizes().saveItems(this.prizeList);
    }

    public void setStoreList(Toy newToy){
        System.out.println(newToy + "\nADDED TO STORE!");
        this.storeList.add(newToy);
    }

    public void setPrizeList(Prize newPrize){
        this.prizeList.add(newPrize);
    }

    public void setQuantity(Toy toyToChange){
        for (Toy toy: this.storeList) {
            if (toy.getId() == toyToChange.getId()){
                toy.setQuantity(toy.getQuantity() - 1);
                break;
            }
        }
    }

    public void printStore(){
        for (Toy toy: this.storeList) {
            System.out.println(toy);
        }
    }

    public void deleteToyItem (Toy toy){
        for (Toy item : this.storeList) {
            if (toy.getId() == item.getId()){
                this.storeList.remove(item);
                System.out.println(item + "\nDELETED FROM STORE!");
                break;
            }
        }
    }

    public void deletePrizeItem (Prize prize){
        for (Prize item : this.prizeList) {
            if (prize.getId() == item.getId()){
                this.prizeList.remove(item);
                System.out.println(item + "\nDELETED FROM PRIZES!");
                break;
            }
        }
    }

}

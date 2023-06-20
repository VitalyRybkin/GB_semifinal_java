package model;

public class RWPrizesFactory implements IRWFactory{

    private final String fileName;


    public RWPrizesFactory(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public RWPrizes createRepositoryOperations(){
        return new RWPrizes(this.fileName);
    }
}

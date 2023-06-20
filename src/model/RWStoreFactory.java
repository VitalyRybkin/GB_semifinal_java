package model;

public class RWStoreFactory implements IRWFactory{

    private final String file_name;

    public RWStoreFactory(String file_name) {
        this.file_name = file_name;
    }
    @Override
    public RWStore createRepositoryOperations(){
        return new RWStore(this.file_name);
    }
}

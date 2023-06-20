package model;

import java.io.IOException;
import java.util.List;

public interface IReadWriteFiles {

    List<?> readItems() throws IOException;

    void saveItems(List<?> list) throws IOException;
}

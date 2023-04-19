package Model.ADT;

import java.util.HashMap;

public interface MyILatchTable {
    void put(int key, int value) throws Exception;
    int get(int key) throws Exception;
    boolean containsKey(int key);
    int getFreeAddress();
    void update(int key, int value) throws Exception;
    void setFreeAddress(int freeAddress);
    HashMap<Integer, Integer> getLatchTable();
    void setLatchTable(HashMap<Integer, Integer> newLatchTable);
}

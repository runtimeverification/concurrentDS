package concurrent;

import java.util.ArrayList;

public class MyArrayList {

    private int size;
    private ArrayList<MyElem> internalList;

    public MyArrayList(int size) {
        this.size = size;
        this.internalList = new ArrayList<MyElem>(size);
        for (int i = 0 ; i < size; i++) {
            internalList.add(new MyElem(null, false));
        }
    }

    public ArrayList<MyElem> getList() {
        return internalList;
    }
}

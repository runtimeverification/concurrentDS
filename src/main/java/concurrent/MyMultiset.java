package concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyMultiset {

    private int listSize;
    private List<MyArrayList> internalLists;

    public MyMultiset(int size) {
        this.listSize = size;
        this.internalLists = Collections.synchronizedList(new ArrayList<MyArrayList>());
        internalLists.add(new MyArrayList(listSize));
    }

    public boolean insertPair(int x, int y) {
        Position pos1 = findSlot(x);
        if (pos1 == null) {
            System.out.println("insert fail");
            return false;
        }
        Position pos2 = findSlot(y);
        if (pos2 == null) {
            // clean the dirty element in a separate thread
            /*MyElem setElem = elemArray.get(pos1);
            setElem.elem = null;*/
            System.out.println("insert fail");
            return false;
        }

        /*MyElem setElem1 = elemArray.get(pos1);
        MyElem setElem2 = elemArray.get(pos2);
        setWriteFlag(setElem1, setElem2);*/

        MyElem setElem1 = internalLists.get(pos1.row).getList().get(pos1.col);
        MyElem setElem2 = internalLists.get(pos2.row).getList().get(pos2.col);
        synchronized(setElem1) {
            synchronized(setElem2) {
                setWriteFlag(setElem1, setElem2);
            }
        }

        return true;
    }

    public void remove(int x) {
       /*for (int i = 0; i < size; i++) {
            MyElem setElem = elemArray.get(i);
            synchronized(setElem) {
                if (setElem.elem != null && setElem.elem == x) {
                    setElem.elem = null;
                    setElem.valid = false;
                    return;
                }
            }
        }*/
        int listCount = internalLists.size();
        for (int row = 0 ; row < listCount; row++) {
            MyArrayList mal = internalLists.get(row);
            for (int col = 0; col < listSize; col++) {
                MyElem setElem = mal.getList().get(col);
                synchronized(setElem) {
                    if (setElem.elem != null && setElem.elem == x) {
                        cleanSetElem(setElem);
                        return;
                    }
                }
            }
        }
    }

    public boolean lookup(int rid, int x) {
        /*try {
            for (int i = 0; i < size; i++) {
                Thread.sleep(10);
                MyElem setElem = elemArray.get(i);
                synchronized(setElem) {
                    if (setElem.elem != null && setElem.elem == x && setElem.valid == true) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {}*/
        int listCount = internalLists.size();
        for (int row = 0 ; row < listCount; row++) {
            MyArrayList mal = internalLists.get(row);
            for (int col = 0; col < listSize; col++) {
                MyElem setElem = mal.getList().get(col);
                synchronized(setElem) { 
                    if (setElem.elem != null && setElem.elem == x && setElem.valid == true) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void cleanUp() {
        System.out.println("start to clean up");
        int listCount = internalLists.size();
        for (int row = 0 ; row < listCount; row++) {
            MyArrayList mal = internalLists.get(row);
            for (int col = 0; col < listSize; col++) {
                MyElem setElem = mal.getList().get(col);
                synchronized(setElem) {
                    if (setElem.elem != null && setElem.valid == false) {
                        System.out.println("Cleaned up 1 element!");
                        setElem.elem = null;    
                    }
                }
            }
        }

    }

    public int maxSize() {
        return listSize * internalLists.size();
    }

    private void setWriteFlag(MyElem elem1, MyElem elem2) {
        elem1.valid = true;
        elem2.valid = true;
    }

    private int cleanSetElem(MyElem setElem) {
        int cleanValue = setElem.elem;
        setElem.elem = null;
        setElem.valid = false;
        return cleanValue;
    }

    private Position findSlot(int x) {
        int listCount = internalLists.size();
        for (int row = 0 ; row < listCount; row++) {
            MyArrayList mal = internalLists.get(row);
            for (int col = 0; col < listSize; col++) {
                MyElem setElem = mal.getList().get(col);
                synchronized(setElem) {
                    if (setElem.elem == null) {
                        setElem.elem = x;
                        return new Position(row, col);
                    }
                }
            }
        }

        
        // Fail to find slot in the existing arrays.
        synchronized(internalLists) {
            internalLists.add(new MyArrayList(listSize));
        }

        return null;
      
    }

}

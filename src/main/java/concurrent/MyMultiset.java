package concurrent;

import java.util.ArrayList;

public class MyMultiset {

    private int size;
    private ArrayList<MyElem> elemArray;

    public MyMultiset(int size) {
        this.size = size;
        this.elemArray = new ArrayList<MyElem>(size);
        for (int i = 0 ; i < size; i++) {
            elemArray.add(new MyElem(null, false));
        }
    }

    public boolean insertPair(int x, int y) {
        int pos1 = findSlot(x);
        if (pos1 == -1) {
            return false;
        }
        int pos2 = findSlot(y);
        if (pos2 == -1) {
            MyElem setElem = elemArray.get(pos1);
            setElem.elem = null;
            return false;
        }

        MyElem setElem1 = elemArray.get(pos1);
        MyElem setElem2 = elemArray.get(pos2);
        synchronized(setElem1) {
            synchronized(setElem2) {
                 setWriteFlag(setElem1, setElem2);
            }
        }

        return true;
    }

    public void remove(int x) {
        for (int i = 0; i < size; i++) {
            MyElem setElem = elemArray.get(i);
            synchronized(setElem) {
                if (setElem.elem != null && setElem.elem == x && setElem.valid == true) {
                    cleanSetElem(setElem);
                    return;
                }
            }
        }
    }

    public boolean lookup(int rid, int x) {
        try {
            for (int i = 0; i < size; i++) {
                Thread.sleep(10);
                MyElem setElem = elemArray.get(i);
                synchronized(setElem) {
                    if (setElem.elem != null && setElem.elem == x && setElem.valid == true) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {}
        return false;
    }

    public void printElements() {
        String str = "";
        for (int i = 0 ; i < size; i++) {
            MyElem setElem = elemArray.get(i);
            if (setElem.elem != null && setElem.valid == true) {
                if (str.equals("")) {
                    str += setElem.elem;
                } else {
                    str += "," + setElem.elem;
                }
            }
        }
        System.out.println("Set = {" + str + "}");
    }

    public int maxSize() {
        return size;
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

    private int findSlot(int x) {
        for (int i = 0; i < size; i++) {
            MyElem setElem = elemArray.get(i);
            synchronized(setElem) {
                if (setElem.elem == null) {
                    setElem.elem = x;
                    return i;
                }
            }
        }
        return -1;
    }

}

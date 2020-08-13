package concurrent;

import java.util.Random;

public class RWThread extends Thread {

    private final int tid;
    private final int round;
    private final MyMultiset multiset;
    private int setMaxSize;
    private final Random rand;

    public RWThread(int tid, int round, MyMultiset multiset) {
        this.tid = tid;
        this.round = round;
        this.multiset = multiset;
        this.setMaxSize = multiset.maxSize();
        this.rand = new Random(99 + tid);
    }

    @Override
    public void run() {
        for (int i = 0; i < round; i++) {
            int operation = rand.nextInt(3);
            if (operation == 0) {
                // insertPair
                int data1 = rand.nextInt(setMaxSize);
                int data2 = rand.nextInt(setMaxSize);
                multiset.insertPair(data1, data2);
            } else if (operation == 1 ) {
                int data = rand.nextInt(setMaxSize);
                multiset.remove(data);
            } else {
                int data = rand.nextInt(setMaxSize);
                multiset.lookup(tid, data);
            }
        }   
    }
}

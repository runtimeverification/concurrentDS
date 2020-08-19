package concurrent;

import java.util.Random;

public class CleanUpThread extends Thread {

    private final int round;
    private final MyMultiset multiset;

    public CleanUpThread(int round, MyMultiset multiset) {
        this.round = round;
        this.multiset = multiset;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < round; i++) {
                Thread.sleep(10);
                multiset.cleanUp();
            }
        } catch (Exception e) {}
    }
}

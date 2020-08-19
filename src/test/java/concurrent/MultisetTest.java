package concurrent;

import org.junit.jupiter.api.Test;

public class MultisetTest {

    @Test
    public void testMutliSet() throws Exception {
        MyMultiset set = new MyMultiset(11);
        RWThread t1 = new RWThread(0, 500, set);
        RWThread t2 = new RWThread(1, 500, set);
        CleanUpThread t3 = new CleanUpThread(10, set);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }

}

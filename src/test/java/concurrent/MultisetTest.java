package concurrent;

import org.junit.jupiter.api.Test;

public class MultisetTest {

    @Test
    public void testMutliSet() throws Exception {
        MyMultiset set = new MyMultiset(10);
        RWThread t1 = new RWThread(0, 100, set);
        RWThread t2 = new RWThread(1, 100, set);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}

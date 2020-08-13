package mop;

import java.util.Set;
import java.util.HashSet;

public class LookupResult {

    public int value;
    public Set<Boolean> results;

    public LookupResult(int value) {
        this.value = value;
        this.results = new HashSet<Boolean>();
    }

}

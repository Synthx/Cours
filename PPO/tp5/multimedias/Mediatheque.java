package multimedias;

import java.util.*;
import java.util.stream.Collectors;

import lib.Bibliotheque;

public class Mediatheque extends Bibliotheque {

    public List<CD> cds() {
        return this.ouvrages.values().stream()
            .filter(o -> o instanceof CD)
            .map(o -> (CD)o)
            .collect(Collectors.toList());
    }
}
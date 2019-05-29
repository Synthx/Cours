package multimedias;

import lib.Bibliotheque;

import java.util.List;
import java.util.stream.Collectors;

public class Mediatheque extends Bibliotheque {

    public List<CD> cds() {
        return this.ouvrages.values().stream()
                .filter(o -> o instanceof CD)
                .map(o -> (CD) o)
                .collect(Collectors.toList());
    }
}

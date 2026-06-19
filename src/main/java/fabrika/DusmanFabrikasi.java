package fabrika;

import model.oyun.Dusman;

public abstract class DusmanFabrikasi {
    public abstract Dusman dusmanOlustur(String tip, int oyuncuSeviyesi);
}
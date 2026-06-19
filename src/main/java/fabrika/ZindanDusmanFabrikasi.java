package fabrika;

import model.oyun.Dusman;
import model.oyun.Ork;
import model.oyun.Zombi;

public class ZindanDusmanFabrikasi extends DusmanFabrikasi {

    @Override
    public Dusman dusmanOlustur(String tip, int oyuncuSeviyesi) {
        if (tip == null || tip.isEmpty()) {
            return null;
        }

        switch (tip.toLowerCase()) {
            case "zombi":
                return new Zombi(oyuncuSeviyesi);
            case "ork":
                return new Ork(oyuncuSeviyesi);
            default:
                throw new IllegalArgumentException("Bilinmeyen düşman tipi: " + tip);
        }
    }
}
package model.strateji;

public class HizliSaldiri implements SavasStratejisi {
    @Override
    public int hasarHesapla(int tabanHasar) {
        return tabanHasar;
    }

    @Override
    public String getStratejiAdi() {
        return "Hızlı Saldırı";
    }
}
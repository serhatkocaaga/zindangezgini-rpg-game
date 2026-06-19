package model.strateji;

public class AgirSaldiri implements SavasStratejisi {
    @Override
    public int hasarHesapla(int tabanHasar) {
        return (int) (tabanHasar * 1.5);
    }

    @Override
    public String getStratejiAdi() {
        return "Ağır Saldırı";
    }
}
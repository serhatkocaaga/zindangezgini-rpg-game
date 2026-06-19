package model.strateji;

public class SavunmaPozisyonu implements SavasStratejisi {
    @Override
    public int hasarHesapla(int tabanHasar) {
        return (int) (tabanHasar * 0.2);
    }

    @Override
    public String getStratejiAdi() {
        return "Savunma Pozisyonu";
    }
}
package model.karakter;

public class Buyucu extends Karakter {
    private int zekaPuani;
    private int manaPuani;

    public Buyucu(String isim, int zekaPuani, int manaPuani) {
        this.isim = isim;
        this.zekaPuani = zekaPuani;
        this.manaPuani = manaPuani;
        this.unvan = "Büyücü";
        this.toplamXpPuani = 0;
    }

    public Buyucu() {
        this.unvan = "Büyücü";
    }

    // Üst sınıfın soyut metodunu eziyoruz (Polymorphism)
    @Override
    public int getTabanHasar() {
        // Büyücünün ham hasarı zeka puanı kadardır
        return this.zekaPuani;
    }

    // Getter ve Setter'lar
    public int getZekaPuani() { return zekaPuani; }
    public void setZekaPuani(int zekaPuani) { this.zekaPuani = zekaPuani; }
    public int getManaPuani() { return manaPuani; }
    public void setManaPuani(int manaPuani) { this.manaPuani = manaPuani; }
}
package model.karakter;

public class Savasci extends Karakter {
    private int fizikselGuc;
    private int ofkeBari;

    public Savasci(String isim, int fizikselGuc) {
        this.isim = isim;
        this.fizikselGuc = fizikselGuc;
        this.ofkeBari = 0;
        this.unvan = "Savaşçı";
        this.toplamXpPuani = 0;
    }

    public Savasci() {
        this.unvan = "Savaşçı";
    }

    // Üst sınıfın soyut metodunu eziyoruz (Polymorphism)
    @Override
    public int getTabanHasar() {
        // Savaşçının ham hasarı fiziksel gücü kadardır
        return this.fizikselGuc;
    }

    // Getter ve Setter'lar
    public int getFizikselGuc() { return fizikselGuc; }
    public void setFizikselGuc(int fizikselGuc) { this.fizikselGuc = fizikselGuc; }
    public int getOfkeBari() { return ofkeBari; }
    public void setOfkeBari(int ofkeBari) { this.ofkeBari = ofkeBari; }
}
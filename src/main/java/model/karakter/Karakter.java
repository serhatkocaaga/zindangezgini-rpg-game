package model.karakter;

import model.esya.EnvanterKaydi;
import model.oyun.Dusman;
import model.strateji.SavasStratejisi; // Strateji arayüzümüzü içe aktarıyoruz
import model.strateji.HizliSaldiri;    // Varsayılan strateji için

import java.util.ArrayList;
import java.util.List;

public abstract class Karakter {
    protected int karakterId;
    protected String isim;
    protected String unvan;
    protected int guncelHp;
    protected int altinMiktari;
    protected int toplamXpPuani;

    protected List<String> durumEtkileri = new ArrayList<>();
    protected List<EnvanterKaydi> envanter = new ArrayList<>();


    protected SavasStratejisi aktifStrateji = new HizliSaldiri();


    public void setStrateji(SavasStratejisi yeniStrateji) {
        this.aktifStrateji = yeniStrateji;
        System.out.println("-> " + this.isim + " taktik değiştirdi: [" + yeniStrateji.getStratejiAdi() + "]");
    }


    public int taktikselSaldiriYap() {
        int tabanHasar = getTabanHasar();
        int gerceklesenHasar = aktifStrateji.hasarHesapla(tabanHasar);

        System.out.println(getTamIsim() + ", [" + aktifStrateji.getStratejiAdi() + "] taktiği ile saldırdı! -> Hasar: " + gerceklesenHasar);
        return gerceklesenHasar;
    }


    public abstract int getTabanHasar();

    public int getSeviye() {
        return (toplamXpPuani / 100) + 1;
    }

    public String getTamIsim() {
        return unvan + " " + isim;
    }
    public int getToplamXpPuani(){
        return this.toplamXpPuani;
    }
    public void setToplamXpPuani(int toplamXpPuani) {
        this.toplamXpPuani = toplamXpPuani;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getIsim() { return isim; }

    public void dusmaniYen(Dusman dusman) {
        int kazanilanXp = dusman.getTabanHp() / 2;
        int eskiSeviye = this.getSeviye();

        this.toplamXpPuani += kazanilanXp;

        System.out.println(" SAVAŞ SONUCU: " + this.isim + ", " + dusman.getDusmanIsmi() + " adlı düşmanı alt etti!");
        System.out.println("  > +" + kazanilanXp + " XP kazanıldı. (Toplam XP: " + this.toplamXpPuani + ")");

        if (this.getSeviye() > eskiSeviye) {
            System.out.println("  >  SEVİYE ATLADIN! Yeni Seviyen: " + this.getSeviye());
        }
        System.out.println("---------------------------------------------------");
    }
}
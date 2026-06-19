package model.kullanici;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Kullanici {
    private int kullaniciId;
    private String kullaniciAdi;
    private String sifre;
    private LocalDate kayitTarihi;

    private List<String> karakterlerListesi = new ArrayList<>();


    public int getHesapYasi() {
        if (kayitTarihi == null) return 0;
        return Period.between(kayitTarihi, LocalDate.now()).getYears();
    }


}
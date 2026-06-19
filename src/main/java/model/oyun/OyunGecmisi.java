package model.oyun;

import java.time.LocalDateTime;
import java.time.Duration;

public class OyunGecmisi {
    private int skorId;
    private int kullaniciId;
    private LocalDateTime baslangicSaati;
    private LocalDateTime bitisSaati;

    public long getHayattaKalinanSureDakika() {
        if (baslangicSaati != null && bitisSaati != null) {
            return Duration.between(baslangicSaati, bitisSaati).toMinutes();
        }
        return 0;
    }
}
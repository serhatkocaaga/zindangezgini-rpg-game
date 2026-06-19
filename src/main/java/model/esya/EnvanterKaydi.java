package model.esya;

public class EnvanterKaydi {
    private int envanterId;
    private int karakterId;
    private Esya esya;
    private EsyaEfsunlari efsun; // Veritabanı ilişkisi için tuttuk
    private int miktar;

    public EnvanterKaydi(int karakterId, Esya esya, EsyaEfsunlari efsun, int miktar) {
        this.karakterId = karakterId;
        this.esya = esya;
        this.efsun = efsun;
        this.miktar = miktar;
    }

    public Esya getKullanimaHazirEsya() {
        Esya kullanilacakEsya = this.esya;

        if (this.efsun != null) {
            kullanilacakEsya = this.efsun;
        }
        return kullanilacakEsya;
    }

    public Esya getEsya() { return esya; }
    public EsyaEfsunlari getEfsun() { return efsun; }
    public int getMiktar() { return miktar; }
    public void setMiktar(int miktar) { this.miktar = miktar; }
}
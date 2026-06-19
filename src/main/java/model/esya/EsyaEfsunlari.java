package model.esya;

public abstract class EsyaEfsunlari implements Esya {

    protected Esya sarmalananEsya;

    private int efsunId;
    private String efsunAdi;
    private String elementTipi;
    private int ekstraHasarPuani;

    public EsyaEfsunlari(Esya sarmalananEsya, int efsunId, String efsunAdi, String elementTipi, int ekstraHasarPuani) {
        this.sarmalananEsya = sarmalananEsya;
        this.efsunId = efsunId;
        this.efsunAdi = efsunAdi;
        this.elementTipi = elementTipi;
        this.ekstraHasarPuani = ekstraHasarPuani;
    }

    @Override
    public String getTamAd() {
        return sarmalananEsya.getTamAd() + " [+" + this.efsunAdi + " (" + this.elementTipi + ")]";
    }

    @Override
    public int getToplamGuc() {
        return sarmalananEsya.getToplamGuc() + this.ekstraHasarPuani;
    }
}
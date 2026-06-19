package model.esya;

import java.util.ArrayList;
import java.util.List;

public class TemelEsya implements Esya {
    private int esyaId;
    private String esyaAdi;
    private String esyaTuru;
    private List<Integer> temelEtkiGucu = new ArrayList<>();

    public TemelEsya(int esyaId, String esyaAdi, String esyaTuru, int tabanGuc) {
        this.esyaId = esyaId;
        this.esyaAdi = esyaAdi;
        this.esyaTuru = esyaTuru;
        this.temelEtkiGucu.add(tabanGuc);
    }

    public TemelEsya() {}

    @Override
    public String getTamAd() {
        return this.esyaAdi;
    }

    @Override
    public int getToplamGuc() {
        if (temelEtkiGucu != null && !temelEtkiGucu.isEmpty()) {
            return temelEtkiGucu.get(0);
        }
        return 0;
    }

    public String getEsyaAdi() { return esyaAdi; }
    public void setEsyaAdi(String esyaAdi) { this.esyaAdi = esyaAdi; }
}
package com.example.mindharbor.model;

public class PazientiNumTest {
        private Integer numTest;
        private Paziente paziente;
        //nome, cognome, genere e username

        public PazientiNumTest() {}

        public PazientiNumTest(Integer numTest, Paziente paziente) {
            this.numTest=numTest;
            this.paziente=paziente;
        }

        public Integer getNumTest() {
            return numTest;
        }
        public void setNumTest(Integer numTest) {
            this.numTest = numTest;
        }

    public Paziente getPaziente() {
        return paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }
}

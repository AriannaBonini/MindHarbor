package com.example.mindharbor.model;

public class Appuntamento {
        private String idAppuntamento;
        private String data;
        private String ora;
        private String usernamePaziente;
        private String usernamePsicologo;
        private String nomePsicologo;
        private String cognomePsicologo;
        private String nomePaziente;
        private String cognomePaziente;


        public Appuntamento(String data, String ora, String nomePsicologo, String cognomePsicologo, String nomePaziente, String cognomePaziente, String usernamePaziente, String usernamePsicologo, String idAppuntamento) {
            this.idAppuntamento = idAppuntamento;
            this.data = data;
            this.ora = ora;
            this.usernamePaziente = usernamePaziente;
            this.usernamePsicologo = usernamePsicologo;
            this.nomePaziente = nomePaziente;
            this.cognomePaziente = cognomePaziente;
            this.nomePsicologo = nomePsicologo;
            this.cognomePsicologo = cognomePsicologo;

        }
        public String getIdAppuntamento() {
            return idAppuntamento;
        }

        public void setIdAppuntamento(String idAppuntamento) {
            this.idAppuntamento = idAppuntamento;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getOra() {
            return ora;
        }

        public void setOra(String ora) {
            this.ora = ora;
        }

        public String getUsernamePaziente() {
            return usernamePaziente;
        }

        public void setUsernamePaziente(String usernamePaziente) {
            this.usernamePaziente = usernamePaziente;
        }

        public String getUsernamePsicologo() {
            return usernamePsicologo;
        }

        public void setUsernamePsicologo(String usernamePsicologo) {
            this.usernamePsicologo = usernamePsicologo;
        }

        public String getNomePsicologo() {return nomePsicologo;}

        public void setNomePsicologo(String nomePsicologo) {this.nomePsicologo = nomePsicologo;}

        public String getCognomePsicologo() {return cognomePsicologo;}

        public void setCognomePsicologo(String cognomePsicologo) {this.cognomePsicologo = cognomePsicologo;}

        public String getNomePaziente() {return nomePaziente;}

        public void setNomePaziente(String nomePaziente) {this.nomePaziente = nomePaziente;}

        public String getCognomePaziente() {return cognomePaziente;}

        public void setCognomePaziente(String cognomePaziente) {this.cognomePaziente = cognomePaziente;}
}


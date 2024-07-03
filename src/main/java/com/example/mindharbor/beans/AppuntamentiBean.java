package com.example.mindharbor.beans;

public class AppuntamentiBean {
        private String data;
        private String ora;
        private String usernamePaziente;
        private String usernamePsicologo;
        private String nomePsicologo;
        private String cognomePsicologo;
        private String nomePaziente;
        private String cognomePaziente;
        private Integer idAppuntamento;
        private Integer anni;
        private String genere;
        private Integer notificaRichiesta;


    public AppuntamentiBean() {
    }

    public AppuntamentiBean(String data, String ora, String nomePsicologo, String cognomePsicologo, String nomePaziente, String cognomePaziente, String usernamePaziente, String usernamePsicologo, Integer idAppuntamento, Integer anni, String genere, Integer notificaRichiesta) {
            this.data = data;
            this.ora = ora;
            this.usernamePsicologo = usernamePsicologo;
            this.usernamePaziente= usernamePaziente;
            this.nomePaziente = nomePaziente;
            this.cognomePaziente = cognomePaziente;
            this.nomePsicologo = nomePsicologo;
            this.cognomePsicologo = cognomePsicologo;
            this.idAppuntamento = idAppuntamento;
            this.anni = anni;
            this.genere=genere;
            this.notificaRichiesta=notificaRichiesta;
        }

    public AppuntamentiBean(String data, String ora, String nomePsicologo, String cognomePsicologo, String nomePaziente, String cognomePaziente, String usernamePaziente, String usernamePsicologo, Integer idAppuntamento, Integer anni) {
        this(data, ora, nomePsicologo, cognomePsicologo, nomePaziente, cognomePaziente, usernamePaziente, usernamePsicologo, idAppuntamento, anni, null,null);
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

        public String getusernamePaziente() {
            return usernamePaziente;
        }

        public void setusernamePaziente(String usernamePaziente) {
            this.usernamePaziente = usernamePaziente;
        }

        public String getusernamePsicologo() {
            return usernamePsicologo;
        }

        public void setusernamePsicologo(String usernamePsicologo) {
            this.usernamePsicologo= usernamePsicologo;
        }

        public String getNomePsicologo() {return nomePsicologo;}
        public String getCognomePsicologo() {return cognomePsicologo;}

        public String getNomePaziente() {return nomePaziente;}

        public void setNomePaziente(String nomePaziente) {this.nomePaziente = nomePaziente;}

        public String getCognomePaziente() {return cognomePaziente;}

        public void setCognomePaziente(String cognomePaziente) { this.cognomePaziente = cognomePaziente;}

        public Integer getAnni() {return anni;}

        public void setAnni(Integer anni) {this.anni = anni;}

        public Integer getIdAppuntamento() {return idAppuntamento;}

        public String getGenere() {return genere;}

        public void setGenere(String genere) {this.genere = genere;}

        public Integer getNotificaRichiesta() {return notificaRichiesta;}

}

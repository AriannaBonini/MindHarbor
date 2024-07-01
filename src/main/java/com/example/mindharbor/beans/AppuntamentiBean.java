package com.example.mindharbor.beans;

public class AppuntamentiBean {
        private String Data;
        private String Ora;
        private String usernamePaziente;
        private String usernamePsicologo;
        private String nomePsicologo;
        private String cognomePsicologo;
        private String nomePaziente;
        private String cognomePaziente;
        private String IdAppuntamento;
        private Integer età ;

    public AppuntamentiBean() {
    }


    public AppuntamentiBean(String Data,String Ora,String nomePsicologo,String cognomePsicologo,String nomePaziente,String cognomePaziente,String usernamePaziente,String usernamePsicologo, String IdAppuntamento,Integer età) {
            this.Data = Data;
            this.Ora= Ora;
            this.usernamePsicologo = usernamePsicologo;
            this.usernamePaziente= usernamePaziente;
            this.nomePaziente = nomePaziente;
            this.cognomePaziente = cognomePaziente;
            this.nomePsicologo = nomePsicologo;
            this.cognomePsicologo = cognomePsicologo;
            this.IdAppuntamento=IdAppuntamento;
            this.età=età;
        }

        public String getData() {
            return Data;
        }

        public void setData(String Data) {
            this.Data = Data;
        }

        public String getOra() {
            return Ora;
        }

        public void setOra(String Ora) {
            this.Ora = Ora;
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

        public void setNomePsicologo(String nomePsicologo) {this.nomePsicologo = nomePsicologo;}

        public String getCognomePsicologo() {return cognomePsicologo;}

        public void setCognomePsicologo(String cognomePsicologo) {this.cognomePsicologo = cognomePsicologo;}

        public String getNomePaziente() {return nomePaziente;}

        public void setNomePaziente(String nomePaziente) {this.nomePaziente = nomePaziente;}

        public String getCognomePaziente() {return cognomePaziente;}

        public void setCognomePaziente(String cognomePaziente) { this.cognomePaziente = cognomePaziente;}

    public Integer getEtà() {return età;}

    public void setEtà(Integer età) {this.età = età;}

    public String getIdAppuntamento() {return IdAppuntamento;}

    public void setIdAppuntamento(String idAppuntamento) {IdAppuntamento = idAppuntamento;}
}

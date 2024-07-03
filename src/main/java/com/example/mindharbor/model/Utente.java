package com.example.mindharbor.model;

import com.example.mindharbor.user_type.UserType;



    public class Utente {
        private String username;
        private String nome;
        private String cognome;
        private UserType userType;
        private String genere;
        private String password;

        public Utente(String username, String nome, String cognome, UserType userType, String genere, String password ) {
            setUsername(username);
            setNome(nome);
            setCognome(cognome);
            setUserType(userType);
            setGenere(genere);
            setPassword(password);
        }

        public Utente(String username, String nome, String cognome, UserType userType) {
            this(username, nome, cognome, userType, null, null);
        }

        public Utente(String username, String password) {
            this(username, null, null, null, null, password);
        }

        public Utente(String username) {
            this(username, null);
        }



        public String getGenere() {return genere;}
        public void setGenere(String genere) {this.genere = genere;}
        public String getPassword() {return password;}
        public void setPassword(String password) {this.password = password;}
        public String getUsername() {return username;}
        public void setUsername(String username) {this.username = username;}
        public String getNome() {return nome;}
        public void setNome(String nome) {this.nome = nome;}
        public String getCognome() {return cognome;}
        public void setCognome(String cognome) {this.cognome = cognome;}
        public UserType getUserType() {return userType;}
        public void setUserType(UserType userType) {
            this.userType = userType;
        }
    }


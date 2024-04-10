package com.example.mindharbor.model;

import com.example.mindharbor.Enum.UserType;



    public class Utente{
        protected String username;
        protected String nome;
        protected String cognome;
        protected final UserType userType;
        public Utente(String username, String nome, String cognome, UserType userType) {
            this.username = username;
            this.nome = nome;
            this.cognome = cognome;
            this.userType = userType;
        }
        public String getUsername() {
            return username;
        }
        public String getNome() {
            return nome;
        }

        public String getCognome() {
            return cognome;
        }

        public UserType getUserType() {
            return userType;
        }
    }


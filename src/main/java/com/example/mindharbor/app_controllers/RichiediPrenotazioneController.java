package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.utilities.setInfoUtente;

public class RichiediPrenotazioneController {
    public HomeInfoUtenteBean getPageRichPrenInfo() {return new setInfoUtente().getInfo();}
}

package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.DomandeTestBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.mockapi.BoundaryMockAPI;
import com.example.mindharbor.model.DomandeTest;
import com.example.mindharbor.utilities.setInfoUtente;

import java.util.List;

public class SvolgiTestController {
    public HomeInfoUtenteBean getPagePazInfo() {
        HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
        return homeInfoUtente;
    }

    public List<DomandeTestBean> CercaDomande(String nomeTest) {
        List<DomandeTest> domandeTest= BoundaryMockAPI.DomandeTest(nomeTest);

        List<DomandeTestBean> domandeBean=null;
        return  domandeBean;
    }
}

package com.example.mindharbor.dao;

import com.example.mindharbor.eccezioni.DAOException;
import com.example.mindharbor.model.Psicologo;

public interface PsicologoDAO {
    Psicologo getInfoPsicologo(Psicologo psicologo) throws DAOException;
}

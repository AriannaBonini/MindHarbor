package com.example.mindharbor.dao;

import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Psicologo;

public interface PsicologoDAO {
    Psicologo getInfoPsicologo(Psicologo psicologo) throws DAOException;
}

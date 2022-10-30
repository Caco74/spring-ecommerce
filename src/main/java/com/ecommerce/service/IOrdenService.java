package com.ecommerce.service;

import com.ecommerce.model.Orden;
import com.ecommerce.model.Usuario;

import java.util.List;

public interface IOrdenService {
    List<Orden> findAll();
    Orden save(Orden orden);
    String generarNumeroOrden();
    List<Orden> findByUsuario(Usuario usuario);
}

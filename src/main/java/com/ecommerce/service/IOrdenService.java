package com.ecommerce.service;

import com.ecommerce.model.Orden;

import java.util.List;

public interface IOrdenService {
    List<Orden> findAll();
    Orden save(Orden orden);
}

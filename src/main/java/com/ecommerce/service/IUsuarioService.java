package com.ecommerce.service;

import com.ecommerce.model.Usuario;

import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> findById(Integer id);
    Usuario save(Usuario u);
    Optional<Usuario> findByEmail(String email);
}

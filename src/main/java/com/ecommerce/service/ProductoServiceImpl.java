package com.ecommerce.service;

import com.ecommerce.model.Producto;
import com.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository prodRepository;

    @Override
    public Producto save(Producto producto) {
        return prodRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(Integer id) {
        //return Optional.empty();
        return prodRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        prodRepository.save(producto);
    }

    @Override
    public void delete(Integer id) {
        prodRepository.deleteById(id);
    }
}

package com.ecommerce.service;

import com.ecommerce.model.Producto;
import com.ecommerce.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService {
    @Autowired
    private IProductoRepository prodRepository;

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

    @Override
    public List<Producto> findAll() {
        return prodRepository.findAll();
    }
}

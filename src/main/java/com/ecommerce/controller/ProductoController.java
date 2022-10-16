package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.ProductoService;
import com.ecommerce.service.UploadFileService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService service;

    @Autowired
    private UploadFileService upload;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos", service.findAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
        LOGGER.info("Este es el objeto Producto {}", producto);
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        producto.setUsuario(u);

        // Imagen
        if (producto.getId()==null) { // Cuando se crea un producto. Primera vez
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        } else {
            if (file.isEmpty()) { // Cuando editamos el producto, pero no cambiamos la imagen
                Producto p = new Producto();
                p = service.get(producto.getId()).get();
                producto.setImagen(p.getImagen());
            } else {
                String nombreImagen = upload.saveImage(file);
                producto.setImagen(nombreImagen);
            }
        }

        service.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        new Producto();
        Producto caco;
        Optional<Producto> optProducto = service.get(id);
        caco = optProducto.get();

        LOGGER.info("Producto buscado: {}", caco);
        model.addAttribute("producto", caco);

        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto) {
        service.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/productos";
    }
}

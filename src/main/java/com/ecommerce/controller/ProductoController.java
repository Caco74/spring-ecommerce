package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.IProductoService;
import com.ecommerce.service.IUsuarioService;
import com.ecommerce.service.UploadFileService;
import com.ecommerce.service.UsuarioServiceImpl;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private IProductoService service;

    @Autowired
    private IUsuarioService usuarioService;

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
    public String save(Producto producto,@RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("Este es el objeto Producto {}", producto);
        Usuario u = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        producto.setUsuario(u);

        // Imagen
        if (producto.getId()==null) { // Cuando se crea un producto. Primera vez
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        } else {

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
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        Producto p = new Producto();
        p = service.get(producto.getId()).get();

        if (file.isEmpty()) { // Cuando editamos el producto, pero no cambiamos la imagen

            producto.setImagen(p.getImagen());
        } else { // Cuando se edita también la imagen

            // Borramos siempre y cuando no sea la imagen por defecto.
            if (!p.getImagen().equals("default.jpg")) {
                upload.deleteImage(p.getImagen());
            }
            producto.setUsuario(p.getUsuario());
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }

        service.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Producto p = new Producto();
        p = service.get(id).get();

        // Borramos siempre y cuando no sea la imagen por defecto.
        if (!p.getImagen().equals("default.jpg")) {
            upload.deleteImage(p.getImagen());
        }

        service.delete(id);
        return "redirect:/productos";
    }
}

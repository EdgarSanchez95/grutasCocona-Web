package com.proyecto.cocona.servicio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GuardarImagenServicio {  //clase para subir imagen del producto al servidor
    private String folder = "imagenes//";

    public String guardarImagen(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        }
        return "default.jpg";
    }

    public void eliminarImagen(String nombre) {
        String ruta = "imagenes//";
        File file= new File(ruta+nombre);
        file.delete();
    }

}
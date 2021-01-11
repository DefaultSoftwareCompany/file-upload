package com.dsc.controller;

import com.dsc.model.FileStorage;
import com.dsc.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
public class FileController {
    private final FileRepository repository;

    @Autowired
    public FileController(FileRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/save")
    public @ResponseBody
    Long save(@RequestParam MultipartFile image, @RequestParam String password) throws IOException {
        if (password.equals("Sunnat5427#")) {
            FileStorage fileStorage = new FileStorage();
            fileStorage.setContent(image.getBytes());
            return repository.save(fileStorage).getImageId();
        } else return null;
    }

    @GetMapping(value = "/get/{imageId}", produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE
    }
    )
    public @ResponseBody
    byte[] getImage(@PathVariable Long imageId) throws Exception {
        Optional<FileStorage> image = repository.findById(imageId);
        if (image.isPresent()) return image.get().getContent();
        else throw new Exception("There is no image with such an id");
    }

    @DeleteMapping("/delete/{imageId}")
    public void delete(@PathVariable Long imageId, @RequestParam String password) {
        if (password.equals("Sunnat5427#")) {
            repository.deleteById(imageId);
        }
    }
}

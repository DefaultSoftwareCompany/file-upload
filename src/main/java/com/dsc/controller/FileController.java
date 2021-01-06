package com.dsc.controller;

import com.dsc.model.FileStorage;
import com.dsc.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {
    private final FileRepository repository;

    @Autowired
    public FileController(FileRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/save")
    public @ResponseBody
    Long save(@RequestParam MultipartFile image) throws IOException {
        FileStorage fileStorage = new FileStorage();
        fileStorage.setContent(image.getBytes());
        return repository.save(fileStorage).getImageId();
    }

    @GetMapping(value = "/get/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@PathVariable Long imageId) {
        return repository.getOne(imageId).getContent();
    }

    @DeleteMapping("/delete/{imageId}")
    public void delete(@PathVariable Long imageId) {
        repository.deleteById(imageId);
    }
}

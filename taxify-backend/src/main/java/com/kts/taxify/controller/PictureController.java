package com.kts.taxify.controller;

import com.kts.taxify.services.picture.GetPicture;
import com.kts.taxify.services.picture.UploadPicture;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/picture")
@RequiredArgsConstructor
public class PictureController {
    private final UploadPicture uploadPicture;

    private final GetPicture getPicture;

    @PostMapping(value = "/")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        return uploadPicture.execute(file);
    }

    @GetMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
    public InputStreamResource download(@RequestParam String fileName) throws IOException {
        return getPicture.execute(fileName);
    }
}

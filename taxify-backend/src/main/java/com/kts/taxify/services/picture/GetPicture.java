package com.kts.taxify.services.picture;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GetPicture {
    private final DownloadPicture downloadPicture;

    public InputStreamResource execute(String fileName) throws IOException {
        String path = new File("data/images/" + fileName).getAbsolutePath();
        var imgFile = new FileSystemResource(path);
        if (!imgFile.exists()) {
            path = downloadPicture.execute(fileName);
            imgFile = new FileSystemResource(path);
        }
        return new InputStreamResource(imgFile.getInputStream());
    }
}

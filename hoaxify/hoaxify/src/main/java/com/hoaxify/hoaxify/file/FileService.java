package com.hoaxify.hoaxify.file;

import com.hoaxify.hoaxify.configuration.AppConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService {

    AppConfiguration appConfiguration;

    Tika tika;

    public FileService(AppConfiguration appConfiguration){
        super();
        this.appConfiguration = appConfiguration;
        this.tika = new Tika();
    }

    public String saveProfileImage(String base64Image) throws IOException {
        String imageName = UUID.randomUUID().toString().replace("-", "");

        byte[] decodeBytes = Base64.getDecoder().decode(base64Image);

        File targetFile = new File(appConfiguration.getFullProfileImagesPath() + "/"+imageName);

        FileUtils.writeByteArrayToFile(targetFile, decodeBytes);
        return imageName;
    }

    private String getRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String detectType(byte[] fileArr) {
        return tika.detect(fileArr);
    }
}

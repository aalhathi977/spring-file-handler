package com.spring.files.handling.springfilehandler.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class controller {

    @RequestMapping(value = "/file")
    ResponseEntity<ByteArrayResource> getFile() throws IOException {
        byte[] fileBytes = loadEmployeesWithClassPathResource();
        readImagesUsingBufferedImageAndBufferedReaderAndWriter();
        ByteArrayResource resource = new ByteArrayResource(fileBytes);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("whatever")
                                .build().toString())
                .body(resource);
    }

    public byte[] loadEmployeesWithClassPathResource() throws IOException {
//        ClassPathResource classPathResource = new ClassPathResource("src/main/resources/static/images/amazing-city-picture.jpg");
//        if (classPathResource.exists()&&classPathResource.isReadable()){
//            System.out.println("Exists!");
//        }
//        File f = new File("src/main/resources/static/images/amazing-city-picture.jpg");
//        if(f.exists() && !f.isDirectory()) {
//            System.out.println("yes");
//        }

        Path path = Paths.get("src/main/resources/static/images/Invoice_2755.pdf");
        byte[] data = Files.readAllBytes(path);

        byte[] ba2 = {1,2,3,4,5,70,90};
        File outputfile = new File("src/main/resources/static/images/bytes.txt");
        OutputStream outputStream = new FileOutputStream(outputfile);
        outputStream.write(ba2);
        outputStream.close();



        return data;
    }

    public void readImagesUsingBufferedImageAndBufferedReaderAndWriter(){
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("src/main/resources/static/images/amazing-city-picture.jpg"));
            for (int y = 0; y < bufferedImage.getHeight(); y++){
                for (int x = 0; x < bufferedImage.getWidth(); x++){
                    if ((x % 2) == 0){
                        bufferedImage.setRGB(x,y,new Color(0,102,255).getRGB());
                    }
                }
            }
            File outputImage = new File("src/main/resources/static/images/editedImage.jpg");
            ImageIO.write(bufferedImage,"jpg",outputImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

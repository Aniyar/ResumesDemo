package com.example.resumes.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {
	
    static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
                
        try {
            while (!deflater.finished()) {
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            outputStream.close();
        } catch (IOException e) {
            logger.info("Exception at compressImage " +e.getMessage());
        }
        finally{
            
        }
        return outputStream.toByteArray();
    }


    public static byte[] getBytes(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        }
        catch (Exception ignored) {

        }
        return new byte[0];
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        finally{
        }
        return outputStream.toByteArray();
    }
}
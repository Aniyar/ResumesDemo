package com.example.resumes.util;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@UtilityClass
public class ImageUtil {
	
    static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	public static byte[] compressImage(byte[] data) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)){
            Deflater deflater = new Deflater();
            deflater.setLevel(Deflater.BEST_COMPRESSION);
            deflater.setInput(data);
            deflater.finish();
            byte[] tmp = new byte[4*1024];
            while (!deflater.finished()) {
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            logger.info("Exception at compressImage " +e.getMessage());
        }
        return new byte[0];
    }


    public static byte[] getBytes(String filePath){
        try{
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        }catch(IOException ex){
            logger.info("IOException " + ex.getMessage() + " at getBytes");
        }
        return new byte[0];
    }

    public static byte[] decompressImage(byte[] data) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)){
            Inflater inflater = new Inflater();
            inflater.setInput(data);
            byte[] tmp = new byte[4*1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            return outputStream.toByteArray();
        } catch (Exception ex) {
            logger.info("Exception " + ex.getMessage() + " at decompressImage");
        }
        return new byte[0];
    }
}
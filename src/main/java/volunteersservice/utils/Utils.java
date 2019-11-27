package volunteersservice.utils;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.core.context.SecurityContextHolder;

import volunteersservice.models.entities.User;
import volunteersservice.security.UserDetailsImpl;

public class Utils {
    public static String calcSHA256(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(string.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String calcMD5(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStaticFileContents(String dirPath, String fileName, String alternativeText) {
        try {
            return new String(Files.readAllBytes(FileSystems.getDefault().getPath(dirPath, fileName)));
        } catch (Exception ex) {
            return alternativeText;
        }
    }

    public static User getUserFromContext() {
        Object res = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (res.getClass() == UserDetailsImpl.class) {
            return ((UserDetailsImpl) res).getUser();
        } else {
            return null;//new User("unathorized@unathorized", "unathorized", "unathorized", null, "unathorized", null);
        }
    }
}
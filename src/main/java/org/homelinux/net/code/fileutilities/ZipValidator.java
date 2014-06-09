package org.homelinux.net.code.fileutilities;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipFile;


public class ZipValidator {

    private static int errorCount = 0;
    private static int okCount = 0;

    public static void main(String[] args) throws IOException {

        Path dir = (args.length < 1) ? Paths.get(".") : Paths.get(args[0]);

        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (isValidZip(file)) {
                    okCount++;
                } else {
                    errorCount++;
                    System.out.println("Error zip: " + file.getFileName().toString());

                }
                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println("Zip validation result:");
        System.out.println("Errors: " + errorCount);
        System.out.println("Ok:" + okCount);

    }

    static boolean isValidZip(final Path file) {
        ZipFile zipfile = null;
        try {
            // Simple zip check
            zipfile = new ZipFile(file.toFile());
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (zipfile != null) {
                    zipfile.close();
                    zipfile = null;
                }
            } catch (IOException e) {
            }
        }
    }
}
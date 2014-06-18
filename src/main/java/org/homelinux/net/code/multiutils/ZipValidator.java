package org.homelinux.net.code.multiutils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
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
        return isValidZipWithValidEntries(file);
    }

    static boolean isValidZipWithValidEntries(final Path file) {
        ZipFile zipArchive = null;
        try {
            zipArchive = new ZipFile(file.toFile());
            Enumeration entries = zipArchive.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                if (zipEntry == null) {
                    return false;
                }
                InputStream zipEntryContent = zipArchive.getInputStream(zipEntry);
                if (!validCrc(zipEntry, zipEntryContent)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (zipArchive != null) {
                    zipArchive.close();
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private static boolean validCrc(ZipEntry zipEntry, InputStream zipContent) throws IOException {
        long correctCrc = zipEntry.getCrc();
        long currentCrc = calcCheckSum(zipContent);
        return currentCrc == correctCrc;
    }

    static boolean isValidZipWithValidFirstEntry(final Path file) {
        ZipFile zipfile = null;
        try {
            // Simple zip check, taste the first zip entry
            zipfile = new ZipFile(file.toFile());
            Enumeration entries = zipfile.entries();
            ZipEntry zip = (ZipEntry) entries.nextElement();
            return zip != null;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (zipfile != null) {
                    zipfile.close();
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    static boolean isValidZipContainer(final Path file) {
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
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    static long calcCheckSum(final InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        int byteCount;
        CRC32 checksum = new CRC32();
        byte[] buffer = new byte[1024];
        while ((byteCount = bis.read(buffer)) != -1) {
            checksum.update(buffer, 0, byteCount);
        }
        bis.close();
        return checksum.getValue();
    }
}
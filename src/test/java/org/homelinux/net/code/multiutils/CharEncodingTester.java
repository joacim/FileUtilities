package org.homelinux.net.code.multiutils;

import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Ignoring document encoding is easy an you often get away with it in the western european java world.
 *
 * But to create systems that can handle and scale to international fame you do need to handle encoding.
 *
 * This is just some unit tests to show ignorant behaviour and where it fails using default/platform
 * encoding when the byte streams are interpreted.
 *
 * In general you should be wary as soon as you start interpreting or modifying the Input-/OutputStream.
 * Using StreamReader/Writer, getBytes(), new String(byte[]) and other byte interpretations where you
 * are able to feed a Charset in the call should always be called with an explicit Charset. Or you should
 * have a very good reason.
 *
 */
public class CharEncodingTester {

    @Test
    public void utf16EncodedDocumentCanBeTreatedAsBytes() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("utf16EncDoc.xml");
        byte[] readBytes = new byte[50];
        is.read(readBytes);
        is.close();

        String testFile = "utf16-test.xml";
        OutputStream os = new FileOutputStream(testFile);
        os.write(readBytes);
        os.close();

        InputStream readBackStream = new FileInputStream(testFile);
        byte[] writtenBytes = new byte[50];
        readBackStream.read(writtenBytes);
        readBackStream.close();

        File readBackFile = new File(testFile);
        readBackFile.delete();

        assertThat(writtenBytes, is(readBytes));
    }

    @Test
    public void utf8EncodedDocumentCanBeTreatedAsBytes() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("utf8EncDoc.xml");
        byte[] readBytes = new byte[50];
        is.read(readBytes);
        is.close();

        String testFile = "utf8-test.xml";
        OutputStream os = new FileOutputStream(testFile);
        os.write(readBytes);
        os.close();

        InputStream readBackStream = new FileInputStream(testFile);
        byte[] writtenBytes = new byte[50];
        readBackStream.read(writtenBytes);
        readBackStream.close();

        File readBackFile = new File(testFile);
        readBackFile.delete();

        assertThat(writtenBytes, is(readBytes));
    }

    @Test
    public void iso885915EncodedDocumentCanBeTreatedAsBytes() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("iso885915EncDoc.xml");
        byte[] readBytes = new byte[50];
        is.read(readBytes);
        is.close();

        String testFile = "iso885915-test.xml";
        OutputStream os = new FileOutputStream(testFile);
        os.write(readBytes);
        os.close();

        InputStream readBackStream = new FileInputStream(testFile);
        byte[] writtenBytes = new byte[50];
        readBackStream.read(writtenBytes);
        readBackStream.close();

        File readBackFile = new File(testFile);
        readBackFile.delete();

        assertThat(writtenBytes, is(readBytes));
    }

    @Test
    public void utf8EncodedDocumentCanBeTreatedAsString() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("utf8EncDoc.xml");
        byte[] readBytes = new byte[50];
        is.read(readBytes);
        is.close();

        String interpretedBytes = new String(readBytes); // Mistake #1 - default encoding dependent

        String testFile = "utf8-test.xml";
        OutputStream os = new FileOutputStream(testFile);
        os.write(interpretedBytes.getBytes()); // Mistake #2 - default encoding dependent
        os.close();

        InputStream readBackStream = new FileInputStream(testFile);
        byte[] writtenBytes = new byte[50];
        readBackStream.read(writtenBytes);
        readBackStream.close();

        File readBackFile = new File(testFile);
        readBackFile.delete();

        assertThat(writtenBytes, is(readBytes));
    }

    @Test
    public void iso885915EncodedDocumentCanBeTreatedAsString() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("iso885915EncDoc.xml");
        byte[] readBytes = new byte[50];
        is.read(readBytes);
        is.close();

        String interpretedBytes = new String(readBytes); // Mistake #1 - default encoding dependent

        String testFile = "iso885915-test.xml";
        OutputStream os = new FileOutputStream(testFile);
        os.write(interpretedBytes.getBytes()); // Mistake #2 - default encoding dependent
        os.close();

        InputStream readBackStream = new FileInputStream(testFile);
        byte[] writtenBytes = new byte[50];
        readBackStream.read(writtenBytes);
        readBackStream.close();

        File readBackFile = new File(testFile);
        readBackFile.delete();

        assertThat(writtenBytes, is(readBytes));
    }

//    @Test
//    public void encodingTester() throws UnsupportedEncodingException {
//
//        byte[] iso8859_15 = { (byte) 0xA4 }; // euro sign
//        String utf16 = new String(iso8859_15, Charset.forName("ISO-8859-15"));
//        byte[] utf8 = utf16.getBytes(Charset.forName("UTF-8"));
//
//
//        System.out.println("Human readable representation of character:\n" + utf16);
//        System.out.println("Input data (ISO-8859-15 encoded)\n" + DatatypeConverter.printHexBinary(iso8859_15));
//        System.out.println("Java internal String (UTF-16 encoded)\n" + DatatypeConverter.printHexBinary(utf16.getBytes("UTF-16")));
//        System.out.println("String transcoded to UTF-8:\n" + DatatypeConverter.printHexBinary(utf8));
//
////        for (byte b : utf8)
////            System.out.format("%02x%n", b);
//    }



}

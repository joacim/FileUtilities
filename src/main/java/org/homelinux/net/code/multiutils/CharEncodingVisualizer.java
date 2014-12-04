package org.homelinux.net.code.multiutils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.io.UnsupportedEncodingException;

public class CharEncodingVisualizer implements DocumentListener {

    private JTextField inputField;
    private final JLabel inputLabel = new JLabel("UTF-16 chars");
    private JTextField utf8HexField;
    private final JLabel utf8HexLabel = new JLabel("UTF-8 hex");
    private JTextField utf8BinField;
    private final JLabel utf8BinLabel = new JLabel("UTF-8 binary");
    private JTextField utf16HexField;
    private final JLabel utf16HexLabel = new JLabel("UTF-16 hex");
    private JTextField iso88591HexField;
    private final JLabel iso88591HexLabel = new JLabel("ISO-8859-1 hex");
    private JTextField iso885915HexField;
    private final JLabel iso885915HexLabel = new JLabel("ISO_8859-15 hex");
    private JTextField cp1252HexField;
    private final JLabel cp1252HexLabel = new JLabel("CP1252 hex");

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("CharEncodingVisualizer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CharEncodingVisualizer visualizer = new CharEncodingVisualizer();
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);
        frame.add(visualizer.textFields(), c);

        frame.pack();
        frame.setVisible(true);
    }

    private JPanel textFields() {
        JPanel panel = new JPanel(new GridBagLayout());

        inputField = new JTextField(10);
        inputField.setEditable(true);
        inputField.getDocument().addDocumentListener(this);

        utf8HexField = new JTextField(40);
        utf8HexField.setEditable(false);
        utf16HexField = new JTextField(40);
        utf16HexField.setEditable(false);
        iso88591HexField = new JTextField(40);
        iso88591HexField.setEditable(false);
        iso885915HexField = new JTextField(40);
        iso885915HexField.setEditable(false);
        cp1252HexField = new JTextField(40);
        cp1252HexField.setEditable(false);

        utf8BinField = new JTextField(40);
        utf8BinField.setEditable(false);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.gridx = 0;
        c.anchor = GridBagConstraints.EAST;
        panel.add(inputLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        panel.add(inputField, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.EAST;
        panel.add(utf8HexLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        panel.add(utf8HexField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.EAST;
        panel.add(utf16HexLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        panel.add(utf16HexField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.EAST;
        panel.add(iso88591HexLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        panel.add(iso88591HexField, c);

        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.EAST;
        panel.add(iso885915HexLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        panel.add(iso885915HexField, c);

        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.EAST;
        panel.add(cp1252HexLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        panel.add(cp1252HexField, c);

        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.EAST;
        panel.add(utf8BinLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        panel.add(utf8BinField, c);

        return panel;
    }

    private String toBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        try {
            utf8HexField.setText(DatatypeConverter.printHexBinary(inputField.getText().getBytes("UTF-8")));
            utf16HexField.setText(DatatypeConverter.printHexBinary(inputField.getText().getBytes("UTF-16")));
            iso88591HexField.setText(DatatypeConverter.printHexBinary(inputField.getText().getBytes("ISO-8859-1")));
            iso885915HexField.setText(DatatypeConverter.printHexBinary(inputField.getText().getBytes("ISO-8859-15")));
            cp1252HexField.setText(DatatypeConverter.printHexBinary(inputField.getText().getBytes("CP1252")));
            utf8BinField.setText(toBinary(inputField.getText().getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        try {
            utf8HexField.setText(DatatypeConverter.printHexBinary(inputField.getText().getBytes("UTF-8")));
            utf16HexField.setText(DatatypeConverter.printHexBinary(inputField.getText().getBytes("UTF-16")));
            iso88591HexField.setText(DatatypeConverter.printHexBinary(inputField.getText().getBytes("ISO-8859-1")));
            iso885915HexField.setText(DatatypeConverter.printHexBinary(inputField.getText().getBytes("ISO-8859-15")));
            cp1252HexField.setText(DatatypeConverter.printHexBinary(inputField.getText().getBytes("CP1252")));
            utf8BinField.setText(toBinary(inputField.getText().getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
    }
}

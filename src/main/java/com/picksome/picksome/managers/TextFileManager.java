package com.picksome.picksome.managers;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TextFileManager {
    //TODO only last 25
    private Path filePath;

    public TextFileManager(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public String[] readLines() throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            return new String[0];
        }
        List<String> lines = Files.readAllLines(filePath);
        return lines.toArray(new String[0]);
    }

    public void prependLine(String newLine) throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        List<String> lines = Files.readAllLines(filePath);

        lines.addFirst(newLine);

        Files.write(filePath, lines);
    }

    public static void main(String[] args) {
        TextFileManager manager = new TextFileManager("./history.txt");

        try {
            String[] lines = manager.readLines();
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


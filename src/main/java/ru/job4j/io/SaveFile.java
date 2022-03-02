package ru.job4j.io;

import java.io.*;

public final class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public synchronized void  saveContent(String content) throws IOException {
        try (BufferedWriter out = new BufferedWriter(
                new FileWriter(file))) {
            for (int index = 0; index < content.length(); index += 1) {
                out.write(content.charAt(index));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

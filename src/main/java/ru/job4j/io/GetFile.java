package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class GetFile {
    private final File file;

    public GetFile(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return getContentByPredicate(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContentByPredicate(data -> data < 0x80);
    }

    public synchronized String getContentByPredicate(Predicate<Character> filter) {
        String output = "";
        try (BufferedReader in = new BufferedReader(
                new FileReader(file))) {
            int data;
            while ((data = in.read()) != -1) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

}

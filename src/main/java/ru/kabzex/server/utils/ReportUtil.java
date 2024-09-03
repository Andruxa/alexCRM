package ru.kabzex.server.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@UtilityClass
public class ReportUtil {
    private final String PATTERN = "(\\$.*?})";
    private Pattern compiled = Pattern.compile(PATTERN);
    private static final String DOCUMENT_XML_NAME = "word/document.xml";
    private static final String TEMP_DOCUMENT_XML_NAME = "word/____document.xml";

    private static String processString(String str) {
        Matcher m = compiled.matcher(str);
        while (m.find()) {
            str = str.replace(m.group(1), removeMSWTagIntoWords(m.group(1)));
        }
        return str;
    }

    private static String removeMSWTagIntoWords(String inputString) {
        return inputString.replaceAll("<[^>]+>", "");
    }

    public static void modifyTextFileInZip(File inputDoc) throws IOException {
        Path zipFilePath = inputDoc.toPath();
        try (FileSystem fs = FileSystems.newFileSystem(zipFilePath, ClassLoader.getPlatformClassLoader())) {
            Path source = fs.getPath(DOCUMENT_XML_NAME);
            Path temp = fs.getPath(TEMP_DOCUMENT_XML_NAME);
            if (Files.exists(temp)) {
                throw new IOException("temp file exists, generate another name");
            }
            Files.move(source, temp);
            streamCopyAndFix(temp, source);
            Files.delete(temp);
        }
    }

    private static void streamCopyAndFix(Path src, Path dst) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Files.newInputStream(src), StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(
                     new OutputStreamWriter(Files.newOutputStream(dst), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = processString(line);
                bw.write(line);
                bw.newLine();
            }
        }
    }

}

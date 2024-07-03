package com.glos.filestorageservice.domain.utils;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    public static InputStream createZip(List<InputStream> filesData, List<String> fileNames) throws IOException {
        if (filesData.size() != fileNames.size()) {
            throw new IllegalArgumentException("Files data and file names lists must have the same size.");
        }

        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);

        new Thread(() -> {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(pipedOutputStream)) {
                for (int i = 0; i < filesData.size(); i++) {
                    zipOutputStream.putNextEntry(new ZipEntry(fileNames.get(i)));
                    filesData.get(i).transferTo(zipOutputStream);
                    zipOutputStream.closeEntry();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    pipedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return pipedInputStream;
    }

    public static InputStream createRepositoryZip(Map<String, InputStream> filesData, Map<String, String> fileNames) throws IOException {
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);

        new Thread(() -> {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(pipedOutputStream)) {
                Set<String> uniqueFileNames = new HashSet<>();

                for (Map.Entry<String, InputStream> entry : filesData.entrySet()) {
                    String key = entry.getKey();
                    InputStream fileData = entry.getValue();
                    String fileName = fileNames.get(key);

                    if (fileName.endsWith(".placeholder")) {
                        continue;
                    }

                    if (!uniqueFileNames.add(fileName)) {
                        int index = 1;
                        String newFileName;
                        while (!uniqueFileNames.add(newFileName = fileNameWithoutExtension(fileName) + "_" + index + getFileExtension(fileName))) {
                            index++;
                        }
                        fileName = newFileName;
                    }

                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zipOutputStream.putNextEntry(zipEntry);
                    fileData.transferTo(zipOutputStream);
                    zipOutputStream.closeEntry();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    pipedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return pipedInputStream;
    }

    private static String fileNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
}
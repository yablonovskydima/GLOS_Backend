package com.glos.filemanagerservice.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil
{
    public static byte[] createZip(List<byte[]> filesData, List<String> fileNames) throws IOException
    {
        if (filesData.size() != fileNames.size())
        {
            throw new IllegalArgumentException("Files data and file names lists must have the same size.");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream))
        {
            for (int i = 0; i < filesData.size(); i++)
            {
                zipOutputStream.putNextEntry(new ZipEntry(fileNames.get(i)));
                zipOutputStream.write(filesData.get(i));
                zipOutputStream.closeEntry();
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] createRepositoryZip(Map<String, Object> filesData, Map<String, String> fileNames) throws IOException
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream))
        {
            Set<String> uniqueFileNames = new HashSet<>();

            for (Map.Entry<String, Object> entry : filesData.entrySet())
            {
                String key = entry.getKey();
                byte[] fileData = (byte[]) entry.getValue();
                String fileName = fileNames.get(key);

                if (fileName.endsWith(".placeholder"))
                {
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
                zipOutputStream.write(fileData);
                zipOutputStream.closeEntry();
            }
        }

        return byteArrayOutputStream.toByteArray();
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

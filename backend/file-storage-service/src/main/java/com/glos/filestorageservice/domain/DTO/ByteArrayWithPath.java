package com.glos.filestorageservice.domain.DTO;

public class ByteArrayWithPath
{
    private String filePath;
    private byte[] file;
    private String contentType;

    public ByteArrayWithPath(String filePath, byte[] file, String contentType) {
        this.filePath = filePath;
        this.file = file;
        this.contentType = contentType;
    }

    public ByteArrayWithPath() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}

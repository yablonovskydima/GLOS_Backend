package com.glos.filemanagerservice.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class FileUpdateRequest
{
    public static class FileNode
    {
        private Long id;
        private String fileBody;
        private MultipartFile fileData;

        public FileNode(Long id, String fileBody, MultipartFile fileData) {
            this.id = id;
            this.fileBody = fileBody;
            this.fileData = fileData;
        }

        public FileNode() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFileBody() {
            return fileBody;
        }

        public void setFileBody(String fileBody) {
            this.fileBody = fileBody;
        }

        public MultipartFile getFileData() {
            return fileData;
        }

        public void setFileData(MultipartFile fileData) {
            this.fileData = fileData;
        }
    }

    private List<FileNode> files;

    public FileUpdateRequest(List<FileNode> fileNodes) {
        this.files = fileNodes;
    }

    public FileUpdateRequest() {
        this.files = new ArrayList<>();
    }

    public List<FileNode> getFiles() {
        return files;
    }

    public void setFiles(List<FileNode> fileNodes) {
        this.files = fileNodes;
    }
}

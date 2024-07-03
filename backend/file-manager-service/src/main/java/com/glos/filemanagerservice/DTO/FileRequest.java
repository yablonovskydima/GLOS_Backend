package com.glos.filemanagerservice.DTO;

import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.validation.OnCreate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileRequest
{
    @Valid
    public static class FileNode
    {
        @NotEmpty(message = "can't be empty",
                groups = {OnCreate.class})
        private String fileData;

        @NotNull(message = "can't be null",
                groups = {OnCreate.class})
        private MultipartFile file;

        public FileNode() {
        }

        public FileNode(String fileData, MultipartFile file) {
            this.fileData = fileData;
            this.file = file;
        }


        public String getFileData() {
            return fileData;
        }

        public void setFileData(String fileData) {
            this.fileData = fileData;
        }

        public MultipartFile getFile() {
            return file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }
    }


    private List<FileNode> files;

    public FileRequest() {
    }

    public FileRequest(List<FileNode> files) {
        this.files = files;
    }

    public List<FileNode> getFiles() {
        return files;
    }

    public void setFiles(List<FileNode> files) {
        this.files = files;
    }
}

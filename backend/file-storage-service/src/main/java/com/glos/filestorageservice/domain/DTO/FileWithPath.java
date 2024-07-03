package com.glos.filestorageservice.domain.DTO;

import org.springframework.web.multipart.MultipartFile;

public class FileWithPath {
        private String filePath;
        private MultipartFile file;

        public FileWithPath() {
        }

        public FileWithPath(String filePath, MultipartFile file) {
            this.filePath = filePath;
            this.file = file;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public MultipartFile getFile() {
            return file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }
    }
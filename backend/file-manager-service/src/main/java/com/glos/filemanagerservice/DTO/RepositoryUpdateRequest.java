package com.glos.filemanagerservice.DTO;

import com.glos.filemanagerservice.entities.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoryUpdateRequest
{
    public static class RepositoryNode
    {
        private Long id;
        private RepositoryUpdateDTO repository;

        public RepositoryNode() {
        }

        public RepositoryNode(Long id, RepositoryUpdateDTO repository) {
            this.id = id;
            this.repository = repository;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public RepositoryUpdateDTO getRepository() {
            return repository;
        }

        public void setRepository(RepositoryUpdateDTO repository) {
            this.repository = repository;
        }
    }

    private List<RepositoryNode> repositories;

    public RepositoryUpdateRequest() {
        this.repositories = new ArrayList<>();
    }

    public RepositoryUpdateRequest(List<RepositoryNode> repositories) {
        this.repositories = repositories;
    }

    public List<RepositoryNode> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<RepositoryNode> repositories) {
        this.repositories = repositories;
    }
}

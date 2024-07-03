package com.glos.filemanagerservice.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Page<T> {

    public static class PageBuilder<T> {
        private List<T> content;
        private int number;
        private int size;
        private long totalElements;
        private String sortBy;
        private String sortDir;
        private boolean sorted;

        public PageBuilder(List<T> content) {
            this.content = content;
        }

        public PageBuilder<T> setContent(List<T> content) {
            this.content = content;
            return this;
        }

        public PageBuilder<T> setNumber(int number) {
            this.number = number;
            return this;
        }

        public PageBuilder<T> setSize(int size) {
            this.size = size;
            return this;
        }

        public PageBuilder<T> setTotalElements(long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public PageBuilder<T> setSortBy(String sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public PageBuilder<T> setSortDir(String sortDir) {
            this.sortDir = sortDir;
            return this;
        }

        public PageBuilder<T> setSorted(boolean sorted) {
            this.sorted = sorted;
            return this;
        }

        public Page<T> build() {
            return new Page<>(content, number, size, totalElements, sortBy, sortDir, sorted);
        }
    }

    public static <T> PageBuilder<T> builder(List<T> content) {
        return new PageBuilder<T>(content);
    }

    private List<T> content;
    private int number;
    private int size;
    private long totalElements;
    private String sortBy;
    private String sortDir;
    private boolean sorted;

    public Page() {
    }

    public Page(List<T> content, int number, int size, long totalElements, String sortBy, String sortDir, boolean sorted) {
        this.content = content;
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        this.sortBy = sortBy;
        this.sortDir = sortDir;
        this.sorted = sorted;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setSortPattern(String sort) {
        String[] sortElements = sort.split(",");
        this.sortBy = sortElements[0];
        this.sortDir = sortElements[1];
        this.sorted = true;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public <R> Page<R> map(Function<T, R> function) {
        final List<R> newContent = new ArrayList<>();
        final Page<R> pageNew = new Page<>(newContent, number, size, totalElements, sortBy, sortDir, sorted);
        pageNew.setTotalElements(content.size());
        content.forEach(t -> newContent.add(function.apply(t)));
        return pageNew;
    }
    public Stream<T> stream() {
        return content.stream();
    }

}

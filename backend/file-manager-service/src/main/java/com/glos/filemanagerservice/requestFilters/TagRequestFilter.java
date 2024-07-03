package com.glos.filemanagerservice.requestFilters;

public class TagRequestFilter
{
    private Long id;
    private String name;

    private int page;
    private int size;
    private String sort;

    public TagRequestFilter(Long id, String name, int page, int size, String sort) {
        this.id = id;
        this.name = name;
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public TagRequestFilter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}

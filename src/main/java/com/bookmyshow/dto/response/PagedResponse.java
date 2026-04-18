package com.bookmyshow.dto.response;

import java.util.List; public class PagedResponse<T> {
    private List<T> content;

    private int pageNumber;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public List<T> getContent() { return this.content; }
    public int getPageNumber() { return this.pageNumber; }
    public int getPageSize() { return this.pageSize; }
    public long getTotalElements() { return this.totalElements; }
    public int getTotalPages() { return this.totalPages; }
    public boolean isLast() { return this.last; }

    public void setContent(List<T> content) { this.content = content; }
    public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public void setLast(boolean last) { this.last = last; }

    public PagedResponse() {}

    public PagedResponse(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages, boolean last) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public static <T> PagedResponseBuilder<T> builder() { return new PagedResponseBuilder<>(); }

    public static class PagedResponseBuilder<T> {
        private List<T> content;
        private int pageNumber;
        private int pageSize;
        private long totalElements;
        private int totalPages;
        private boolean last;

        public PagedResponseBuilder<T> content(List<T> content) { this.content = content; return this; }
        public PagedResponseBuilder<T> pageNumber(int pageNumber) { this.pageNumber = pageNumber; return this; }
        public PagedResponseBuilder<T> pageSize(int pageSize) { this.pageSize = pageSize; return this; }
        public PagedResponseBuilder<T> totalElements(long totalElements) { this.totalElements = totalElements; return this; }
        public PagedResponseBuilder<T> totalPages(int totalPages) { this.totalPages = totalPages; return this; }
        public PagedResponseBuilder<T> last(boolean last) { this.last = last; return this; }

        public PagedResponse<T> build() { return new PagedResponse<T>(content, pageNumber, pageSize, totalElements, totalPages, last); }
    }

}

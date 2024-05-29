package com.berk2s.ds.api.infrastructure.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse<T> extends RepresentationModel<PaginationResponse<T>> {
    private List<T> data;
    private Pagination pagination;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Pagination {
        private int pageNumber;
        private int pageSize;
        private int totalPages;
        private long totalElements;
        private int total;
        private int size;
        private int currentPage;
        private int numberOfElements;
        private boolean isLast;
        private boolean isFirst;
        private boolean isSorted;
        private boolean empty;
        private boolean hasNext;
        private boolean hasPrev;
    }

    public static Pagination fromPage(final Page page) {
        return Pagination.builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .currentPage(page.getPageable().getPageNumber())
                .numberOfElements(page.getNumberOfElements())
                .isLast(page.isLast())
                .isFirst(page.isFirst())
                .empty(page.isEmpty())
                .isSorted(page.getPageable().getSort().isSorted())
                .hasNext(!page.isLast())
                .hasPrev(!page.isFirst())
                .total(page.getTotalPages())
                .build();
    }
}


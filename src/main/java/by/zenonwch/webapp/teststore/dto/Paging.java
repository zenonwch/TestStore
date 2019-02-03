package by.zenonwch.webapp.teststore.dto;

import lombok.Getter;

@Getter
public class Paging {
    private static final int MAX_BUTTONS_TO_SHOW = 5;

    private final int totalPages;
    private final int currentPage;
    private int startPage;
    private int endPage;

    public Paging(final int totalPages, final int currentPage) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;

        startPage = 1;
        endPage = MAX_BUTTONS_TO_SHOW;

        if (totalPages < MAX_BUTTONS_TO_SHOW) {
            endPage = totalPages;
        }

        final int middlePageNumber = MAX_BUTTONS_TO_SHOW / 2;
        if (currentPage > middlePageNumber) {
            startPage = currentPage - middlePageNumber;
            endPage = currentPage + middlePageNumber;
        }

        if (endPage > totalPages) {
            startPage = totalPages - MAX_BUTTONS_TO_SHOW + 1;
            endPage = totalPages;
        }
    }
}
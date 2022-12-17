package com.sovathc.mongodemocrud.common.response;

import com.sovathc.mongodemocrud.common.request.PageableRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableResponse<T> {
    protected static final long DEFAULT_RECORDS = NumberUtils.LONG_ZERO;

    private Long records = DEFAULT_RECORDS;

    private List<T> items;

    private Integer pages;
    private Integer page;

    public PageableResponse(Integer records, List<T> items, PageableRequest pageable) {
        this((long) records, items, pageable);
    }

    public PageableResponse(Long records, List<T> items, PageableRequest pageable) {
        if (ObjectUtils.isNotEmpty(records)) {
            this.records = records;
        }
        this.items = items;
        this.pages = (int) Math.ceil((double) this.records / pageable.getRpp());
        this.page = pageable.getPage();
    }
}

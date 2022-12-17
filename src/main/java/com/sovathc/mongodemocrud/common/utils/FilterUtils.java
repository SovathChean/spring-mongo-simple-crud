package com.sovathc.mongodemocrud.common.utils;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class FilterUtils {
    public static <Entity> Criteria generate(Criteria initCriteria, Class<Entity> entityClass, String filterString)
    {
        List<Criteria> expressions = new ArrayList<>();
        String[] filters = filterString.split("&");
        for (String filter: filters)
        {
            String[] fields = filter.split("=");
            String key = PageableUtils.getColumn(fields[0], entityClass);
            String value = fields[1];
            expressions.add(Criteria.where(key).is(value));
        }

        return initCriteria.andOperator(expressions.toArray(new Criteria[0]));
    }
}

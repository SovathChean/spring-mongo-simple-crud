package com.sovathc.mongodemocrud.common.utils;

import com.google.common.base.CaseFormat;
import com.sovathc.mongodemocrud.common.request.PageableRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.Field;
import java.util.List;

public class PageableUtils {

    public static <Entity> Page<Entity> generate(MongoTemplate template, Class<Entity> entityClass, Criteria criteria, PageableRequest request)
    {
        Query query = new Query();
        PageRequest pageRequest = PageRequest.of(request.getPage()-1, request.getRpp());
        //add criteria
        if(ObjectUtils.isNotEmpty(criteria))
            query.addCriteria(criteria);
        //pagination with sort
        if(ObjectUtils.allNotNull(request.getDirection(), request.getProperty()))
        {
            String column = getColumn(request.getProperty(), entityClass);
            if(ObjectUtils.isNotEmpty(column))
            {
                Sort sort = Sort.by(request.getDirection(), column);
                pageRequest = PageRequest.of(request.getPage() - 1, request.getRpp(), sort);
            }
        }

        long record = template.count(query, entityClass);
        List<Entity> items = template.find(query.with(pageRequest), entityClass);


        return new PageImpl<>(items, pageRequest, record);
    }

    public static <Entity> String getColumn(String fieldName, Class<Entity> entityClass)
    {
        String column  = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, fieldName);
        for (Field field: entityClass.getDeclaredFields())
        {
            if(field.getName().equals(column))
                return column;
        }

        for (Field field: entityClass.getSuperclass().getDeclaredFields())
        {
            if(field.getName().equals(column))
                return column;
        }
        return null;
    }
}

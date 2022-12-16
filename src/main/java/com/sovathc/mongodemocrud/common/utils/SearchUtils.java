package com.sovathc.mongodemocrud.common.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.lang.reflect.Field;
import java.util.List;

public class SearchUtils {
    public static <SearchDTO> Criteria generate(MongoTemplate template, Class<SearchDTO> entityClass, String keywords )
    {
        List<Criteria> expressions = null;
        Criteria orCriteria = new Criteria();
        if(ObjectUtils.isEmpty(keywords))
            return null;

        for(Field field: entityClass.getDeclaredFields())
        {
            expressions.add(Criteria.where(field.getName()).regex(".*" + keywords+ ".*", "i"));
        }

        return orCriteria.orOperator(expressions.toArray(new Criteria[0]));
    }
}

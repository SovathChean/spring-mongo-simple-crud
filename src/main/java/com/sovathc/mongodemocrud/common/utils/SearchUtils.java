package com.sovathc.mongodemocrud.common.utils;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SearchUtils {
    public static <SearchDTO> Criteria generate(Criteria initCriteria, Class<SearchDTO> entityClass, String keywords )
    {
        List<Criteria> expressions = new ArrayList<>();
        for(Field field: entityClass.getDeclaredFields())
        {
            expressions.add(Criteria.where(field.getName()).regex(".*" + keywords+ ".*", "i"));
        }

        return initCriteria.orOperator(expressions.toArray(new Criteria[0]));
    }
}

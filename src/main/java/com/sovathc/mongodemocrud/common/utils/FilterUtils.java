package com.sovathc.mongodemocrud.common.utils;

import com.sovathc.mongodemocrud.common.constants.SysHttpResultCode;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class FilterUtils {
    public static <Entity> void generate(Criteria initCriteria, Class<Entity> entityClass, String filterString) throws BusinessException {
        List<Criteria> expressions = new ArrayList<>();
        String[] filters = filterString.split("&");
        for (String filter: filters)
        {
            String[] fields = filter.split("=");
            String key = PageableUtils.getColumn(fields[0], entityClass);
            String value = fields[1];
            if(ObjectUtils.allNull(key, value))
                throw new BusinessException(SysHttpResultCode.ERROR_400.getCode(), "Filter string is invalid");
            expressions.add(Criteria.where(key).is(value));
        }

        initCriteria.andOperator(expressions.toArray(new Criteria[0]));
    }
}

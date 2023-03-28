package com.sovathc.mongodemocrud.common.utils;

import com.sovathc.mongodemocrud.common.constants.SysHttpResultCode;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.ObjectUtils;

public class MongoEntityUtils {
    public static <Entity> Entity findEntityById(MongoTemplate template, Class<Entity> entityClass, String id) throws BusinessException {
        Entity entity = template.findById(id, entityClass);

        if(ObjectUtils.isEmpty(entity))
            throw new BusinessException(SysHttpResultCode.ERROR_400.getCode(), SysHttpResultCode.ERROR_400.getDescription());

        return entity;
    }
}

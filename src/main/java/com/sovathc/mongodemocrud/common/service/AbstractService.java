package com.sovathc.mongodemocrud.common.service;

import java.util.List;

public interface AbstractService <DTO>{
    default void create(DTO dto) {};
    default void update(String id, DTO dto) {};
    default void delete(String id) {};
    default DTO findOne(String id) {return null;}
    default List<DTO> findAll() {return null;}
}

package com.sovathc.mongodemocrud.common.controller;

import com.sovathc.mongodemocrud.common.response.PageableResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface AbstractController <ITEM_RESPONSE, RESPONSE, CRATED_REQUEST, UPDATE_REQUEST, PAGEABLE_REQUEST>{

    @PostMapping("/create")
    default ResponseMessage<RESPONSE> create(@Valid  @RequestBody CRATED_REQUEST request) {
        return new ResponseBuilderMessage<RESPONSE>()
                .success()
                .addData(null)
                .build();
    }
    @PostMapping("/update")
    default ResponseMessage<RESPONSE> update(String id, @Valid @RequestBody UPDATE_REQUEST request) {
        return new ResponseBuilderMessage<RESPONSE>()
                .success()
                .addData(null)
                .build();
    };
    @PostMapping("/delete/{id}")
    default ResponseMessage<Void> delete(@PathVariable String id)
    {
        return new ResponseBuilderMessage<Void>()
                .success()
                .build();
    }
    @GetMapping("/find-one/{id}")
    default ResponseMessage<RESPONSE> findOne(@PathVariable String id) {

        return new ResponseBuilderMessage<RESPONSE>()
                .success()
                .addData(null)
                .build();
    }
    @GetMapping("/find-with-page")
    default ResponseMessage<PageableResponse<ITEM_RESPONSE>> findWithPage(@Valid PAGEABLE_REQUEST pageableRequest) {
        return new ResponseBuilderMessage<PageableResponse<ITEM_RESPONSE>>()
                .success()
                .addData(null)
                .build();
    }
}

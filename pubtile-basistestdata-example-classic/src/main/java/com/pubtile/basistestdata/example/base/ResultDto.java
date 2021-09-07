package com.pubtile.basistestdata.example.base;

import lombok.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.io.Serializable;

/**
 * 通用Result DTO
 *
 * @author jiayan
 * @version 0.6.17 2021-09-03
 * @since 0.6.17 2021-09-03
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto<T> implements Serializable {
    public static ResultDto<Void> SUCCESS_WITHOUT_DATA = ResultDto.<Void>builder().success(true).build();
    public static ResultConverter CONVERTER = Mappers.getMapper(ResultConverter.class);
    boolean success ;
    String errorMessage;
    T data;

    @Mapper
    public abstract static class ResultConverter {
        public abstract ResultDto result2Result(ResultDto resultDto);
    }
}
package com.pubtile.basistestdata.example.base.dto.update;

import com.pubtile.basistestdata.example.base.po.BasePO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class BaseUpdateRequestDTOMapper {
    public static BaseUpdateRequestDTOMapper INSTANCE = Mappers.getMapper(BaseUpdateRequestDTOMapper.class);

    public <T extends BasePO> T convertToBeUpdatedPO(@MappingTarget T toBeUpdatePo, IUpdateRequestDTO requestDto, T currentPo){
        //主键
        toBeUpdatePo.setId(currentPo.getId());
        //版本号，用于乐观锁
        toBeUpdatePo.setRevision(currentPo.getRevision());
        return toBeUpdatePo;

    }
}

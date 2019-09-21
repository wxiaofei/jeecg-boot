package org.jeecg.modules.tms.material.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.tms.material.entity.TmsCateMaterial;
import org.jeecg.modules.tms.material.mapper.TmsCateMaterialMapper;
import org.jeecg.modules.tms.material.service.ITmsCateMaterialService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TmsCateMaterialServieImpl extends ServiceImpl<TmsCateMaterialMapper, TmsCateMaterial> implements ITmsCateMaterialService {
    @Override
    public List<SysUser> queryMaterialByCateId(String cateId) {
        return null;
    }
}

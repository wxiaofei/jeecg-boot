package org.jeecg.modules.tms.material.service.impl;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.tms.material.entity.TmsBdMaterial;
import org.jeecg.modules.tms.material.entity.TmsCateMaterial;
import org.jeecg.modules.tms.material.mapper.TmsBdMaterialMapper;
import org.jeecg.modules.tms.material.mapper.TmsCateMaterialMapper;
import org.jeecg.modules.tms.material.service.ITmsBdMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 商品管理
 * @Author: jeecg-boot
 * @Date:   2019-09-10
 * @Version: V1.0
 */
@Service
public class TmsBdMaterialServiceImpl extends ServiceImpl<TmsBdMaterialMapper, TmsBdMaterial> implements ITmsBdMaterialService {

    @Autowired
    private TmsCateMaterialMapper tmsCateMaterialMapper;

    @Override
    public void saveCateMaterial(TmsBdMaterial material) {

        this.save(material);

        if (oConvertUtils.isNotEmpty(material.getCateId())){

            TmsCateMaterial  cateMaterial = new TmsCateMaterial(material.getId(),material.getCateId());
            tmsCateMaterialMapper.insert(cateMaterial);
        }

    }
}

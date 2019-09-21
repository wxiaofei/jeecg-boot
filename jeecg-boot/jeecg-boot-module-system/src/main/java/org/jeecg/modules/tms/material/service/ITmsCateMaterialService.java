package org.jeecg.modules.tms.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.tms.material.entity.TmsCateMaterial;

import java.util.List;

public interface ITmsCateMaterialService extends IService<TmsCateMaterial> {

    List<SysUser> queryMaterialByCateId(String cateId);
}

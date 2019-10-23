package org.jeecg.modules.tms.contract.service;

import org.jeecg.modules.tms.contract.entity.TmsBdContract;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 合同管理
 * @Author: jeecg-boot
 * @Date:   2019-09-23
 * @Version: V1.0
 */
public interface ITmsBdContractService extends IService<TmsBdContract> {

    List<Map<String, Object>> selectByCondition();
}

package org.jeecg.modules.tms.contract.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.tms.contract.entity.TmsBdContract;
import org.jeecg.modules.tms.contract.mapper.TmsBdContractMapper;
import org.jeecg.modules.tms.contract.service.ITmsBdContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 合同管理
 * @Author: jeecg-boot
 * @Date:   2019-09-23
 * @Version: V1.0
 */
@Service
public class TmsBdContractServiceImpl extends ServiceImpl<TmsBdContractMapper, TmsBdContract> implements ITmsBdContractService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @DS("multi-datasource2")
    public List<Map<String, Object>> selectByCondition() {
        List<Map<String, Object>>  result  =jdbcTemplate.queryForList("select   *  from  bd_corp  where  pk_corp ='1021'");
        return  result;
    }
}

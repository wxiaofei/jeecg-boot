package org.jeecg.modules.demo.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 订单
 * @Author: jeecg-boot
 * @Date:  2019-02-15
 * @Version: V1.0
 */
@Data
@TableName("ct_sale")
public class CTSale implements Serializable {
    private static final long serialVersionUID = 1L;

	/**订单号*/
	private String vbillcode;

	private String ctname;
	/**订单类型*/
	private String pkCtSale;
	/**订单日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dbilldate;

}

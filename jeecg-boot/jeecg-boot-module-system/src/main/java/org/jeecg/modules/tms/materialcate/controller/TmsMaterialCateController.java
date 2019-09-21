package org.jeecg.modules.tms.materialcate.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.system.model.DepartIdModel;
import org.jeecg.modules.system.model.SysDepartTreeModel;
import org.jeecg.modules.tms.materialcate.entity.MaterialCateIdModel;
import org.jeecg.modules.tms.materialcate.entity.TmsMaterialCate;
import org.jeecg.modules.tms.materialcate.entity.TmsMaterialCateTreeModel;
import org.jeecg.modules.tms.materialcate.service.ITmsMaterialCateService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 物料分类
 * @Author: jeecg-boot
 * @Date:   2019-09-16
 * @Version: V1.0
 */
@RestController
@RequestMapping("/materialcate/tmsMaterialCate")
@Slf4j
public class TmsMaterialCateController {
	@Autowired
	private ITmsMaterialCateService tmsMaterialCateService;
	
	/**
	  * 分页列表查询
	 * @param tmsMaterialCate
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/rootList")
	public Result<IPage<TmsMaterialCate>> queryPageList(TmsMaterialCate tmsMaterialCate,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		if(oConvertUtils.isEmpty(tmsMaterialCate.getPid())){
			tmsMaterialCate.setPid("0");
		}
		Result<IPage<TmsMaterialCate>> result = new Result<IPage<TmsMaterialCate>>();
		QueryWrapper<TmsMaterialCate> queryWrapper = QueryGenerator.initQueryWrapper(tmsMaterialCate, req.getParameterMap());
		Page<TmsMaterialCate> page = new Page<TmsMaterialCate>(pageNo, pageSize);
		IPage<TmsMaterialCate> pageList = tmsMaterialCateService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	@GetMapping(value = "/childList")
	public Result<List<TmsMaterialCate>> queryPageList(TmsMaterialCate tmsMaterialCate,HttpServletRequest req) {
		Result<List<TmsMaterialCate>> result = new Result<List<TmsMaterialCate>>();
		QueryWrapper<TmsMaterialCate> queryWrapper = QueryGenerator.initQueryWrapper(tmsMaterialCate, req.getParameterMap());
		List<TmsMaterialCate> list = tmsMaterialCateService.list(queryWrapper);
		result.setSuccess(true);
		result.setResult(list);
		return result;
	}



	 /**
	  * 查询数据 查出所有部门,并以树结构数据格式响应给前端
	  *
	  * @return
	  */
	 @RequestMapping(value = "/queryTreeList", method = RequestMethod.GET)
	 public Result<List<TmsMaterialCateTreeModel>> queryTreeList() {
		 Result<List<TmsMaterialCateTreeModel>> result = new Result<>();
		 try {
			 // 从内存中读取
			 List<TmsMaterialCateTreeModel> list = tmsMaterialCateService.queryTreeList();
			 result.setResult(list);
			 result.setSuccess(true);
		 } catch (Exception e) {
			 log.error(e.getMessage(),e);
		 }
		 return result;
	 }

	 /**
	  * 查询数据 添加或编辑页面对该方法发起请求,以树结构形式加载所有部门的名称,方便用户的操作
	  *
	  * @return
	  */
	 @RequestMapping(value = "/queryIdTree", method = RequestMethod.GET)
	 public Result<List<MaterialCateIdModel>> queryIdTree() {

		 Result<List<MaterialCateIdModel>> result = new Result<>();
		 try {
			 List<MaterialCateIdModel> list = tmsMaterialCateService.queryCateIdTreeList();
			 result.setResult(list);
			 result.setSuccess(true);
		 } catch (Exception e) {
			 log.error(e.getMessage(),e);
		 }
		 return result;
	 }



	 /**
	  * <p>
	  * 部门搜索功能方法,根据关键字模糊搜索相关部门
	  * </p>
	  *
	  * @param keyWord
	  * @return
	  */
	 @RequestMapping(value = "/searchBy", method = RequestMethod.GET)
	 public Result<List<TmsMaterialCateTreeModel>> searchBy(@RequestParam(name = "keyWord", required = true) String keyWord) {
		 Result<List<TmsMaterialCateTreeModel>> result = new Result<List<TmsMaterialCateTreeModel>>();
		 try {
			 List<TmsMaterialCateTreeModel> treeList = tmsMaterialCateService.searhBy(keyWord);
			 if (treeList.size() == 0 || treeList == null) {
				 throw new Exception();
			 }
			 result.setSuccess(true);
			 result.setResult(treeList);
			 return result;
		 } catch (Exception e) {
			 e.fillInStackTrace();
			 result.setSuccess(false);
			 result.setMessage("查询失败或没有您想要的任何数据!");
			 return result;
		 }
	 }

	 /**
	  *   添加
	 * @param tmsMaterialCate
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<TmsMaterialCate> add(@RequestBody TmsMaterialCate tmsMaterialCate) {
		Result<TmsMaterialCate> result = new Result<TmsMaterialCate>();
		try {
			tmsMaterialCateService.addTmsMaterialCate(tmsMaterialCate);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param tmsMaterialCate
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<TmsMaterialCate> edit(@RequestBody TmsMaterialCate tmsMaterialCate) {
		Result<TmsMaterialCate> result = new Result<TmsMaterialCate>();
		try {
			tmsMaterialCateService.updateTmsMaterialCate(tmsMaterialCate);
			result.success("修改成功!");
		} catch (Exception e) {
			result.error500(e.getMessage());
		}
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<TmsMaterialCate> delete(@RequestParam(name="id",required=true) String id) {
		Result<TmsMaterialCate> result = new Result<TmsMaterialCate>();
		try {
			tmsMaterialCateService.deleteTmsMaterialCate(id);
			result.success("删除成功!");
		} catch (Exception e) {
			result.error500(e.getMessage());
		}
		return result;
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<TmsMaterialCate> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<TmsMaterialCate> result = new Result<TmsMaterialCate>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.tmsMaterialCateService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<TmsMaterialCate> queryById(@RequestParam(name="id",required=true) String id) {
		Result<TmsMaterialCate> result = new Result<TmsMaterialCate>();
		TmsMaterialCate tmsMaterialCate = tmsMaterialCateService.getById(id);
		if(tmsMaterialCate==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(tmsMaterialCate);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, TmsMaterialCate tmsMaterialCate) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<TmsMaterialCate> queryWrapper = QueryGenerator.initQueryWrapper(tmsMaterialCate, request.getParameterMap());
      List<TmsMaterialCate> pageList = tmsMaterialCateService.list(queryWrapper);
      // Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      // 过滤选中数据
      String selections = request.getParameter("selections");
      if(oConvertUtils.isEmpty(selections)) {
    	  mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  List<TmsMaterialCate> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
    	  mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
      }
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "物料分类列表");
      mv.addObject(NormalExcelConstants.CLASS, TmsMaterialCate.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("物料分类列表数据", "导出人:Jeecg", "导出信息"));
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<TmsMaterialCate> listTmsMaterialCates = ExcelImportUtil.importExcel(file.getInputStream(), TmsMaterialCate.class, params);
              tmsMaterialCateService.saveBatch(listTmsMaterialCates);
              return Result.ok("文件导入成功！数据行数:" + listTmsMaterialCates.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }

}

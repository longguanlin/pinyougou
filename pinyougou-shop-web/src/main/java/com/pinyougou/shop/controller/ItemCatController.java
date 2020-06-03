package com.pinyougou.shop.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.sellergoods.service.ItemCatService;

import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/cat")
public class ItemCatController {

	@Reference
	private ItemCatService catService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbItemCat> findAll(){			
		return catService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return catService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param cat
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbItemCat cat){
		try {
			catService.add(cat);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param cat
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbItemCat cat){
		try {
			catService.update(cat);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbItemCat findOne(Long id){
		return catService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			catService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbItemCat cat, int page, int rows  ){
		return catService.findPage(cat, page, rows);		
	}
	
	
	/**
	 * 根据上级ID查询商品分类列表
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/findByParentId")
	public List<TbItemCat> findByParentId(Long parentId) {
		return catService.findByParentId(parentId);
	}
	
}

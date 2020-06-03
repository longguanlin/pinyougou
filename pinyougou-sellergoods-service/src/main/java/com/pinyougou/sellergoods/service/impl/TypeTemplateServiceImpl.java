package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.pojo.TbTypeTemplateExample;
import com.pinyougou.pojo.TbTypeTemplateExample.Criteria;
import com.pinyougou.sellergoods.service.TypeTemplateService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TbTypeTemplateMapper templateMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbTypeTemplate> findAll() {
		return templateMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbTypeTemplate> page=   (Page<TbTypeTemplate>) templateMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbTypeTemplate template) {
		templateMapper.insert(template);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbTypeTemplate template){
		templateMapper.updateByPrimaryKey(template);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbTypeTemplate findOne(Long id){
		return templateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			templateMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbTypeTemplate template, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbTypeTemplateExample example=new TbTypeTemplateExample();
		Criteria criteria = example.createCriteria();
		
		if(template!=null){			
						if(template.getName()!=null && template.getName().length()>0){
				criteria.andNameLike("%"+template.getName()+"%");
			}
			if(template.getSpecIds()!=null && template.getSpecIds().length()>0){
				criteria.andSpecIdsLike("%"+template.getSpecIds()+"%");
			}
			if(template.getBrandIds()!=null && template.getBrandIds().length()>0){
				criteria.andBrandIdsLike("%"+template.getBrandIds()+"%");
			}
			if(template.getCustomAttributeItems()!=null && template.getCustomAttributeItems().length()>0){
				criteria.andCustomAttributeItemsLike("%"+template.getCustomAttributeItems()+"%");
			}
	
		}
		
		Page<TbTypeTemplate> page= (Page<TbTypeTemplate>)templateMapper.selectByExample(example);		
		
		//缓存处理
		saveToRedis();
		return new PageResult(page.getTotal(), page.getResult());
	}
	
	@Autowired
	private RedisTemplate redisTemplate;	
	
	/**
	 * 将品牌列表与规格列表放入缓存
	 */
	private void saveToRedis() {
		List<TbTypeTemplate> templateList = findAll();
		for (TbTypeTemplate template : templateList) {
			//得到品牌列表
			List<Map> brandList = JSON.parseArray(template.getBrandIds(),Map.class);
			redisTemplate.boundHashOps("brandList").put(template.getId(), brandList);
			//得到规格列表
			List<Map> specList = findSpecList(template.getId());
			redisTemplate.boundHashOps("specList").put(template.getId(), specList);
			
		}
		System.out.println("缓存品牌与规格列表");
	}

	@Override
	public List<Map> selectOptionList() {
		// TODO Auto-generated method stub
		return templateMapper.selectOptionList();
	}

	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	@Override
	public List<Map> findSpecList(Long id) {
		//查询模板
				TbTypeTemplate typeTemplate = templateMapper.selectByPrimaryKey(id);
				
				List<Map> list = JSON.parseArray(typeTemplate.getSpecIds(), Map.class)  ;
				for(Map map:list){
					//查询规格选项列表
					TbSpecificationOptionExample example=new TbSpecificationOptionExample();
					com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
					criteria.andSpecIdEqualTo( new Long( (Integer)map.get("id") ) );
					List<TbSpecificationOption> options = specificationOptionMapper.selectByExample(example);
					map.put("options", options);
				}		
				return list;
	}
	
}

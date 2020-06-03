package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojo.TbSpecificationOptionExample.Criteria;
import com.pinyougou.sellergoods.service.SpecificationOptionService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class SpecificationOptionServiceImpl implements SpecificationOptionService {

	@Autowired
	private TbSpecificationOptionMapper optionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecificationOption> findAll() {
		return optionMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecificationOption> page=   (Page<TbSpecificationOption>) optionMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSpecificationOption option) {
		optionMapper.insert(option);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSpecificationOption option){
		optionMapper.updateByPrimaryKey(option);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbSpecificationOption findOne(Long id){
		return optionMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			optionMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecificationOption option, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationOptionExample example=new TbSpecificationOptionExample();
		Criteria criteria = example.createCriteria();
		
		if(option!=null){			
						if(option.getOptionName()!=null && option.getOptionName().length()>0){
				criteria.andOptionNameLike("%"+option.getOptionName()+"%");
			}
	
		}
		
		Page<TbSpecificationOption> page= (Page<TbSpecificationOption>)optionMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}

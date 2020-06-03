package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbGoodsDescExample;
import com.pinyougou.pojo.TbGoodsDescExample.Criteria;
import com.pinyougou.sellergoods.service.GoodsDescService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsDescServiceImpl implements GoodsDescService {

	@Autowired
	private TbGoodsDescMapper descMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoodsDesc> findAll() {
		return descMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoodsDesc> page=   (Page<TbGoodsDesc>) descMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbGoodsDesc desc) {
		descMapper.insert(desc);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbGoodsDesc desc){
		descMapper.updateByPrimaryKey(desc);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbGoodsDesc findOne(Long id){
		return descMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			descMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbGoodsDesc desc, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsDescExample example=new TbGoodsDescExample();
		Criteria criteria = example.createCriteria();
		
		if(desc!=null){			
						if(desc.getIntroduction()!=null && desc.getIntroduction().length()>0){
				criteria.andIntroductionLike("%"+desc.getIntroduction()+"%");
			}
			if(desc.getSpecificationItems()!=null && desc.getSpecificationItems().length()>0){
				criteria.andSpecificationItemsLike("%"+desc.getSpecificationItems()+"%");
			}
			if(desc.getCustomAttributeItems()!=null && desc.getCustomAttributeItems().length()>0){
				criteria.andCustomAttributeItemsLike("%"+desc.getCustomAttributeItems()+"%");
			}
			if(desc.getItemImages()!=null && desc.getItemImages().length()>0){
				criteria.andItemImagesLike("%"+desc.getItemImages()+"%");
			}
			if(desc.getPackageList()!=null && desc.getPackageList().length()>0){
				criteria.andPackageListLike("%"+desc.getPackageList()+"%");
			}
			if(desc.getSaleService()!=null && desc.getSaleService().length()>0){
				criteria.andSaleServiceLike("%"+desc.getSaleService()+"%");
			}
	
		}
		
		Page<TbGoodsDesc> page= (Page<TbGoodsDesc>)descMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}

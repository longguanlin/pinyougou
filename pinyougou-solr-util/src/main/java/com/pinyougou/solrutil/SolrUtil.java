package com.pinyougou.solrutil;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.pojo.TbItemExample.Criteria;

@Component
public class SolrUtil {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private SolrTemplate solrTemplate;
	public void importItemData() {
		
		TbItemExample example=new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("1");	//商品审核状态为一才上传
		List<TbItem> itemList = itemMapper.selectByExample(example);
		System.out.println("商品列表");
		for (TbItem tbItem : itemList) {
			System.out.println(tbItem.getId()+"  "+tbItem.getTitle()+"  "+tbItem.getBrand());
			Map specMap = JSON.parseObject(tbItem.getSpec(),Map.class);	//从数据库中提取规格json字符串转换为map
			tbItem.setSpecMap(specMap);
			
		}
		solrTemplate.saveBeans(itemList);
		solrTemplate.commit();
		System.out.println("结束");
	}
	
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
		SolrUtil solrUtil=(SolrUtil) context.getBean("solrUtil");
		solrUtil.importItemData();
	}
}

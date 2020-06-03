//服务层
app.service('catService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../cat/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../cat/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../cat/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../cat/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../cat/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../cat/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../cat/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
	//根据上级分类查询商品分类列表
	this.findByParentId=function(parentId){
		return $http.get('../cat/findByParentId.do?parentId='+parentId);
	}
	
});

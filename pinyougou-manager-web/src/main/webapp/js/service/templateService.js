//服务层
app.service('templateService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../template/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../template/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../template/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../template/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../template/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../template/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../template/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
	
	//读取模板列表
	this.selectOptionList=function(){
		return $http.get('../template/selectOptionList.do');
	}
	
});

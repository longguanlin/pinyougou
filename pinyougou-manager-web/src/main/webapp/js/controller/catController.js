 //控制层 
app.controller('catController' ,function($scope,$controller   ,catService,templateService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		catService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		catService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		catService.findOne(id).success(
			function(response){
				$scope.entity= response;	
				
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=catService.update( $scope.entity ); //修改  
		}else{
			$scope.entity.parentId=$scope.parentId;//赋予上级ID
			$scope.entity.typeId=$scope.entity.typeId.id;
			serviceObject=catService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
					$scope.findByParentId($scope.parentId);//重新加载
					
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		catService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		catService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
	$scope.parentId=0;//上级ID
	
	//根据上级Id查询商品分类列表
	$scope.findByParentId=function(parentId){
		$scope.parentId=parentId;//记住上级ID
		catService.findByParentId(parentId).success(
				function(response){
					$scope.list=response;
				}
		);
	}
	
	$scope.grade=1; //当前级别
	//设置级别
	$scope.setGrade=function(value){
		$scope.grade=value;
	}
	
	$scope.selectList=function(p_entity){
		if($scope.grade==1){
			$scope.entity_1=null;
			$scope.entity_2=null;
		}
		if($scope.grade==2){
			$scope.entity_1=p_entity;
			$scope.entity_2=null;
		}
		if($scope.grade==3){
			$scope.entity_2=p_entity;
		}
		$scope.findByParentId(p_entity.id)
	}
	
	$scope.templateList={data:[]};//模板id列表
	//读取模板id列表
	$scope.findTemplateList=function(){
		templateService.selectOptionList().success(
				function(response){
					$scope.templateList={data:response};
				}
		);
	}
});	

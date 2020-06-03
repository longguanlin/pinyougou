app.controller("brandController",function($scope,$http,$controller,brandService){
			
			$controller('baseController',{$scope:$scope});
			//查询品牌列表
			$scope.findAll=function(){
				brandService.findAll().success(
					function(response){
						$scope.list=response;
					}		
				);
			}
			
			
			//分页查询
			$scope.findPage = function(page,size){
				brandService.findPage(page.size).success(
					function(response){
						$scope.list=response.rows;//显示当前页数据
						$scope.paginationConf.totalItems=response.total;//更新总记录数
					}		
				);
			}
			
			//新增和修改
			$scope.save = function(){
				var object=null;
				if($scope.entity.id!=null){
					object=brandService.update($scope.entity);
				}else{
					object=brandService.add($scope.entity);
				}
				object.success(
					function(response){
						if(response.success){
							$scope.reloadList();
						}else{
							alert(response.message);
						}
					}		
				)
			}
			
			//修改查询一个
			$scope.findOne = function(id){
				brandService.findOne(id).success(
					function(response){
						$scope.entity=response;
					}		
				)
			}
			
			
			//删除
			$scope.delet=function(){
				brandService.delet($scope.selectIds).success(
					function(response){
						if(response.success){
							$scope.reloadList();
						}else{
							alert(response.message);
						}
					}		
				);
			}
			
			//查询
			$scope.searchEntity={};
			$scope.search=function(page,size){
				brandService.search(page,size,$scope.searchEntity).success(
						function(response){
							$scope.list=response.rows;//显示当前页数据
							$scope.paginationConf.totalItems=response.total;//更新总记录数
						}		
					);
			}
		
		});
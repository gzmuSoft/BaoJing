function formatNumber (n) {
	n = n.toString()
	return n[1] ? n : '0' + n;
}
function formatTime (number, format) {
	var time = new Date(number)
	var newArr = []
	var formatArr = ['Y', 'M', 'D', 'h', 'm', 's']
	newArr.push(time.getFullYear())
	newArr.push(formatNumber(time.getMonth() + 1))
	newArr.push(formatNumber(time.getDate()))

	newArr.push(formatNumber(time.getHours()))
	newArr.push(formatNumber(time.getMinutes()))
	newArr.push(formatNumber(time.getSeconds()))

	for (var i in newArr) {
		format = format.replace(formatArr[i], newArr[i])
	}
	return format;
}
$(function(){
			var currentPage = 1;
			var currentType = 1;
    		function getData(page,type)
    		{	
    			$.ajax({
    				url:'/supply/need',
    				data:{page:page,type:type},
    				type:'get',
					dataType:'json',
    				success:function(data)
    				{
    					console.log(data);
    					var html = "";
    					//移除查看更多
			    		$("#showMore").remove();
    					for(var i =0;i<data.length;i++)
    					{
							if(!data[i].file){
							html += '<div class="contentBox" data-id="'+data[i].id+'">\
				    		<div class="contentInnerBox" >\
					    		<div class="headerContent" >\
				    			<img src="/market/images/mn.jpg"  width="50px"; height="50px"; style="border-radius: 50%;" class="pic">\
				    			<span class="username">'+data[i].username+'</span>\
								<div style=" clear:both;">\
									<div class="fr">\
										<span style="font-size: 14px; color: #CCC;" >发布时间：</span>\
										<span class="time" >'+formatTime(data[i].date, 'Y-M-D h:m:s')+'</span>\
									</div>\
									<div id="clear"></div>\
									</div>\
				    		</div>\
				    		<div id="clear"></div>\
				    		<div class="contentText">'+data[i].content+'</div>\
							<hr style=" margin-top:20px; width:100%; border:#DDD solid 0.5px;"   >\
							</div>\
						</div>'
							}
							else
							{
							html += '<div class="contentBox" data-id="'+data[i].id+'">\
				    		<div class="contentInnerBox" >\
					    		<div class="headerContent" >\
				    			<img src="/market/images/mn.jpg"  width="50px"; height="50px"; style="border-radius: 50%;" class="pic">\
				    			<span class="username">'+data[i].username+'</span>\
								<div style=" clear:both;">\
									<div class="fr">\
										<span style="font-size: 14px; color: #CCC;" >发布时间：</span>\
										<span class="time" >'+formatTime(data[i].date, 'Y-M-D h:m:s')+'</span>\
									</div>\
									<div id="clear"></div>\
									</div>\
				    		</div>\
				    		<div id="clear"></div>\
				    		<div class="contentText">'+data[i].content+'</div>\
							<div class="img"><img src="/preview/'+data[i].file+'" style="width:44%; height: 100px ; margin-left: 3%;"/></div>\
							<hr id="hr01" style="  width:98%; border:#DDD solid 0.5px;"   >\
							</div>\
						</div>'
							}
    					
						}
    					if(data.length==0){
    						
    						html+= '<div style="height:80px;padding:3px 0 6px 0;">\
			    		<center  >\
			    			<span style="color:#777777" class="more" >已经到底</span>\
			    		</center>\
			    	</div>'
    					}else if(data.length>2){
    						html+= '<div style="height:80px;padding:3px 0 6px 0;color:#1EA076;" id="showMore">\
			    		<center  >\
			    			<span style="" class="more">查看更多</span>\
			    			<span class="fa fa-angle-down more" ></span>\
			    		</center>\
			    	</div>'
    					}
			    	$("#showSection").append(html);
    				}
    			})
    		}

    		//查看更多按钮点击事件
    		$(document).on("click",".more",function(){
    			currentPage++;
    			getData(currentPage,currentType);
    		})

    		// 项目按钮点击事件
    		$(document).on("click",".item li",function(){
    			var cla = $(this).attr("class");
    			 
    			if(cla=="current"){
    				return;
    			}else{
    				$(".item li").eq(0).removeClass('current');
    				$(".item li").eq(1).removeClass('current');
    				$(this).addClass('current');

    				var type = $(this).attr('type');
    				currentType = type;
    				//清空内容
    				$("#showSection").html("");

    				getData(1,currentType);
    			}
    			
    		})
			
			//选项卡点击事件
			$(document).on("click",".contentBox",function(){
				var id = $(this).attr("data-id");
				window.location.href="/supply/needDetail/"+id;
			})
			
			
			
    		getData(currentPage,currentType);

    	})
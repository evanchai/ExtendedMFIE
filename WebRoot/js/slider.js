
$(function(){
	$('#number li').mouseover(function(){
		$("#"+this.id).addClass("selected").siblings().removeClass("selected");
		$('.slider1 li img').fadeIn(4000).attr("src","images/"+(this.id)+".jpg");
		});
	});
	
/*首页广告效果*/
$(function(){
     var len  = $(".num > li").length;
	 var index = 0;
	 var adTimer;
	 $(".num li").mouseover(function(){
		index  =   $(".num li").index(this);
		showImg(index);
	 }).eq(0).mouseover();	
	 //滑入 停止动画，滑出开始动画.
	 $('.ad').hover(function(){
			 clearInterval(adTimer);
		 },function(){
			 adTimer = setInterval(function(){
			    showImg(index)
				index++;
				if(index==len){index=0;}
			  } , 2000);
	 }).trigger("mouseleave");
})
// 通过控制top ，来显示不同的幻灯片
function showImg(index){
        var adHeight = $(".content_right .ad").height();
		$(".slider").stop(true,false).animate({left : -adHeight*index},1000);
		$(".num li").removeClass("on")
			.eq(index).addClass("on");
}
	

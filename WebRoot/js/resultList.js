
//Added by Luminosite 2014/4/29
	
	
	var count=0;
	
	function liveTipEsc(){
		$('div#liveTip').hide('slow');
	}
	
	function liveTipREsc(){
		$('div#liveTipR').hide('slow');
	}
	
	var CurrentCover;
	var onInfo;
	function liveTipSelect(){
		$('div#liveTip').hide('slow');
		var res = CurrentCover;
		addToResult(res, onInfo);
	}
	
	function addToResult(e, onIn){
		
		$.ajax({
             type: "GET",
             url: "ResultSetServlet",
             data: {action:"NULL_add", id:e, callon:onIn},
             dataType: "json",
             success:resultListRecall
         });
         
	}
	
	function removeFromResult(){
		liveTipREsc();
		
		removeResult(CurrentCover, onInfo);
	}
	
	function removeResult(e, onIn){
		
		$.ajax({
             type: "GET",
             url: "ResultSetServlet",
             data: {action:"NULL_remove", id:e, callon:onIn},
             dataType: "json",
             success:resultListRecall
         });
         
	}
	
	function resultListRecall(data, textStatus){
		$('div#addedDiv').empty();
		for(x in data.list){
			var newA = "<a class=\"rname\" href=\"javascript:void(0)\" onclick=\""
				+data.list[x].onC+"\" >"+data.list[x].name
				+"</a>";
			$('div#addedDiv').append(newA);
		}
		
		$('a.rname').bind('mouseover', function(event) {
			
			var $liveTip = $('div#liveTipR');
			var $link = $(event.target).closest('a');
			if (!$link.length) { return; }
			var link = $link[0];
			
				$liveTip.css({
					top: event.pageY + 12,
					left: event.pageX + 12
				});
				CurrentCover = link.innerHTML;
				onInfo = $(this).attr('onclick').toString();
				$liveTip.show("slow");
			
		});
		
		/*
		$('a.rname').hover(function(event) {
			var $liveTip = $('div#liveTip');
			var link = $(this);
			$liveTip.css({
				top: event.pageY + 12,
				left: event.pageX + 12
			});
			var as = $('a#liveSelect');
			CurrentCover = $(this).innerHTML;
			onInfo = $(this).attr('onclick').toString();
			alert("this:"+CurrentCover);
			as.text('remove');
			$liveTip.show("slow");
		}, function(event){
		
		});
		*/
	}
	
	jQuery(function($) {
		$('div#liveTipR').hide();
		var $liveTip = $('div#liveTip');
		$liveTip.hide();
		
		$('a.name').bind('mouseover mouseout mousemove', function(event) {
			var $link = $(event.target).closest('a');
			if (!$link.length) { return; }
			var link = $link[0];
			if (event.type == 'mouseover') {
				$liveTip.css({
					top: event.pageY + 12,
					left: event.pageX + 12
				});
				CurrentCover = link.innerHTML;
				onInfo = $(this).attr('onclick').toString();
				$liveTip.show("slow");
			};
			
		});
		
		$('a.rname').bind('mouseover', function(event) {
			
			var $liveTipR = $('div#liveTipR');
			var $link = $(event.target).closest('a');
			if (!$link.length) { return; }
			var link = $link[0];
			
				$liveTipR.css({
					top: event.pageY + 12,
					left: event.pageX + 12
				});
				CurrentCover = link.innerHTML;
				onInfo = $(this).attr('onclick').toString();
				$liveTipR.show("slow");
			
		});
		
	});
		

var checkedList = [];
var facetDia;

function createDiv(id) {
	var div = $("<div></div>");
	div.attr("id", id);
	return div;
}

/*
 * <div id="facetDia3" style="visibility: hidden;"> <div id="content3"> <div
 * id="maingrid3"></div> <div style="display:none;"></div>
 * 
 * <div id="picSlider3"></div> <div id="picEnlarge3"> <div id="picZone3"> <div
 * class="label">Thumb of Image</div> <img id="imgLarge3" src="" alt="" />
 * </div> </div> </div> </div>
 */
function createPicArea(count) {
	$("#facetDia" + count).remove();
	var div = createDiv("facetDia" + count);
	var content = createDiv("content" + count);
	div.append(content);
	var margin = createDiv("maingrid" + count);
	var padding = $('<div style="display:none;"></div>');
	var picSlider = createDiv("picSlider" + count);
	var picEnlarge = createDiv("picEnlarge" + count);
	content.append(margin).append(padding).append(picSlider).append(picEnlarge);
	var picZone = createDiv("picZone" + count);
	picZone.append('<div class="label">Thumb of Image</div>');
	var img = $("<img src='' alt=''/>");
	img.attr("id", "imgLarge" + count);
	picZone.append(img);
	picEnlarge.append(picZone);
	$(document.body).append(div);
	return div;
}

function loadPic(count, picObjStr) {
	for ( var i in picObjStr) {
		var picID = i + "_pic";
		var checkID = i + "_check";
		var picCheckbox = "<input type=\"checkbox\" class=\"picCheckbox\"  id="
				+ checkID + " />";
		var picPreview = "<img class=\"picPreview\" src=" + picObjStr[i]
				+ " alt=\"PIC Priview\" id=" + picID + " />";

		var divWrap = "<div class=\"wrap\">" + picCheckbox + picPreview
				+ "</div>";
		$("#picSlider" + count).append(divWrap);
		$("#" + picID).attr("src", picObjStr[i]);
	}
}

function bindOpr(count, picObjStr) {
	$(".picCheckbox").click( function() {
		var i = parseInt($(this).attr("id"));
		$("#imgLarge" + count).attr("src", picObjStr[i]);
		this.blur();
		this.focus();

		var unchecked = ($(this)).attr("checked");
		// unchecked鏄偣鍑籧heckbox涔嬪墠鐨勫�
			if (!unchecked) {
				$(this).parent().addClass("checked");
				$(this).parent().removeClass("unchecked");
			} else {
				$(this).parent().addClass("unchecked");
				$(this).parent().removeClass("checked");
			}

		});
	$(".picPreview").click( function() {
		var i = parseInt($(this).attr("id"));
		var checkID = i + "_check";
		$("#" + checkID).click();
	});

}

function getCheckListValues(){
	var checkList = [];
	$(".picCheckbox").each( function() {
		if ($(this).attr("checked")) {
			var i = parseInt($(this).attr("id"));
			checkList.push(i);
		}
	});
	return checkList;
}

function showDialog(count,picObjStr) {
	var facetDia = createPicArea(count);
	loadPic(count, picObjStr);
	bindOpr(count, picObjStr);
	facetDia = $.ligerDialog
			.open( {
				isDrag :true,
				width :600,
				height :480,

				target :facetDia,
				buttons : [
						{
							text :'确定',
							onclick : function() {
							//get the selected options from the checkList
								var checkList = getCheckListValues();
								if(checkList.length == 0){
									alert("Please select one picture");
									return;
								}
								var pic = [];
								var galleryHtml = "<div style=\"width:358px;\"><div class=\"slider-wrapper theme-default controlnav-thumbs\"><div class=\"ribbon\"></div><div id=\"facet-gallery" + count + "\" class=\"nivoSlider\" style=\"width:358px;height:200px;\">"
								for (var i = 0;i < checkList.length;i++) {
										galleryHtml += "<img src=\""
												+ picObjStr[checkList[i]] + "\" rel=\""
												+ picObjStr[checkList[i]]
												+ "\" alt=\"\" />";
										pic.push(picObjStr[checkList[i]]);
								}
								galleryHtml += "</div></div></div>";

								$("#pic" + count).val(pic);

								facetDia.close();

								var facet = $("#facet" + count);
								$("div", facet).remove();
								facet.append(galleryHtml);

								jQuery("#facet-gallery" + count).nivoSlider( {
									effect :"fade",
									slices :15,
									boxCols :8,
									boxRows :4,
									animSpeed :200,
									pauseTime :3000,
									startSlide :0,
									directionNav :true,
									directionNavHide :true,
									controlNav :true,
									controlNavThumbs :true,
									controlNavThumbsFromRel :true,
									keyboardNav :true,
									pauseOnHover :true,
									manualAdvance :false
								});
							}
						}, {
							text :'取消',
							onclick : function() {
								facetDia.close();
							}
						} ]
			});
}

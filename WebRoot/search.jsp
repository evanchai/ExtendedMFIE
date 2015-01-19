<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="cn.edu.fudan.se.web.bean.ResultItem"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	ArrayList<ResultItem> resultList = (ArrayList<ResultItem>)request.getSession().getAttribute(ResultItem.RESULT_LIST);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=gb2312" />
<title>StackOverflow - Facet - SE Lab of Fudan University</title>
<link rel="shortcut icon" href="favicon.ico" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/popwindow.css" rel="stylesheet" type="text/css" />
<style>
#tabs {
	width: 360px;
}
</style>
<!--//added by Luminosite, css 14/5/4 -->
<style type="text/css">
td.postcell {
margin: 0;
padding: 0;
border: 0;
font-size: 100%;
vertical-align: baseline;
background: transparent;
}
.post-text {
margin: 15px;
padding: 0;
border:0;
font-size: 100%;
vertical-align: baseline;
background: tranprent;
}
.post-tag, .post-text .post-tag, .wmd-preview a.post-tag {
color: #3e6d8e;
background-color: #e0eaf1;
border-bottom: 1px solid #b3cee1;
border-right: 1px solid #b3cee1;
padding: 3px 4px 3px 4px;
margin: 2px 2px 2px 0;
text-decoration: none;
font-size: 90%;
line-height: 1.4;
white-space: nowrap;
display: inline-block;
}
pre {
padding: 5px;
font-family: Consolas,Menlo,Monaco,Lucida Console,Liberation Mono,DejaVu Sans Mono,Bitstream Vera Sans Mono,Courier New,monospace,serif;
margin-bottom: 10px;
background-color: #eee;
overflow: auto;
width: auto;
width: 650px !ie7;
padding-bottom: 20px !ie7;
max-height: 600px;
}
code {
font-family: Consolas,Menlo,Monaco,Lucida Console,Liberation Mono,DejaVu Sans Mono,Bitstream Vera Sans Mono,Courier New,monospace,serif;
background-color: #eee;
}
#liveTip {
position: absolute;
background-color: #cfc;
padding: 4px;
border: 2px solid #9c9;
border-radius: 4px;
-webkit-border-radius: 4px;
-moz-border-radius: 4px;
}
#liveTipR {
position: absolute;
background-color: #cfc;
padding: 4px;
border: 2px solid #9c9;
border-radius: 4px;
-webkit-border-radius: 4px;
-moz-border-radius: 4px;
}
#addedDiv {
	OVERFLOW:auto;
	overflow-x:auto;
	overflow-y:auto;
}
#addedDiv a{
	float:left;
	width:800px;
	margin:5px 0px 5px 0px;
}

</style>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script src="js/ligerUI1.9/core/base.js" type="text/javascript"></script>
<script src="js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="js/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="js/ligerUI/js/plugins/ligerCheckBox.js"
	type="text/javascript"></script>
<script src="js/ligerUI1.9/plugins/ligerComboBox.js"
	type="text/javascript"></script>
<script src="js/ligerUI/js/plugins/ligerResizable.js"
	type="text/javascript"></script>

<!-- added by Luminosite 14/5/5 -->	
<script src="js/resultList.js"
	type="text/javascript"></script>

<script src="js/ligerUI1.9/plugins/ligerTree.js" type="text/javascript"></script>

<link href="js/ligerUI/skins/Silvery/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="js/nivo-slider.css" />
<link rel="stylesheet" type="text/css"
	href="js/themes/default/default.css" />
<link href="css/styles/shThemeDefault.css" rel="stylesheet"
	type="text/css" />

<link href="js/Pager.css" rel="stylesheet" type="text/css" />
<link href="css/styles/shCore.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/smoothness/jquery-ui-1.8.21.custom.css"
	rel="Stylesheet" />

<script type="text/javascript" src="js/ligerUI1.9/plugins/ligerGrid.js"></script>
<script type="text/javascript" src="js/mxClient.js"></script>
<!-- Include required JS files -->
<script type="text/javascript" src="js/scripts/shCore.js"></script>



<script>
 
<%@ include file="js/scripts/shBrushJava.js"%>
</script>
<script type="text/javascript" src="js/jquery.nivo.slider.js"></script>





<script src="js/jquery.pager.js" type="text/javascript"></script>

<script type="text/javascript" src="js/jquery-ui-1.8.21.custom.min.js"></script>

<script type="text/javascript" src="js/clusterfacet.js"></script>





<script>
javascript:window.history.forward(1);

var managerVHistory,managerPackage,managerType,managerCalledBy,managerCall,managerAccess,managerTopic, managerTopicCall,managerTopicCalledBy;
var managerlanauageTree,treeSelectedNums;
var currentFacetButton;
var allSelectedItems,treeSelectedHashTable;
var projectName = ""; 
		$(function() {
     	$("#txt3").ligerComboBox({
                width: 240,
                selectBoxWidth: 400,
                selectBoxHeight: 200, 
                
                onSelected: historySelected,
                textField:'text', valueField: 'id',treeLeafOnly:false,
                tree: { data: ${map.HistoryTree} ,
                        parentIcon: null,
        				childIcon: null, 
                		checkbox: false,
                        textFieldName:"text",
                        idFieldName:"id",
                        parentIDFieldName:"pid",
                        onSuccess:function(){
                           $(".l-expandable-close","#div3").click();
                           $("#txt3_val").val("");
                           $("#txt3").val("");
                        }
                }
            });  		
			
			projectName = '${map.projectName}';			
			
	    	$("#focusFacet").ligerTree({ data: ${map.focusFacet}, onCheck:filterSelected});			
			$("#systemFacet").ligerTree({ data: ${map.systemFacet},onCheck: filterSelected});
			$("#languageFacet").ligerTree({ data: ${map.languageFacet},onCheck: filterSelected});
			$("#tagFacet").ligerTree({ data: ${map.packageTree},onCheck: filterSelected});			
			$("#contentFacet").ligerTree({ data: ${map.typeTree},onCheck: filterSelected});			
		
	
			managerPackage = $("#tagFacet").ligerGetTreeManager();
			managerPackage.collapseAll();			 
			managerType = $("#contentFacet").ligerGetTreeManager();
			managerType.collapseAll();	
			managerTopic = $("#focusFacet").ligerGetTreeManager();//对树进行管理的API
			managerTopic.collapseAll();
			managerTopicCall = $("#systemFacet").ligerGetTreeManager();
			managerTopicCall.collapseAll();
			managerlanauageTree = $("#languageFacet").ligerGetTreeManager();
			managerlanauageTree.collapseAll();
			
			$( "#tabTopic" ).tabs().find( ".ui-tabs-nav" ).sortable({ axis: "x" });
	    	$( "#tabEnviroment" ).tabs().find( ".ui-tabs-nav" ).sortable({ axis: "x" });
			$( "#tabs-content" ).tabs().find( ".ui-tabs-nav" ).sortable({ axis: "x" });
			
		});
		
		function getVal(){

			allSelectedItems ="";
			treeSelectedHashTable ={};
		   	var topics = managerTopic.getChecked();
       		
       		//get Focus tree val
            var textTopic = "";
            for (var i = 0; i < topics.length; i++)
            {
		      textTopic += topics[i].data.text + "@";
            }
            allSelectedItems = textTopic + allSelectedItems; 
            treeSelectedHashTable["focus"] = textTopic;
            $("#topicTree1").val(textTopic);
        		
        	//get System Enviroment tree val            
            var notes = managerTopicCall.getChecked();
            var text1 = "";
            for (var i = 0; i < notes.length; i++)
            {
                
                text1 += notes[i].data.text + "@";
            }
             $("#topicCallTree1").val(text1);
            treeSelectedHashTable["system"] = text1;
            allSelectedItems = text1 + allSelectedItems;
            
            //get Language Enviroment tree val
            var lanaguageTree = managerlanauageTree.getChecked();
            var lanaguage = "";
            for (var i = 0; i < lanaguageTree.length; i++)
            {
                
                lanaguage += lanaguageTree[i].data.text + "@";
            }
             $("#valueOfLanguageTree").val(lanaguage);
            treeSelectedHashTable["lanaguage"] = lanaguage;
            allSelectedItems = lanaguage + allSelectedItems;
            
            //get tag tree val            
  		    var notes = managerPackage.getChecked();
            text1 = "";
            for (var i = 0; i < notes.length; i++)
            {
                text1 += notes[i].data.text + "@";
            }
            treeSelectedHashTable["tag"] = text1;
            allSelectedItems = text1 + allSelectedItems;
            $("#valueOfPackageTree").val(text1);
            
            //get content tree val                       
            var notes2 = managerType.getChecked();
            var text2 = "";
            for (var i = 0; i < notes2.length; i++)
            {
                text2 += notes2[i].data.text + "@";
            }
            allSelectedItems = text2 + allSelectedItems;
            treeSelectedHashTable["content"] = text2;
            $("#valueOfTypeTree").val(text2);
            return true;
		}
	</script>

<script>
		function showDetail(id) {//在右边根据element Id 显示代码
			$.ligerDialog.open({
				url : "search?action=detail&id=" + id,
				isDrag : true,
				width : 750,
				height : 530
			});
			
			$('div#liveTip').hide();
		}
		function showPostDetail(id)
		{
			document.forms.form1.target= "_blank";
			document.forms.form1.action="search?action=detail&id=" + id;
			document.forms.form1.submit();
		}
		
		function showStackoverflowPage(id)
		{
			document.forms.form1.target= "_blank";
			document.forms.form1.action="http://stackoverflow.com/questions/" + id;
			document.forms.form1.submit();
		}

		function performExperiment()
		{
			document.forms.form1.action="search?action=performExperiment";
			document.forms.form1.submit();
		}
		
		function showRecommend() {
			var selectedResults = "";
			$("#result > ol > div ").each(function (n){
			if( this.childNodes[1].childNodes[1].childNodes[1].checked == true)
				selectedResults = selectedResults + this.attributes["id"].value + ";";
			});
			
			$.ligerDialog.open({
				url : "search?action=recommend&id=" + selectedResults,
				isDrag : true,
				width : 800,
				height : 600
			});
		}
		
		function selectOption()
		{		
			var selectionControl = document.getElementById("project");
			for(var i=0; i<selectionControl.options.length; i++)
			{
				if(selectionControl.options[i].value == projectName)
				{
					selectionControl.options[i].selected = true;
				};
			};
		}
		
		function doSearchInResult()
		{
			document.forms.form1.action="search?action=searchInResult";
			document.forms.form1.submit();
		}
		
		function doUpdate()
		{
			document.forms.form2.action="search?action=submit";
			document.forms.form2.submit();
		}
		
		function nagivagetHistory(historyID)
		{

			document.forms.form2.action="search?action=historyNavigate&id=" + historyID;
			document.forms.form2.submit();
			
		}

		function getParentID(element)
		{
			while(element!= null &&  element.id=="")
				element = element.parentElement;
			return element.id;
		}
		function filterTopic()
		{
			$('div').filter('[class="l-box l-checkbox l-checkbox-checked"]').each(function (n){
			if( getParentID(this) !="focusFacet")
				this.className="l-box l-checkbox l-checkbox-unchecked";
			});
			currentFacetButton = "btnTopic";
			
			filterSelected();
		}

		function getArray(a,treeSelectedNums) {
		 var hash = {},
		     len = a.length,
		     result = [];
		
		 for (var i = 0; i < len; i++){
		     if (!hash[a[i]]){
		         hash[a[i]] = 1;
		     }
		     else if(hash[a[i]]){
		     	hash[a[i]]++;
		     	var hashnum= hash[a[i]];
		     	if(hashnum>=treeSelectedNums) result.push(a[i]);
		     }
		 }
		 return result;
		}
		
		function deduplicated(a) {		
		 var hash = {},
     		 len = a.length,
     	     result = [];

		 for (var i = 0; i < len; i++){
		     if (!hash[a[i]]){
		         hash[a[i]] = true;
		         result.push(a[i]);
		     } 
		 }		
		 return result;
		}
		function isMultitreeSelected(){
			treeSelectedNums = 0;
			var focus =managerTopic.getChecked();
			var enviroments =managerTopicCall.getChecked();
			var tags =managerPackage.getChecked();
			var contents =managerType.getChecked();
			var languages = managerlanauageTree.getChecked();
			if(focus.length>0) treeSelectedNums++;
			if(enviroments.length>0) treeSelectedNums++;
			if(tags.length>0) treeSelectedNums++;
			if(contents.length>0) treeSelectedNums++;
			if(languages.length>0) treeSelectedNums++;
			return treeSelectedNums>1;
		}
		
		function SelectedIds(allSelectedItems)
		{
			var index1 = allSelectedItems.indexOf("&");
			var num = 0;
			var ids= new Array();
		    while(index1!=-1)
		    {
		    	num++;
		    	allSelectedItems = allSelectedItems.substring(index1+1, allSelectedItems.length);
		    	//alert(allSelectedItems);
		    	var index2 = allSelectedItems.indexOf("&");
		    	var id = allSelectedItems.substring(0, index2);
		    	ids.push(id);
		    	allSelectedItems = allSelectedItems.substring(index2+1, allSelectedItems.length);
		    	index1 = allSelectedItems.indexOf("&");
		    };
		    return ids;
		}
		function filterSelected()
		{

			getVal();
			$("#result > ol > div").hide(); //hide all candidates
			
			var ids = new Array();
			for(var facet in treeSelectedHashTable){
			    //convert selected String to Array
				var facetIds = SelectedIds(treeSelectedHashTable[facet]);
				//make array deduplicated
				facetIds = deduplicated(facetIds);
				//concat all arrays
				ids=ids.concat(facetIds);
			}
			 
			//var ids= SelectedIds(allSelectedItems);
		    if(isMultitreeSelected()) 
		    	ids=getArray(ids,treeSelectedNums); 
		    for(var i = 0; i< ids.length;i++)
		   	{
		   		$("#" + ids[i]).show();
		   	}

			/*

			for(item in selectedItems)
			{
				var numbers = getNumbers(selectedItems[item]);
				if(numbers == nothing)
				 	continue;
	            alert(numbers);
				var numberArray = numbers.trim().split(" ");
				for(number in numberArray)
				{
					var id = numberArray[number].trim();
					$("#" + id).show();
					
				};			
			}*/
		    $("#resultCount").html(ids.length + " results");
		}

		var nothing = "-1";
		function getNumbers(text)
		{
			if(text.indexOf(":white") == -1)
				return nothing;
			var endIndex = text.length - 7;
			var startIndex = text.lastIndexOf(":white") + 8;
			return text.substring(startIndex, endIndex);
		}
		
		function historySelected(value, text)
		{
			if(text!= null && text!= "")
				$("#btnHistory").attr("disabled",false);
			else
				$("#btnHistory").attr("disabled",true);
			
		}
		  
		

	</script>


</head>
<body onload="selectOption()">


	<div class="header">
		<div class="header_content">
			<div class="logo">
				<a title="Multi-Facet Feature Location"><img
					src="images/logo.png" /> </a>
			</div>
			<div class="search">
				<form id="form1" method="post">
					<div class="header_input">

					 <input name="q" type="text" class="header_input_txt"
							id="txtKeywords" value="${q }" size="30" maxlength="50" />
						<button id="search" class="search_btn"
							onmouseout="this.className='search_btn'"
							onmouseover="this.className='search_btn search_btn_hover'" />
						<span class="search_png">Search</span>

					<!-- 	<button id="searchInResult" class="search_btn"
							onmouseout="this.className='search_btn'"
							onmouseover="this.className='search_btn search_btn_hover'"
							onclick="doSearchInResult()" />
						<span class="search_png">Search in Results</span> -->

					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="maincontent">
		<form id="form2" method="post" 
			onsubmit="return getVal();">
			<div class="maincolmask maincol">
				<div class="col1">
					<table class="facet-title" align="center" cellspacing="2px">
						<tr>
							<td class="facet-title" colspan="2">History Explorer</td>

						</tr>
						<tr>

							<td><input type="text" id="txt3" style="z-index:2;" />
							</td>
							<td><button id="btnHistory" class="search_btn"
									onmouseout="this.className='search_btn'"
									onmouseover="this.className='search_btn search_btn_hover'"
									onclick="nagivagetHistory($('#txt3_val').val());"
									disabled="disabled" value="Navigate" /> <span
								class="search_png">Navigate</span>
							</td>
						</tr>
					</table>
					<!-- <div class="facet-title">
						Exploration History  <input type="text" id="txt3" style="z-index:2;" />
						<button id="btnHistory" class="search_btn"
							onmouseout="this.className='search_btn'"
							onmouseover="this.className='search_btn search_btn_hover'"
							onclick="nagivagetHistory($('#txt3_val').val());"
							disabled="disabled" value="Navigate" />
						<span class="search_png">Navigate</span>
					</div> -->
					<!-- for topic facets -->
					
					<div id="tabTopic" style="width: 360px; ">
						<ul>
							<li><a href="#tabs-selfTopic">Focus</a>
							</li>
						</ul>
						<div id="tabs-selfTopic">
							<div class="treeview">
								<div id="focusFacet"></div>
							</div>
							<input type="hidden" name="action" value="submit" /> <input
								type="hidden" id="topicTree1" name="topicTree1" />
						</div>
					</div>
					
<!-- Enviroment Facets -->
					<div id="tabEnviroment" style="width: 360px;">
						<ul>
							<li><a href="#tabs-SystemEnviroment">System</a>
							</li>
							<li><a href="#tabs-LanguageEnviroment">Language</a>
							</li>
						</ul>

						<div id="tabs-SystemEnviroment">
							<div class="treeview">
								<div id="systemFacet"></div>
							</div>
							<input type="hidden" name="action" value="submit" /> <input
								type="hidden" id="topicCallTree1" name="topicCallTree1" />
						</div>
						<div id="tabs-LanguageEnviroment">
							<div class="treeview">
								<div id="languageFacet"></div>
							</div>
							<input type="hidden" name="action" value="submit" /> <input
								type="hidden" id="valueOfLanguageTree" name="valueOfLanguageTree" />
						</div>
					</div>
<!-- Tag and Content Facets -->
					<div id="tabs-content" style="width: 360px;">
						<ul>
							<li><a href="#tab-package">Tag</a>
							</li>
							<li><a href="#tab-type">Content</a>
							</li>
						</ul>

						<div id="tab-package">
							<input type="hidden" name="action" value="submit" /> <input
								type="hidden" id="valueOfPackageTree" name="valueOfPackageTree" />

							<div class="treeview">
								<div id="tagFacet"></div>
							</div>
						</div>
						
						<div id="tab-type">
							<input type="hidden" name="action" value="submit" /> <input
								type="hidden" id="valueOfTypeTree" name="valueOfTypeTree" />

							<div class="treeview">
								<div id="contentFacet"></div>
							</div>
						</div>
					</div>


<!--added by Luminosite 14/5/4 -->
<div id="addedDiv" style="width:300px;height:200px" >

<%	if(resultList!=null){for(ResultItem ri: resultList){ %>
		<a class="rname" href="javascript:void(0)" onclick="<%=ri.onClick%>" ><%=ri.name%></a>
<%	}}%>

</div>
					
					<!-- 
						<div id="tabs-context" style= "width: 360px;">
							<ul>
								<li><a href="#tab-caller">Use </a></li>
								<li><a href="#tab-callee">Used by </a></li>
								<li><a href="#tab-access">Access </a></li>
								</ul>
							
							<div id="tab-callee">								
									 <input type="hidden" name="action" value="submit" /> 
									 <input type="hidden" id="valueOfCalledBy" name="valueOfCalledBy" />
								
								<div class="treeview">
									<div id="calledByTree"></div>
								</div>
							</div>
							<div id="tab-caller">
								
								 <input type="hidden" name="action" value="submit" />
								  <input type="hidden" id="valueOfCall" name="valueOfCall" />
								
								<div class="treeview">
									<div id="callerTree"></div>
								</div>
							</div>
							<div id="tab-access">
								<input type="hidden" name="action" value="submit" /> 
								<input type="hidden" id="valueOfAccess" name="valueOfAccess" />
								
								<div class="treeview">
									<div id="accessTree"></div>
								</div>
							</div>
						</div>
 -->
				</div>



				<div class="col2">





					<div id="resultCount"
						style="margin-left: 10px; color: #999;font-size: 13px;text-overflow: ellipsis;">
						${map.elementCount} results</div>

					<div id="result" style="height: 700px;  overflow-y:auto;">

						<br />

						<ol>
							<c:forEach var="list" items="${map.data}" varStatus="status">
<%-- 								<li class="g" id='${list.postId}'>--%>
								<div id='${list.postId}'>
								<td class="postcell" >
								<div class="content">
										<h3 class="r">
											<input type="checkbox" /> <a href="javascript:void(0)"
												class="name" onclick="showStackoverflowPage('${list.postId}');">${list.post_title}</a>
										</h3>
									<div class ="post-text itemprop="text">
										${list.post_body}
									</div>
									<div class="s">	
										<a class="post-tag">${list.post_tag}</a>
									</div>
								</div>
								</div>
								</td>
								<!-- </li> -->
							</c:forEach>
						</ol>

					</div>
<!-- 					<form id="form3" method="post">
					
					<div class="con">
						<input type="button" style="margin-left:140px" class="search_btn"
							onmouseout="this.className='search_btn'"
							onmouseover="this.className='search_btn search_btn_hover'"
							onclick="showRecommend();" value="Recommend" />
						
						


						<input type="button" style="margin-left:20px" class="search_btn"
							onmouseout="this.className='search_btn'"
							onmouseover="this.className='search_btn search_btn_hover'"
							onclick= "performExperiment();" r
							value="Collect" />
						

						<input type="button" style="margin-left:20px" class="search_btn"
							onmouseout="this.className='search_btn'"
							onmouseover="this.className='search_btn search_btn_hover'" value="Finish" />
						
					</div>
					</form> -->

				</div>
				<!-- End of col2 -->

			</div>
		</form>
	</div>
	<div class="footer">
		<div class="copyright">
			Copyright © 2012 <a href="http://www.se.fudan.edu.cn">SE Lab of
				Fudan University</a>. All rights reserved.
			<p>Last Update: Apr 20th, 2012</p>
		</div>
	</div>
	
<!-- added by Luminosite 14/5/4 -->
<!-- 	<div id="liveTip" style="width:50px" >
		<a id="liveSelect" onclick="liveTipSelect();" href="javascript:void(0)" >select</a>
		<a id="liveEsc" onclick="liveTipEsc();" href="javascript:void(0)" >cansel</a>
	</div> -->
	
<!-- 	<div id="liveTipR" style="width:50px" >
		<a id="liveSelect" onclick="removeFromResult();" href="javascript:void(0)" >remove</a>
		<a id="liveEsc" onclick="liveTipREsc();" href="javascript:void(0)" >cansel</a>
	</div> -->
	
	<script type="text/javascript">
						//markHighLight();
						
						SyntaxHighlighter.all();
						//MarkHighLight($(".java"),"search ");
	
	
	</script>
</body>
</html>

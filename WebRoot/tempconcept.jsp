
<%@ page language="java" import="java.util.*,cn.edu.fudan.se.web.pojo.*"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>My JSP 'concept.jsp' starting page</title>
<style type="text/css">
body div.mxPopupMenu {
	-webkit-box-shadow: 3px 3px 6px #C0C0C0;
	-moz-box-shadow: 3px 3px 6px #C0C0C0;
	box-shadow: 3px 3px 6px #C0C0C0;
	background: white;
	position: absolute;
	border: 3px solid #e7e7e7;
	padding: 3px;
}

body table.mxPopupMenu {
	border-collapse: collapse;
	margin: 0px;
}

body tr.mxPopupMenuItem {
	color: black;
	cursor: default;
}

body td.mxPopupMenuItem {
	padding: 6px 60px 6px 30px;
	font-family: Arial;
	font-size: 10pt;
}

body td.mxPopupMenuIcon {
	background-color: white;
	padding: 0px;
}

body tr.mxPopupMenuItemHover {
	background-color: #eeeeee;
	color: black;
}

table.mxPopupMenu hr {
	border-top: solid 1px #cccccc;
}

table.mxPopupMenu tr {
	font-size: 4pt;
}
</style>
<!-- <script type="text/javascript">
	mxBasePath = '../src';
</script> -->

<!-- Loads and initializes the library -->
<script type="text/javascript" src="js/mxClient.js"></script>
<link href="js/ligerUI1.9/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<script src="js/ligerUI1.9/core/base.js" type="text/javascript"></script>
<script type="text/javascript" src="js/ligerUI1.9/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="js/ligerUI1.9/ligerui.min.js"></script>
<script type="text/javascript" src="js/ligerUI1.9/plugins/ligerGrid.js"></script>
<script type="text/javascript" src="js/ligerUI1.9/plugins/ligerDrag.js"></script>

<!-- Example code -->
<script type="text/javascript">
	var grid;
	var griddata;

	function UUID()
	{
		this.id = this.createUUID();
	}

	// When asked what this Object is, lie and return it's value  
	UUID.prototype.valueOf = function()
	{
		return this.id;
	}
	UUID.prototype.toString = function()
	{
		return this.id;
	}

	//  
	// INSTANCE SPECIFIC METHODS  
	//  

	UUID.prototype.createUUID = function()
	{
		//  
		// Loose interpretation of the specification DCE 1.1: Remote Procedure Call  
		// described at http://www.opengroup.org/onlinepubs/009629399/apdxa.htm#tagtcjh_37  
		// since JavaScript doesn't allow access to internal systems, the last 48 bits   
		// of the node section is made up using a series of random numbers (6 octets long).  
		//    
		var dg = new Date(1582, 10, 15, 0, 0, 0, 0);
		var dc = new Date();
		var t = dc.getTime() - dg.getTime();
		var tl = UUID.getIntegerBits(t, 0, 31);
		var tm = UUID.getIntegerBits(t, 32, 47);
		var thv = UUID.getIntegerBits(t, 48, 59) + '1'; // version 1, security version is 2  
		var csar = UUID.getIntegerBits(UUID.rand(4095), 0, 7);
		var csl = UUID.getIntegerBits(UUID.rand(4095), 0, 7);

		// since detection of anything about the machine/browser is far to buggy,   
		// include some more random numbers here  
		// if NIC or an IP can be obtained reliably, that should be put in  
		// here instead.  
		var n = UUID.getIntegerBits(UUID.rand(8191), 0, 7)
				+ UUID.getIntegerBits(UUID.rand(8191), 8, 15)
				+ UUID.getIntegerBits(UUID.rand(8191), 0, 7)
				+ UUID.getIntegerBits(UUID.rand(8191), 8, 15)
				+ UUID.getIntegerBits(UUID.rand(8191), 0, 15); // this last number is two octets long  
		return tl + tm + thv + csar + csl + n;
	}

	//  
	// GENERAL METHODS (Not instance specific)  
	//  

	// Pull out only certain bits from a very large integer, used to get the time  
	// code information for the first part of a UUID. Will return zero's if there   
	// aren't enough bits to shift where it needs to.  
	UUID.getIntegerBits = function(val, start, end)
	{
		var base16 = UUID.returnBase(val, 16);
		var quadArray = new Array();
		var quadString = '';
		var i = 0;
		for (i = 0; i < base16.length; i++)
		{
			quadArray.push(base16.substring(i, i + 1));
		}
		for (i = Math.floor(start / 4); i <= Math.floor(end / 4); i++)
		{
			if (!quadArray[i] || quadArray[i] == '')
				quadString += '0';
			else
				quadString += quadArray[i];
		}
		return quadString;
	}

	// Replaced from the original function to leverage the built in methods in  
	// JavaScript. Thanks to Robert Kieffer for pointing this one out  
	UUID.returnBase = function(number, base)
	{
		return (number).toString(base).toUpperCase();
	}

	// pick a random number within a range of numbers  
	// int b rand(int a); where 0 <= b <= a  
	UUID.rand = function(max)
	{
		return Math.floor(Math.random() * (max + 1));
	}

	function server(cellId)
	{
		// Increments the request ID as a prefix for the cell IDs
		requestId++;

		// Creates a local graph with no display
		var graph = new mxGraph();

		// Gets the default parent for inserting new cells. This
		// is normally the first child of the root (ie. layer 0).
		var parent = graph.getDefaultParent();

		// Adds cells to the model in a single step
		graph.getModel().beginUpdate();
		try
		{
			var v0 = graph.insertVertex(parent, cellId, cellId, 0, 0, 40, 30);
		

		

			var cellCount = parseInt(Math.random() * 16) + 4;

			// Creates the random links and cells for the response
			for ( var i = 0; i < cellCount; i++)
			{
				var id = new UUID().id;
				var v = graph.insertVertex(parent, id, "child" + i, 0, 0, 40, 30);
				graph.insertEdge(parent, null, 'has child ' , v0, v);
			}
		}
		finally
		{
			// Updates the display
			graph.getModel().endUpdate();
		}

		var enc = new mxCodec();
		var node = enc.encode(graph.getModel());

		return mxUtils.getXml(node);
	};

	function delConcept()
	{
		grid.deleteSelectedRow();

	}

	function addConcept(cell)
	{
		grid.addRow(
		{
			id : cell.id
		});

	}
	function getConcepts()
	{
		var concepts = grid.getData();
		while (concepts.length > 0)
		{
			concept = concepts.pop();

		}
	}

	function navigateConcept(id)
	{

	}
	function installMenu(graph)
	{
		graph.panningHandler.factoryMethod = function(menu, cell, evt)
		{

			menu.addItem('Add concept', null, function()
			{
				addConcept(cell);
			});

			menu.addItem('Navigate to children', null, function()
			{
				alert('Item 2');
			});

			menu.addItem('Navigate to parent', null, function()
			{
				alert('Item 2');
			});

			/* 		menu.addSeparator();

					var submenu1 = menu.addItem('Submenu 1', null, null);

					menu.addItem('Subitem 1', null, function()
					{
						alert('Subitem 1');
					}, submenu1);
					menu.addItem('Subitem 1', null, function()
					{
						alert('Subitem 2');
					}, submenu1); */
		};
	}
	// Program starts here. Creates a sample graph in the
	// DOM node with the specified ID. This function is invoked
	// from the onLoad event handler of the document (see below).

	// Global variable to make sure each cell in a response has
	// a unique ID throughout the complete life of the program,
	// in a real-life setup each cell should have an external
	// ID on the business object or else the cell ID should be
	// globally unique for the lifetime of the graph model.
	var FIRST_NODE = "Parent";
	var requestId = 0;
	var xhr;
	var graphContent;

	function main(container)
	{
		var mxcell = null;
		if (window.XMLHttpRequest)
		{
			xhr = new XMLHttpRequest();
		}
		else if (window.ActiveXObject)
		{
			xhr = new ActiveXObject("Msxml2.XMLHTTP");
		}
		xhr.onreadystatechange = function()
		{
			if (xhr.readyState == 4)
			{
				if (xhr.status >= 200 && xhr.status < 300)
				{
					graphContent = xhr.responseText;
					load(graph, mxcell);
				}
			}
		};
		// Checks if browser is supported
		if (!mxClient.isBrowserSupported())
		{
			// Displays an error message if the browser is
			// not supported.
			mxUtils.error('Browser is not supported!', 200, false);
		}
		else
		{
			// Creates the graph inside the given container
			var graph = new mxGraph(container);

			// Disables all built-in interactions
			graph.setEnabled(true);
			// Disables built-in context menu
			mxEvent.disableContextMenu(document.body);

			// Enables crisp rendering of rectangles in SVG
			mxRectangleShape.prototype.crisp = true;

			// Handles clicks on cells
			graph.addListener(mxEvent.CLICK, function(sender, evt)
			{

				mxcell = evt.getProperty('cell');
				var buttonEvt = evt.getProperty('event');
				if (buttonEvt.button == 0)
				{
					if (evt.getProperty('cell') != null)
					{
						load(graph, cell);
					}
				}
			});

			// Changes the default vertex style in-place
			var style = graph.getStylesheet().getDefaultVertexStyle();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_ELLIPSE;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.EllipsePerimeter;
			style[mxConstants.STYLE_GRADIENTCOLOR] = 'white';

			var parent = graph.getDefaultParent();

			var cx = graph.container.scrollWidth / 2;
			var cy = graph.container.scrollHeight / 2;
			var startID =FIRST_NODE;
			mxcell = graph.createVertex(parent, startID,
					startID, cx - 20, cy - 15,
					40, 30);

			// Animates the changes in the graph model
			graph.getModel().addListener(mxEvent.CHANGE, function(sender, evt)
			{
				var changes = evt.getProperty('edit').changes;
				mxEffects.animateChanges(graph, changes);
				selectionChanged(graph);
			});
			installMenu(graph);
			load(graph, mxcell);
		}

	};

	/**
	 * Updates the properties panel
	 */
	function selectionChanged(graph)
	{
		var div = document.getElementById('properties');

		// Forces focusout in IE
		graph.container.focus();

		// Clears the DIV the non-DOM way
		div.innerHTML = '';

		// Gets the selection cell
		var cell = graph.getSelectionCell();

		if (cell == null)
		{
			mxUtils.writeln(div, 'Nothing selected.');
		}
		else
		{
			// Writes the title
			var center = document.createElement('center');
			mxUtils.writeln(center, cell.value.nodeName + ' (' + cell.id + ')');
			div.appendChild(center);
			mxUtils.br(div);

			// Creates the form from the attributes of the user object
			var form = new mxForm();

			var attrs = cell.value.attributes;

			for ( var i = 0; i < attrs.length; i++)
			{
				createTextField(graph, form, cell, attrs[i]);
			}

			div.appendChild(form.getTable());
			mxUtils.br(div);
		}
	}

	/**
	 * Creates the textfield for the given property.
	 */
	function createTextField(graph, form, cell, attribute)
	{
		var input = form.addText(attribute.nodeName + ':', attribute.nodeValue);

		var applyHandler = function()
		{
			var newValue = input.value || '';
			var oldValue = cell.getAttribute(attribute.nodeName, '');

			if (newValue != oldValue)
			{
				graph.getModel().beginUpdate();

				try
				{
					var edit = new mxCellAttributeChange(cell,
							attribute.nodeName, newValue);
					graph.getModel().execute(edit);
					graph.updateCellSize(cell);
				}
				finally
				{
					graph.getModel().endUpdate();
				}
			}
		};

		mxEvent.addListener(input, 'keypress', function(evt)
		{
			// Needs to take shift into account for textareas
			if (evt.keyCode == /*enter*/13 && !mxEvent.isShiftDown(evt))
			{
				input.blur();
			}
		});

		if (mxClient.IS_IE)
		{
			mxEvent.addListener(input, 'focusout', applyHandler);
		}
		else
		{
			// Note: Known problem is the blurring of fields in
			// Firefox by changing the selection, in which case
			// no event is fired in FF and the change is lost.
			// As a workaround you should use a local variable
			// that stores the focused field and invoke blur
			// explicitely where we do the graph.focus above.
			mxEvent.addListener(input, 'blur', applyHandler);
		}
	}
	// Loads the links for the given cell into the given graph
	// by requesting the respective data in the server-side
	// (implemented for this demo using the server-function)
	function load(graph, cell)
	{

		if (graph.getModel().isVertex(cell))
		{
			var cx = graph.container.scrollWidth / 2;
			var cy = graph.container.scrollHeight / 2;

			// Gets the default parent for inserting new cells. This
			// is normally the first child of the root (ie. layer 0).
			var parent = graph.getDefaultParent();

			// Adds cells to the model in a single step
			graph.getModel().beginUpdate();
			try
			{

				//var xml = graphContent;
				var xml = server(cell.id);
				var doc = mxUtils.parseXml(xml);

				var dec = new mxCodec(doc);
				var model = dec.decode(doc.documentElement);

				if (cell.id == FIRST_NODE)
				{
					graph.getModel().mergeChildren(
							model.getRoot().getChildAt(0), parent);
					for ( var key in graph.getModel().cells)
					{
						var tmp = graph.getModel().getCell(key);
						if (tmp.id == FIRST_NODE
								&& graph.getModel().isVertex(tmp))
						{
							cell = tmp;
							break;
						}
					}
				}

				// Removes all cells which are not in the response
				for ( var key in graph.getModel().cells)
				{
					var tmp = graph.getModel().getCell(key);

					if (tmp.id != cell.id && graph.getModel().isVertex(tmp))
					{
						graph.removeCells(
						[ tmp ]);
					}
				}

				// Merges the response model with the client model
				graph.getModel().mergeChildren(model.getRoot().getChildAt(0),
						parent);

				// Moves the given cell to the center
				var geo = graph.getModel().getGeometry(cell);

				if (geo != null)
				{
					geo = geo.clone();
					geo.x = cx - geo.width / 2;
					geo.y = cy - geo.height / 2;

					graph.getModel().setGeometry(cell, geo);
				}

				// Creates a list of the new vertices, if there is more
				// than the center vertex which might have existed
				// previously, then this needs to be changed to analyze
				// the target model before calling mergeChildren above
				var vertices =
				[];

				for ( var key in graph.getModel().cells)
				{
					var tmp = graph.getModel().getCell(key);

					if (tmp.id != cell.id && model.isVertex(tmp))
					{
						vertices.push(tmp);

						// Changes the initial location "in-place"
						// to get a nice animation effect from the
						// center to the radius of the circle
						var geo = model.getGeometry(tmp);

						if (geo != null)
						{
							geo.x = cx - geo.width / 2;
							geo.y = cy - geo.height / 2;
						}
					}
				}

				// Arranges the response in a circle
				var cellCount = vertices.length;
				var phi = 2 * Math.PI / cellCount;
				var r = Math.min(graph.container.scrollWidth / 4,
						graph.container.scrollHeight / 4);

				for ( var i = 0; i < cellCount; i++)
				{
					var geo = graph.getModel().getGeometry(vertices[i]);

					if (geo != null)
					{
						geo = geo.clone();
						geo.x += r * Math.sin(i * phi);
						geo.y += r * Math.cos(i * phi);

						graph.getModel().setGeometry(vertices[i], geo);
					}
				}

			}
			finally
			{
				// Updates the display
				graph.getModel().endUpdate();
			}
		}
	};
</script>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body onload="main(document.getElementById('graphContainer'))">

	<!-- Creates a container for the graph with a grid wallpaper. Make sure to define the position
		and overflow attributes! See comments on the adding of the size-listener on line 54 ff!  -->
		<div id="property"
		style="overflow:visible;width:200px;height:200px;background:url('editors/images/grid.gif');top:10px; left:410px; position:absolute;">
		<label> Has following attributions</label>
		<textarea rows="1" cols="5">
		Call javax.swing.AbstractButton
		Call org.homeunix.thecave.buddi.model.impl.ModelFactory
		Call java.util.List{ca.digitalcave.moss.common.ParseCommands.ParseVariable} 
		CalledBy org.homeunix.thecave.buddi.view.menu.items.WindowEntry
		</textarea>
	</div>
	<div id="graphContainer"
		style="overflow:visible;width:100%;height:100%;background:url('editors/images/grid.gif');">
	</div>
	<div id="properties" style="border: solid 1px black; padding: 10px;">
	</div>
	<div id="selectedConcepts"
		style="width:200px;height:200px;top:10px; left:10px;  z-index:1; position:absolute;">
		<div id="graphContainer"
			style="border: solid 1px black;overflow:auto;width:200px;height:241px;">
		</div>
		<script type="text/javascript">
			grid = $("#selectedConcepts")
					.ligerGrid(
							{
								columns :
								[
										{
											name : 'id',
											display : 'ID',
											width : 300
										},
										{
											name : 'extent',
											display : 'Count of extent',
											render : function(item)
											{
												return parseInt(Math.random() * 32) + 4;
											}
										},
										{
											name : 'Operation',
											display : 'Operation',
											render : function(item, index)
											{
												return '<a href=javascript:delConcept()>Del</a> <a href=javascript:navigateConcept('
														+ item.id
														+ ')>Navigate</a>';
											}
										} ],
								data :
								{
									Rows : griddata
								}
							});
		</script>
	</div>
</body>
</html>

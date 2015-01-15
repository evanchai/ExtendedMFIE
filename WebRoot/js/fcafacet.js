var griddata;

var grid;
function fillData()
{
	if(grid != null)
	{
		window.liger.remove(grid);
	}
	
	grid =  $("#fcaFacet #selectedConcepts")
			.ligerGrid(
					{
						columns :
						[
								{	name : 'id', display : 'ID', width : 100 },
								{	name : 'extent', display : 'length of extent', 	render : function(item)
									{
										return item.id;
									}
								},
								{	name : 'Operation', display : 'Operation', 	render : function(item, index)
									{
										return '<a href=javascript:delConcept()>Del</a> <a href=javascript:navigateConcept('
												+ item.id + ')>Navigate</a>';
									}
								} ]/*,
						data :
						{
							Rows : griddata
						}*/
					});
}
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

		menu.addItem('Navigate to parents', null, null);

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
var FIRST_NODE = "0-0";
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
					xhr.open('Get', 'FCA?id=' + evt.getProperty('cell').id);
					xhr.send();
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

		mxcell = graph.createVertex(parent, '0-0', '0-0', cx - 20, cy - 15, 40,
				30);

		xhr.open('Get', 'FCA?id=' + FIRST_NODE);
		xhr.send();
		// Animates the changes in the graph model
		graph.getModel().addListener(mxEvent.CHANGE, function(sender, evt)
		{
			var changes = evt.getProperty('edit').changes;
			mxEffects.animateChanges(graph, changes);
			selectionChanged(graph);
		});
		installMenu(graph);
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
				var edit = new mxCellAttributeChange(cell, attribute.nodeName,
						newValue);
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
		if (evt.keyCode == /* enter */13 && !mxEvent.isShiftDown(evt))
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

			var xml = graphContent;
			// var xml = server(cell.id);
			var doc = mxUtils.parseXml(xml);

			var dec = new mxCodec(doc);
			var model = dec.decode(doc.documentElement);

			if (cell.id == FIRST_NODE)
			{
				graph.getModel().mergeChildren(model.getRoot().getChildAt(0),
						parent);
				for ( var key in graph.getModel().cells)
				{
					var tmp = graph.getModel().getCell(key);
					if (tmp.id == FIRST_NODE && graph.getModel().isVertex(tmp))
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


function createFCAArea()
{
	$("#fcaFacet").remove();
	
	var div = createDiv("fcaFacet");

	var graphContainer = createDiv("graphContainer");
	graphContainer
			.attr(
					"style",
					"overflow:visible;width:100%;height:100%;background:url('editors/images/grid.gif')");
	div.append(graphContainer);
	var properties = createDiv("properties");
	properties.attr("style", "border: solid 1px black; padding: 10px");
	div.append(properties);

	var selectedConcepts = createDiv("selectedConcepts");
	selectedConcepts
			.attr("style",
					"width:200px;height:200px;top:10px; left:10px;  z-index:1; position:absolute");
	var graphContainer = createDiv("graphContainer");
	graphContainer.attr("style",
			"border: solid 1px black;overflow:hidden;width:321px;height:241px");
	selectedConcepts.append(graphContainer);
	div.append(selectedConcepts);
	$(document.body).append(div);
	fillData();
	return div;
}

function showFcaDialog(picObjStr)
{
	var fcaFacet = createFCAArea();
	main(document.getElementById('graphContainer'));
	fcaFacet = $.ligerDialog
			.open(
			{
				isDrag : true,
				width : 800,
				height : 600,

				target : fcaFacet,
				buttons :
				[
						{
							text : '确定',
							onclick : function()
							{
								// get the selected options from the checkList
								var checkList = getCheckListValues();
								if (checkList.length == 0)
								{
									alert("Please select one picture");
									return;
								}
								var pic =
								[];
								var galleryHtml = "<div style=\"width:358px;\"><div class=\"slider-wrapper theme-default controlnav-thumbs\"><div class=\"ribbon\"></div><div id=\"facet-gallery"
										+ count
										+ "\" class=\"nivoSlider\" style=\"width:358px;height:200px;\">";
								for ( var i = 0; i < checkList.length; i++)
								{
									galleryHtml += "<img src=\""
											+ picObjStr[checkList[i]]
											+ "\" rel=\""
											+ picObjStr[checkList[i]]
											+ "\" alt=\"\" />";
									pic.push(picObjStr[checkList[i]]);
								}
								galleryHtml += "</div></div></div>";

								$("#pic" + count).val(pic);

								fcaFacet.close();

								var facet = $("#facet" + count);
								$("div", facet).remove();
								facet.append(galleryHtml);

								jQuery("#facet-gallery" + count).nivoSlider(
								{
									effect : "fade",
									slices : 15,
									boxCols : 8,
									boxRows : 4,
									animSpeed : 200,
									pauseTime : 3000,
									startSlide : 0,
									directionNav : true,
									directionNavHide : true,
									controlNav : true,
									controlNavThumbs : true,
									controlNavThumbsFromRel : true,
									keyboardNav : true,
									pauseOnHover : true,
									manualAdvance : false
								});
							}
						},
						{
							text : '取消',
							onclick : function()
							{
								fcaFacet.close();
							}
						} ]
			});
}

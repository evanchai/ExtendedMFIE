package cn.edu.fudan.se.web.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.w3c.dom.Node;

import cn.edu.fudan.se.fca.FCAService;
import cn.edu.fudan.se.fca.LatticeNode;
import cn.edu.fudan.se.web.pojo.Element;

import com.mxgraph.io.mxCodec;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;

public class FCAGetDataService
{
	private static String removePrefixOfFCAString = "org.homeunix.thecave.buddi.";
	private static String START_NODE = "0-0";
	private HttpSession session;

	// "shui(doctor,play);wei(teacher, doctor, glasses);yang(master, play);px(teacher, doctor, play, glasses)"
	private String createContext(List<Element> elementList)
	{
		StringBuilder sb = new StringBuilder();
		for (Element element : elementList)
		{
			String methodName = element.getMethodname();
			if (methodName.startsWith(removePrefixOfFCAString))
				methodName = methodName.substring(removePrefixOfFCAString.length());
			sb.append(methodName);
			sb.append("(");
			for (String callClass : element.getCallClassList())
			{
				if (callClass.startsWith(removePrefixOfFCAString))
					callClass = callClass.substring(removePrefixOfFCAString.length());
				sb.append("call " + callClass + ",");
			}
			for (String calledClass : element.getCalledClassList())
			{
				if (calledClass.startsWith(removePrefixOfFCAString))
					calledClass = calledClass.substring(removePrefixOfFCAString.length());
				sb.append("called by " + calledClass + ",");
			}
			for (String accessField : element.getAccessfields())
			{
				if (accessField.startsWith(removePrefixOfFCAString))
					accessField = accessField.substring(removePrefixOfFCAString.length());
				sb.append("access " + accessField + ",");
			}
			if (sb.charAt(sb.length() - 1) == ',')
				sb.deleteCharAt(sb.length() - 1);
			sb.append(");");
		}
		if (sb.charAt(sb.length() - 1) == ';')
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public void setSession(HttpSession session)
	{
		this.session = session;

	}

	public FCAGetDataService()
	{
		fcaService = new FCAService();

	}

	public void buildConcepts(String context)
	{
		fcaService.buildConcepts(context);
	}

	FCAService fcaService;

	public LatticeNode getRootNode()
	{

		return fcaService.getRootNode();
	}

	public LatticeNode getNodeByID(String id)
	{
		return fcaService.getLatticeNodeByID(id);
	}

	@SuppressWarnings("unchecked")
	public String getChildGraphByID(String requestID)
	{
		mxGraph graph = new mxGraph();
		System.out.println("requestID is " + requestID);
		LatticeNode parentConcept;
		if (requestID.equals(START_NODE))
		{
			Map<String, Object> map = (Map<String, Object>) session
					.getAttribute("map");
			List<Element> elementList = (List<Element>) map.get("data");
			String context = createContext(elementList);
			buildConcepts(context);
			parentConcept = getRootNode();
		}
		else
		{
			parentConcept = getNodeByID(requestID);
		}
		graph.getModel().beginUpdate();
		Object parent = graph.getDefaultParent();

		try
		{
			Object v0 = graph.insertVertex(parent, parentConcept.getId(), parentConcept.getId(), 0, 0, 40, 30);
			List<LatticeNode> childConcepts = fcaService.getChildren(parentConcept);

			for (LatticeNode child : childConcepts)
			{

				Object v = graph.insertVertex(parent, child.getId(), child.getId(), 0, 0, 40, 30);
				graph.insertEdge(parent, null, "has child ", v0, v);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			graph.getModel().endUpdate();
		}
		mxCodec codec = new mxCodec();
		Node node = codec.encode(graph.getModel());
		return mxXmlUtils.getXml(node);

	}

}

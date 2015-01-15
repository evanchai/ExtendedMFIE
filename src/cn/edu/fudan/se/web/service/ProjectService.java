package cn.edu.fudan.se.web.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.fudan.se.data.util.ExtConstant;
import cn.edu.fudan.se.data.util.Indexer;
import cn.edu.fudan.se.web.bean.Class;
import cn.edu.fudan.se.web.bean.CloneClass;
import cn.edu.fudan.se.web.bean.Experiment;
import cn.edu.fudan.se.web.bean.ExperimentResult;
import cn.edu.fudan.se.web.bean.ExperimentType;
import cn.edu.fudan.se.web.bean.Feature;
import cn.edu.fudan.se.web.bean.Method;
import cn.edu.fudan.se.web.bean.MethodInvocation;
import cn.edu.fudan.se.web.bean.MethodInvocationLink;
import cn.edu.fudan.se.web.bean.NeiborMethod;
import cn.edu.fudan.se.web.bean.SimilarAction;
import cn.edu.fudan.se.web.bean.SimilarElement;
import cn.edu.fudan.se.web.bean.SingleLinkSimilarElement;
import cn.edu.fudan.se.web.bean.TokenInMethod;
import cn.edu.fudan.se.web.bean.Variable;
import cn.edu.fudan.se.web.dal.DBHelper;
import cn.edu.fudan.se.web.dal.ProjectDAL;
import cn.edu.fudan.se.web.pojo.Element;
import cn.edu.fudan.se.web.util.AstUtil;
import cn.edu.fudan.se.web.util.CommUtil;

public class ProjectService
{

	public static boolean count = false;
	public static long start = 0,end = 0;
	private static long[] countMillis = null;
	static{
		int cc = 2;
		countMillis = new long[cc];
		for(int i=0;i<cc;i++)
			countMillis[i]=0;
	}
	
	private static int lengthOfSnapshot = 300;

	public static ProjectService getInstance()
	{
		if (instance == null)
			instance = new ProjectService();
		return instance;
	}

	private List<Element> totalElements;

	private List<Class> totalClassList;
	private List<TokenInMethod> totalTokenInMethodList;
	private List<MethodInvocationLink> methodInvocationLinkList;

	private List<CloneClass> cloneClassList;
	private List<Method> satifiedMethodList;
	private static ProjectService instance;
	private ProjectDAL projectDAL;

	private static boolean extendPhase = false;

	Hashtable<String, Element> elementHash = new Hashtable<String, Element>();



	String[][] experimentDataSet;

	int[] hintCounts = new int[4];
	
	public ProjectService(int type){
		projectDAL = new ProjectDAL(type);
		if((type&GetDataService.INIT_PROJECT_LIST)>0){
			
		}
	}

	private ProjectService()
	{
		projectDAL = ProjectDAL.getInstance();
		methodInvocationLinkList = projectDAL.getMethodInvocationLinkList();
		totalClassList = projectDAL.getClassList();
		totalTokenInMethodList = projectDAL.getTokenInMethodList();
		cloneClassList = projectDAL.getCloneClassList();
		initalTotalElements();
		initialCloneClassList();

	}

	private void calculateHint(int dataSetIndex, String[] selectedIDs)
	{
		List<SimilarAction> recommendResults = getRecommend(selectedIDs);
		for (int topN = 40; topN > 0; topN -= 10)
		{
			int hitCount = calculateHitCount(recommendResults, experimentDataSet[dataSetIndex], topN);
			switch (topN)
			{
			case 10:
				hintCounts[0] += hitCount;
				break;
			case 20:
				hintCounts[1] += hitCount;
				break;
			case 30:
				hintCounts[2] += hitCount;
				break;
			case 40:
				hintCounts[3] += hitCount;
				break;
			default:
				break;
			}

		}
	}

	private int calculateHitCount(List<SimilarAction> recommendResults, String[] godSet, int topN)
	{
		int result = 0;
		for (int index = 0; index < topN && index < recommendResults.size(); index++)
		{
			for (int i = 0; i < godSet.length; i++)
			{
				if (recommendResults.get(index).getMethodname().contains(godSet[i]))
					result++;
			}
		}
		return result;
	}

	private Element findElementByName(String methodName)
	{
		Element result = elementHash.get(methodName);
		if (result == null)
		{
			for (Element element : totalElements)
			{
				if (element.getMethodname().equals(methodName))
				{
					result = element;
					elementHash.put(methodName, result);
					break;
				}
			}
		}
		return result;
	}

	private Element findElementByPartName(String name)
	{
		int hitCount = 0;
		String methodNameWithoutParas = AstUtil.getMethodNameWithoutParas(name);
		String[] paras = AstUtil.getMethodParas(name);
		Element result = elementHash.get(name);
		if (result == null)
		{
			for (Element element : totalElements)
			{
				if (element.getMethodname().contains(methodNameWithoutParas+ "("))
				{
					
					String[] elementParas = AstUtil.getMethodParas(element.getMethodname());
					if(elementParas.length == paras.length)
					{
						boolean found = true;
						for(int i = 0; i< elementParas.length ; i++)
						{
							if(elementParas[i].endsWith(paras[i]) == false )
								found = false;
							else if( paras[i].trim().length() == 0 && elementParas[i].trim().length() >0)
								found = false;
						}
						if(found)
						{
							result = element;
							elementHash.put(name, result);
							hitCount++;
						}
					}
					
				}
			}
			if (hitCount > 1)
				System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊　Error　＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊，　Multi-Method: " + name);
		}
		if(result == null)
			System.out.println("****************** Error *********************　miss  "  + name);
		return result;
	}

	

	

	private String getCloneClassName(String methodname)
	{
		for (CloneClass cloneClass : cloneClassList)
		{
			if (cloneClass.getMethodName().equals(methodname))
				return cloneClass.getCloneClassName();
		}
		return null;
	}

	private List<Element> getCloneElementsByCloneClass(String cloneClassName)
	{
		List<Element> result = new ArrayList<Element>();
		for (CloneClass clone : cloneClassList)
		{
			if (clone.getCloneClassName().equals(cloneClassName))
			{
				result.add(clone.getElement());
			}
		}
		return result;
	}

	private Element getElementByID(String id)
	{
		for (Element element : totalElements)
		{
			if (element.getId().equals(id))
				return element;
		}
		return null;
	}

	public List<Method> getMethodListByElementList(List<Element> elementList)
	{
		List<Method> result = new ArrayList<Method>();
		for (Element element : elementList)
		{
			result.add(projectDAL.getMethodByName(element.getMethodname()));
		}
		return result;
	}

	public List<Method> getMethodListByElementListLucene(List<Element> elementList)
	{
		List<Method> result = new ArrayList<Method>();
		
		//for method getMethodByName_Project
		DBHelper.openConn();
		
		for (Element element : elementList)
		{
			result.add(getMethodByName_Project(element.getProjectName(), element.getMethodname()));
		}
		
		//for method getMethodByName_Project
		DBHelper.closeConn();
		
		return result;
	}
	
	public List<String> getProjectList()
	{
		return projectDAL.getProjectList();
	}

	public synchronized List<SimilarAction> getRecommend(String[] selectedIDs)
	{
		Map<String, SimilarAction> similarElements = new HashMap<String,SimilarAction>();
		List<Element> selectedElemens = new ArrayList<Element>();
		String currenttype = currentExperimentType.toString().toLowerCase();
		for (String selectedID : selectedIDs)
		{
			selectedElemens.add(getElementByID(selectedID));
		}
		for (Element element : totalElements)
		{
			// 避免选中自己
			if (selectedElemens.contains(element))
			{
				continue;
			}
			String shortestName = element.getShortestMethodName();
			SimilarAction newSimilarElement = null;
			if(similarElements.containsKey(element.getId()))
				newSimilarElement = similarElements.get(element.getId());
			for (Element selectedElement : selectedElemens)
			{				
				String selectedElementName = selectedElement.getShortestMethodName();		
				// 得到相同方法名的其它方法, 只考虑语法时,跳过
				if ((currenttype.contains("link") || currenttype.contains("samename")) && shortestName.equals(selectedElementName))
				{
					if (newSimilarElement == null)
					{
						if (currentExperimentType == ExperimentType.singleLink)
							newSimilarElement = new SingleLinkSimilarElement(SimilarElement.SAME_NAME, element, selectedElement.getId());
						else
							newSimilarElement = new SimilarElement(SimilarElement.SAME_NAME, element, selectedElement.getId());
						similarElements.put(element.getId(),newSimilarElement);
					}
					else
					{
						newSimilarElement.addRelation(SimilarElement.SAME_NAME, selectedElement.getId());
					}

				}
				
				
				// 得到处于同个类的方法
				
				if ((currenttype.contains("link") || currenttype.contains("sameclass"))
						&& selectedElement.getClassName().equals(element.getClassName()))
				{
					
					if (newSimilarElement == null)
					{
						if (currentExperimentType == ExperimentType.singleLink)
							newSimilarElement = new SingleLinkSimilarElement(SimilarElement.SAME_CLASS, element, selectedElement.getId());
						else
							newSimilarElement = new SimilarElement(SimilarElement.SAME_CLASS, element, selectedElement.getId());
						similarElements.put(element.getId(),newSimilarElement);
					}
					else
					{
						newSimilarElement.addRelation(SimilarElement.SAME_CLASS, selectedElement.getId());
					}					
				}
				
				
				// 得到亲属方法
				
				if (selectedElement.getNeiborElements().size() > 0)
				{
					
					if ((currenttype.contains("relative") || currenttype.contains("link")) && selectedElement.getNeiborElements().contains(element))
					{
						if (newSimilarElement == null)
						{
							if (currentExperimentType == ExperimentType.singleLink)
								newSimilarElement = new SingleLinkSimilarElement(SimilarElement.RELATIVE_METHOD, element, selectedElement.getId());
							else
								newSimilarElement = new SimilarElement(SimilarElement.RELATIVE_METHOD, element, selectedElement.getId());
							similarElements.put(element.getId(),newSimilarElement);
						}
						else
						{
							newSimilarElement.addRelation(SimilarElement.RELATIVE_METHOD, selectedElement.getId());
						}
					}					
				}
				
				
				
				// 得以访问相同变量的方法
				if ((currenttype.contains("link") || currenttype.contains("access")) && selectedElement.getAccessSameVariableElements().size() > 0)
				{
					
					if (selectedElement.getAccessSameVariableElements().contains(element))
					{

						if (newSimilarElement == null)
						{
							if (currentExperimentType == ExperimentType.singleLink)
								newSimilarElement = new SingleLinkSimilarElement(SimilarElement.ACCESS_SAME_VARIABLE, element,
										selectedElement.getId());
							else
								newSimilarElement = new SimilarElement(SimilarElement.ACCESS_SAME_VARIABLE, element, selectedElement.getId());
							similarElements.put(element.getId(),newSimilarElement);
						}
						else
						{
							newSimilarElement.addRelation(SimilarElement.ACCESS_SAME_VARIABLE, selectedElement.getId());
						}
					}
					
				}

			}
		}



		for (Element selectedElement : selectedElemens)
		{
			if (currenttype.contains("link") || currenttype.contains("invocation"))
			{
				// 得到处于调用链的方法
				for (MethodInvocationLink methodInvocationLink : methodInvocationLinkList)
				{
					if (methodInvocationLink.getName().equals(selectedElement.getMethodname()))
					{
						String[] similarMethodNames = methodInvocationLink.getInvocationLink().split("[;]");
						for (String similarMethodName : similarMethodNames)
						{
							Element linkElement = findElementByName(similarMethodName);
							if (linkElement == null || selectedElemens.contains(linkElement))
								continue;
							SimilarAction newSimilarElement = null;
							if(similarElements.containsKey(linkElement.getId()))
								newSimilarElement = similarElements.get(linkElement.getId());
							if (newSimilarElement == null)
							{
								if (currentExperimentType == ExperimentType.singleLink)
									newSimilarElement = new SingleLinkSimilarElement(SimilarElement.INVOCATION_LINK, linkElement,
											selectedElement.getId());
								else
									newSimilarElement = new SimilarElement(SimilarElement.INVOCATION_LINK, linkElement, selectedElement.getId());
								similarElements.put(linkElement.getId(),newSimilarElement);
							}
							else
							{
								newSimilarElement.addRelation(SimilarElement.INVOCATION_LINK, selectedElement.getId());
							}
						}
					}
				}
			}

			if (currenttype.contains("clone") || currenttype.contains("link"))
			{
				// 得到屬于同個克隆類的方法
				String cloneClass = getCloneClassName(selectedElement.getMethodname());
				if (cloneClass == null)
					continue;
				List<Element> cloneElements = getCloneElementsByCloneClass(cloneClass);
				for (Element element : cloneElements)
				{
					if (selectedElemens.contains(element))
						continue;
					SimilarAction newSimilarElement = null;
					if(similarElements.containsKey(element.getId()))
						newSimilarElement = similarElements.get(element.getId());					
					if (newSimilarElement == null)
					{
						if (currentExperimentType == ExperimentType.singleLink)
							newSimilarElement = new SingleLinkSimilarElement(SimilarElement.CLONE_CLASS, element, selectedElement.getId());
						else
							newSimilarElement = new SimilarElement(SimilarElement.CLONE_CLASS, element, selectedElement.getId());
						similarElements.put(element.getId(),newSimilarElement);
					}
					else
					{
						newSimilarElement.addRelation(SimilarElement.CLONE_CLASS, selectedElement.getId());
					}

				}
			}

		}
		List<SimilarAction> ret = sortListByDegree(similarElements, 40);
		return ret;
	}

	public List<Method> getSatifiedMethodList()
	{
		return satifiedMethodList;
	}

	public List<Class> getTotalClassList()
	{
		return totalClassList;
	}
	
	private void initalTotalElements()
	{
		totalElements = new ArrayList<Element>();
		List<Method> methodList = projectDAL.getMethodList();
		int count = 0;
		for (Method method : methodList)
		{

			Element element = new Element();
			element.setId("" + count++);
			// 1
			// set simple body
			String transferedContent = CommUtil.transferContent(method.getContent()); 
			
			if (transferedContent.length() > lengthOfSnapshot)
				element.setMethodsimplebody(transferedContent.substring(0, lengthOfSnapshot));
			else
			{
				element.setMethodsimplebody(transferedContent);
			}

			// 2
			//set elements info
			element.setProjectName(method.getProjectName());
			element.setMethodbody(transferedContent);
			
			element.setShortestMethodName(AstUtil.getShortestMethodName(method.getName()));
			element.setMethodname(method.getName());
			element.setClassName(method.getClassName());
			element.setTag(method.getTags());
			element.setPackageposition(method.getClassName());
			element.setAccessSameVariableMethods(method.getAccessSameVariableMethods());

			if (!extendPhase)
			{

				// 3
				//set tokens, they are not in the same object with method
				for (TokenInMethod tokenInMethod : totalTokenInMethodList)
				{
					if (tokenInMethod.getMethodName().equals(element.getMethodname()))
					{
						element.setTokens(tokenInMethod.getTokens());
						break;
					}
				}
				
				// 4
				// set the methods and class call or are called by the element.
				for (MethodInvocation invocation : projectDAL.getMethodInvocationList())
				{
					if (invocation.getCallMethodName().equals(method.getName()))
					{
						if (element.getCallmethods().contains(invocation.getCalledMethodName()) == false)
							element.addCallMethod(invocation.getCalledMethodName());
						String callClass = AstUtil.getClassName(invocation.getCalledMethodName());
						if (!element.getCallClassList().contains(callClass))
						{
							element.addCallClass(callClass);
						}

					}
					if (invocation.getCalledMethodName().equals(method.getName()))
					{
						if (element.getCalledBymethods().contains(invocation.getCallMethodName()) == false)
							element.addCalledByMethod(invocation.getCallMethodName());
						String calledClass = AstUtil.getClassName(invocation.getCallMethodName());
						if (!element.getCalledClassList().contains(calledClass))
							element.addCalledClass(calledClass);
					}
				}

				// 5
				//set belonging variable
				for (Variable variable : projectDAL.getVariableList())
				{
					if (variable.getBelongMethod().equals(method.getName()))
					{
						if (element.getAccessfields().contains(variable.getDataType()) == false)
							element.addAccessField(variable.getDataType());
					}
				}

			}

			totalElements.add(element);

		}

		// 6
		// set neighbor method ?
		for (Element element : totalElements)
		{
			for (NeiborMethod neiborMethod : projectDAL.getNeiborMethods())
			{
				if (neiborMethod.getName().equals(element.getMethodname()))
				{
					String[] neibors = neiborMethod.getNeibors().split("[;]");
					for (String neibor : neibors)
					{
						Element neiborElement = findElementByName(neibor);
						element.addNeiborElement(neiborElement);

					}
				}
			}

			String[] accessSameVariableMethods = element.getAccessSameVariableMethods().split("[;]");
			for (String accessSameVariableMethod : accessSameVariableMethods)
			{
				Element e = findElementByName(accessSameVariableMethod);
				element.addAccessSameVariableElement(e);
			}

		}

	}

	private void initialCloneClassList()
	{
		for (CloneClass cloneClass : cloneClassList)
		{
			Element element = findElementByName(cloneClass.getMethodName());
			cloneClass.setElement(element);
		}

	}

	private String[] getRandomIDs(Element[] selectedElements, String[] selectedIDs, int count)
	{
		String[] result = new String[count];
		result = Arrays.copyOf(selectedIDs, selectedIDs.length + count);
		int randomID;
		Random random = new Random();
		boolean reduplicate;
		for (int i = 0; i < count; i++)
		{
			do
			{
				reduplicate = false;
				randomID = random.nextInt(totalElements.size());
				for (int k = 0; k < selectedElements.length; k++)
					if (selectedElements[k].getId().equals(totalElements.get(randomID)))
						reduplicate = true;

				for (int k = 0; k < result.length; k++)
				{
					if (result[k] != null && result[k].equals(totalElements.get(randomID).getId()))
						reduplicate = true;
				}

			}
			while (reduplicate);
			result[selectedIDs.length + i] = totalElements.get(randomID).getId();
		}
		return result;
	}

	private void initialHintCounts()
	{
		for (int i = 0; i < hintCounts.length; i++)
			hintCounts[i] = 0;
	}

	private ExperimentType currentExperimentType;

	public void performExperiment(ExperimentType type)
	{
		
		
		List<Feature>  features = projectDAL.getFeatures(5);
		
		experimentDataSet = new String[features.size()][];
		for(int i =0; i< features.size(); i++)
		{
			String methods = features.get(i).getRelevantElements();			
			experimentDataSet[i] = methods.split("[;]");
			
		}
		/*experimentDataSet[0] = new String[]
		{ "EditPane.EditPane", "EditPane.propertiesChanged", "options.GutterOptionPane._init", "options.GutterOptionPane._save",
				"options.GutterOptionPane.isGutterEnabled", "textarea.Gutter.Gutter", "textarea.Gutter.setBorder(javax.swing.border.Border)",
				"textarea.Gutter.setFont", "textarea.Gutter.setGutterEnabled", "textarea.Gutter.getPreferredSize",
				"textarea.Gutter.setSelectionPopupHandler", "textarea.Gutter.paintLine", "textarea.Gutter.MouseHandler.mousePressed",
				"textarea.Gutter.MouseHandler.mouseDragged", "textarea.Gutter.MouseHandler.getFoldEndOffset",
				"textarea.Gutter.MouseHandler.mouseReleased" };		*/
		
		for (int dataSetIndex = 0; dataSetIndex < experimentDataSet.length; dataSetIndex++)
		{
			for(ExperimentType value: ExperimentType.values())
			{
				currentExperimentType = value;
				System.out.println("*************************  " + value.toString() + "  ***************************");
				doExperiment(dataSetIndex, features.get(dataSetIndex).getID());
			}

		}
		

	}

	private void doExperiment(int dataSetIndex, String featureID)
	{
		System.out.println("第" + dataSetIndex + "組實驗數據");
		int experimentIndex = 0;
		int dataSetSize = experimentDataSet[dataSetIndex].length;
		Element[] experimentElements = new Element[dataSetSize];
		for (int i = 0; i < dataSetSize; i++)
		{
			experimentElements[i] = findElementByPartName(experimentDataSet[dataSetIndex][i]);
			if(experimentElements[i] == null)
			{
				System.out.println("第" + dataSetIndex + "组数据存在错误！");
				return;
			}
		}
		List<ExperimentResult> experimentResults = new ArrayList<ExperimentResult>();
		ExperimentResult experimentResult = new ExperimentResult();
		experimentResult.setExperimentID(Experiment.T1F0);
		experimentResult.setFeatureID(featureID);
		experimentResult.setType(ExperimentType.fullLink.toString());
		
		System.out.println("實驗 " + experimentIndex ++);
		System.out.println("從結果中取一條進行擴展，最終正確結果的出現情況");
		initialHintCounts();
		for (int i = 0; i < dataSetSize; i++)
		{
			String[] selectedIDs = new String[]
			{ experimentElements[i].getId() };
			calculateHint(dataSetIndex, selectedIDs);
		}
		printHintCounts(experimentElements.length);		
		experimentResult.setResult(hintCounts, experimentElements.length);
		experimentResults.add(experimentResult);
		
		
		

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从结果中进行任取2个，最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 1; i++)
		{

			for (int j = i + 1; j < dataSetSize; j++)
			{
				String[] selectedIDs = new String[]
				{ experimentElements[i].getId(), experimentElements[j].getId() };
				calculateHint(dataSetIndex, selectedIDs);

			}
		}
		printHintCounts(dataSetSize * (dataSetSize - 1) / 2);
		
		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从结果中任取3个，最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 2; i++)
		{

			for (int j = i + 1; j < dataSetSize - 1; j++)
			{
				for (int k = j + 1; k < dataSetSize; k++)
				{
					String[] selectedIDs = new String[]
					{ experimentElements[i].getId(), experimentElements[j].getId(), experimentElements[k].getId() };
					calculateHint(dataSetIndex, selectedIDs);
				}
			}
		}
		printHintCounts(dataSetSize * (dataSetSize - 1) * (dataSetSize - 2) / 6);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取1个,从错误结果中随机取一个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 1; i++)
		{
			String[] selectedIDs = new String[]
			{ experimentElements[i].getId() };
			selectedIDs = getRandomIDs(experimentElements, selectedIDs, 1);
			calculateHint(dataSetIndex, selectedIDs);

		}
		printHintCounts(dataSetSize);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取1个,从错误结果中随机取2个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 1; i++)
		{
			String[] selectedIDs = new String[]
			{ experimentElements[i].getId() };
			selectedIDs = getRandomIDs(experimentElements, selectedIDs, 2);
			calculateHint(dataSetIndex, selectedIDs);

		}
		printHintCounts(dataSetSize);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取1个,从错误结果中随机取3个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 1; i++)
		{
			String[] selectedIDs = new String[]
			{ experimentElements[i].getId() };
			selectedIDs = getRandomIDs(experimentElements, selectedIDs, 3);
			calculateHint(dataSetIndex, selectedIDs);

		}
		printHintCounts(dataSetSize);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取1个,从错误结果中随机取4个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 1; i++)
		{
			String[] selectedIDs = new String[]
			{ experimentElements[i].getId() };
			selectedIDs = getRandomIDs(experimentElements, selectedIDs, 4);
			calculateHint(dataSetIndex, selectedIDs);

		}
		printHintCounts(dataSetSize);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取1个,从错误结果中随机取5个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 1; i++)
		{
			String[] selectedIDs = new String[]
			{ experimentElements[i].getId() };
			selectedIDs = getRandomIDs(experimentElements, selectedIDs, 5);
			calculateHint(dataSetIndex, selectedIDs);

		}
		printHintCounts(dataSetSize);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取2个,从错误结果中随机取1个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 1; i++)
		{
			for (int j = i + 1; j < dataSetSize; j++)
			{
				String[] selectedIDs = new String[]
				{ experimentElements[i].getId(), experimentElements[j].getId() };
				selectedIDs = getRandomIDs(experimentElements, selectedIDs, 1);
				calculateHint(dataSetIndex, selectedIDs);
			}
		}
		printHintCounts(dataSetSize * (dataSetSize - 1) / 2);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取2个,从错误结果中随机取2个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 1; i++)
		{
			for (int j = i + 1; j < dataSetSize; j++)
			{

				String[] selectedIDs = new String[]
				{ experimentElements[i].getId(), experimentElements[j].getId() };
				selectedIDs = getRandomIDs(experimentElements, selectedIDs, 2);
				calculateHint(dataSetIndex, selectedIDs);
			}

		}
		printHintCounts(dataSetSize * (dataSetSize - 1) / 2);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取2个,从错误结果中随机取3个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 2; i++)
		{

			for (int j = i + 1; j < dataSetSize - 1; j++)
			{
				String[] selectedIDs = new String[]
				{ experimentElements[i].getId(), experimentElements[j].getId() };
				selectedIDs = getRandomIDs(experimentElements, selectedIDs, 3);
				calculateHint(dataSetIndex, selectedIDs);
			}
		}
		printHintCounts(dataSetSize * (dataSetSize - 1) / 2);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取2个,从错误结果中随机取4个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 2; i++)
		{

			for (int j = i + 1; j < dataSetSize - 1; j++)
			{
				String[] selectedIDs = new String[]
				{ experimentElements[i].getId(), experimentElements[j].getId() };
				selectedIDs = getRandomIDs(experimentElements, selectedIDs, 4);
				calculateHint(dataSetIndex, selectedIDs);
			}
		}
		printHintCounts(dataSetSize * (dataSetSize - 1) / 2);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取2个,从错误结果中随机取5个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 2; i++)
		{

			for (int j = i + 1; j < dataSetSize - 1; j++)
			{
				String[] selectedIDs = new String[]
				{ experimentElements[i].getId(), experimentElements[j].getId() };
				selectedIDs = getRandomIDs(experimentElements, selectedIDs, 5);
				calculateHint(dataSetIndex, selectedIDs);
			}
		}
		printHintCounts(dataSetSize * (dataSetSize - 1) / 2);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取3个,从错误结果中随机取1个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 2; i++)
		{
			for (int j = i + 1; j < dataSetSize - 1; j++)
			{
				for (int k = j + 1; k < dataSetSize; k++)
				{
					String[] selectedIDs = new String[]
					{ experimentElements[i].getId(), experimentElements[j].getId(), experimentElements[k].getId() };
					selectedIDs = getRandomIDs(experimentElements, selectedIDs, 1);
					calculateHint(dataSetIndex, selectedIDs);
				}
			}
		}
		printHintCounts(dataSetSize * (dataSetSize - 1) * (dataSetSize - 2) / 6);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取3个,从错误结果中随机取2个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 2; i++)
		{
			for (int j = i + 1; j < dataSetSize - 1; j++)
			{
				for (int k = j + 1; k < dataSetSize; k++)
				{
					String[] selectedIDs = new String[]
					{ experimentElements[i].getId(), experimentElements[j].getId(), experimentElements[k].getId() };
					selectedIDs = getRandomIDs(experimentElements, selectedIDs, 2);
					calculateHint(dataSetIndex, selectedIDs);
				}
			}
		}
		printHintCounts(dataSetSize * (dataSetSize - 1) * (dataSetSize - 2) / 6);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取3个,从错误结果中随机取3个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 2; i++)
		{
			for (int j = i + 1; j < dataSetSize - 1; j++)
			{
				for (int k = j + 1; k < dataSetSize; k++)
				{
					String[] selectedIDs = new String[]
					{ experimentElements[i].getId(), experimentElements[j].getId(), experimentElements[k].getId() };
					selectedIDs = getRandomIDs(experimentElements, selectedIDs, 3);
					calculateHint(dataSetIndex, selectedIDs);
				}
			}
		}
		printHintCounts(dataSetSize * (dataSetSize - 1) * (dataSetSize - 2) / 6);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取3个,从错误结果中随机取4个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 2; i++)
		{
			for (int j = i + 1; j < dataSetSize - 1; j++)
			{
				for (int k = j + 1; k < dataSetSize; k++)
				{
					String[] selectedIDs = new String[]
					{ experimentElements[i].getId(), experimentElements[j].getId(), experimentElements[k].getId() };
					selectedIDs = getRandomIDs(experimentElements, selectedIDs, 4);
					calculateHint(dataSetIndex, selectedIDs);
				}
			}
		}
		printHintCounts(dataSetSize * (dataSetSize - 1) * (dataSetSize - 2) / 6);

		System.out.println("實驗" + experimentIndex++);
		initialHintCounts();
		System.out.println("从正确结果中进行任取3个,从错误结果中随机取5个,最终正确结果的出现情况");
		for (int i = 0; i < dataSetSize - 2; i++)
		{
			for (int j = i + 1; j < dataSetSize - 1; j++)
			{
				for (int k = j + 1; k < dataSetSize; k++)
				{
					String[] selectedIDs = new String[]
					{ experimentElements[i].getId(), experimentElements[j].getId(), experimentElements[k].getId() };
					selectedIDs = getRandomIDs(experimentElements, selectedIDs, 5);
					calculateHint(dataSetIndex, selectedIDs);
				}
			}
		}
		printHintCounts(dataSetSize * (dataSetSize - 1) * (dataSetSize - 2) / 6);
		
	
	}

	private void printHintCounts(int totalCount)
	{
		for (int i = 1; i <= hintCounts.length; i++)
		{
			System.out.println("前" + i * 10 + "个结果，共命中 " + hintCounts[i - 1] + " 平均每组数据命中 " + hintCounts[i - 1] / (1.0 * totalCount));

		}
	}

	public synchronized List<Element> searchByKeywords(List<Element> elements, String keywords, String project)
	{
		List<Element> result = new ArrayList<Element>();
		satifiedMethodList = new ArrayList<Method>();
		List<String> regularExpressions = CommUtil.getCorrectRegularExpression(keywords);
		for (String expression : regularExpressions)
		{
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher;
			
			ExtConstant ec = ExtConstant.getInstance();
			if(ec.EXT_FLAG==ExtConstant.EXT_MFIE)
				DBHelper.openConn(); 
			
			for (Element element : elements)
			{
				if (element.getProjectName().equals(project))
				{
					matcher = pattern.matcher(element.getMethodbody());
					if (matcher.find())
					{
						String simpleBody = CommUtil.getAroundWords(element.getMethodbody(), matcher.start());
						if (simpleBody.length() > 20)
							element.setMethodsimplebody(simpleBody);
						if (!result.contains(element))
						{
							result.add(element);
							
							if(ec.EXT_FLAG==ExtConstant.ORI_MFIE){
								satifiedMethodList.add(projectDAL.getMethodByName(element.getMethodname()));
							}
							else if(ec.EXT_FLAG==ExtConstant.EXT_MFIE){
								satifiedMethodList.add(getMethodByName_Project(project, element.getMethodname()));
							}
							
						}
					}
				}
			}
			
			if(ec.EXT_FLAG==ExtConstant.EXT_MFIE)
				DBHelper.closeConn();
			
		}
		return result;
	}
	
	public synchronized List<Element> _searchByKeywords(List<Element> elements, String keywords, String project)
	{
		List<Element> result = new ArrayList<Element>();
		satifiedMethodList = new ArrayList<Method>();
		List<String> regularExpressions = CommUtil.getCorrectRegularExpression(keywords);
		for (String expression : regularExpressions)
		{
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher;
			for (Element element : elements)
			{
				if (element.getProjectName().equals(project))
				{
					matcher = pattern.matcher(element.getMethodbody());
					if (matcher.find())
					{
						String simpleBody = CommUtil.getAroundWords(element.getMethodbody(), matcher.start());
						if (simpleBody.length() > 20)
							element.setMethodsimplebody(simpleBody);
						if (!result.contains(element))
						{
							result.add(element);
							satifiedMethodList.add(projectDAL.getMethodByName(element.getMethodname()));
						}
					}
				}
			}
		}
		return result;
	}

	public synchronized List<Element> searchByKeywords(String keywords, String project)
	{

		return searchByKeywords(totalElements, keywords, project);
	}
	
	public synchronized List<Element> searchByKeywordsLucene(String keywords, String project)
	{
		
		long start = System.currentTimeMillis();
		String[][] k = Indexer.search(Indexer.indexPath,"methodContent",keywords,new String[]{"methodName"});

		long end = System.currentTimeMillis();
		System.out.println("index:"+(end-start));
		return getElementListFromDB(k, project);
	}

	private List<Element> getElementListFromDB(String[][] k, String project) {
		//for method getMethodByName_Project
		DBHelper.openConn();
		
		List<Element> ret = new ArrayList<Element>();
		satifiedMethodList = new ArrayList<Method>();
		
		
		for (String[] terms : k) {
			
			if(count)
				start = System.currentTimeMillis();
			
			Method newMethod = getMethodByName_Project(project, terms[0]);
			
			satifiedMethodList.add(newMethod);
			
			if(count){
				end = System.currentTimeMillis();
				countMillis[0]+=(end-start);
				start = System.currentTimeMillis();
			}
			
			if(newMethod!=null)
				ret.add(getElementFromMethod(newMethod));
			if(count){
				end = System.currentTimeMillis();
				countMillis[1]+=(end-start);
			}
		}
		
		if(count){
			System.out.println("search method:"+countMillis[0]);
			System.out.println("complete element:"+countMillis[1]);
		}
		
		//for method getMethodByName_Project
		DBHelper.closeConn();
		
		return ret;
	}

	private static int c=0;//count for id
	
	/**
	 * Before the invocation of this method, there must be a invocation of DBHelper.openConn() 
	 * and after it, there must be a invocation of DBHelper.closeConn(). If not, this setting
	 * feature will be passed to the caller of this method.
	 * 
	 * @param project
	 * @param methodName
	 * @return
	 */
	private static Method getMethodByName_Project(String project, String methodName) {
		String sql = "SELECT className,returnType,modifies,throwExceptions,isConstructor,content,PNGwordcloud,tags, accessSameVariableMethods  FROM "
				+ DBHelper.METHOD_TABLE_NAME
				+ "  where projectName ='"
				+ project + "' and name='"+methodName+"'";
		ResultSet rs = DBHelper.executeQuery(sql);
		
		Method newMethod = null;
		try {
			while (rs.next()) {
				newMethod = new Method();
				newMethod.setName(methodName);
				newMethod.setClassName(rs.getString(1));
				newMethod.setReturnType(rs.getString(2));
				newMethod.setModifier(rs.getString(3));
				newMethod.setExceptions(rs.getString(4));
				newMethod.setConstructor(rs.getBoolean(5));
				newMethod.setContent(rs.getString(6));
				newMethod.setTagCloud(rs.getBytes(7));
				newMethod.setTags(rs.getString(8));
				newMethod.setAccessSameVariableMethods(rs.getString(9));
				newMethod.setProjectName(project);
				newMethod.setId(""+c++);
			}
			
			rs.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return newMethod;
	}
	
	private static Element getElementFromMethod(Method method) {
		Element element = new Element();
		/*
		 * There's no id setting
		 */
		
		///1
		// set simple body
		String transferedContent = CommUtil.transferContent(method.getContent()); 
		if (transferedContent.length() > lengthOfSnapshot)
			element.setMethodsimplebody(transferedContent.substring(0, lengthOfSnapshot));
		else
		{
			element.setMethodsimplebody(transferedContent);
		}
		
		///2
		//set elements info
		element.setProjectName(method.getProjectName());
		element.setMethodbody(transferedContent);
		
		element.setShortestMethodName(AstUtil.getShortestMethodName(method.getName()));
		element.setMethodname(method.getName());
		element.setClassName(method.getClassName());
		element.setTag(method.getTags());
		element.setPackageposition(method.getClassName());
		element.setAccessSameVariableMethods(method.getAccessSameVariableMethods());
		element.setId(method.getId());
		
		if (!extendPhase)
		{

			///3
			//set tokens, they are not in the same object with method
			TokenInMethod tokenInMethod = ProjectDAL.getTokenInMethod(element.getMethodname(), element.getProjectName());
			if(tokenInMethod!=null)
				element.setTokens(tokenInMethod.getTokens());
				
			
			///4
			// set the methods and class call or are called by the element.
			
			// methods element calling
			element.setCallmethods(ProjectDAL.getCallMehtod(method.getName(), method.getProjectName()));
			for(String callMethod:element.getCallmethods()){
				String callClass = AstUtil.getClassName(callMethod);
				element.addCallClass(callClass);
			}
			// methods element was called
			element.setCalledBymethods(ProjectDAL.getCalledMehtod(method.getName(), method.getProjectName()));
			for(String calledMethod:element.getCalledBymethods()){
				String calledClass = AstUtil.getClassName(calledMethod);
				element.addCalledClass(calledClass);
			}
			

			// 5 !
			//set belonging variable
//			for (Variable variable : projectDAL.getVariableList())
//			{
//				if (variable.getBelongMethod().equals(method.getName()))
//				{
//					if (element.getAccessfields().contains(variable.getDataType()) == false)
//						element.addAccessField(variable.getDataType());
//				}
//			}

		}

//		// 6
//		// set neighbor method ?
//		for (Element element : totalElements)
//		{
//			for (NeiborMethod neiborMethod : projectDAL.getNeiborMethods())
//			{
//				if (neiborMethod.getName().equals(element.getMethodname()))
//				{
//					String[] neibors = neiborMethod.getNeibors().split("[;]");
//					for (String neibor : neibors)
//					{
//						Element neiborElement = findElementByName(neibor);
//						element.addNeiborElement(neiborElement);
//	
//					}
//				}
//			}
//	
//			String[] accessSameVariableMethods = element.getAccessSameVariableMethods().split("[;]");
//			for (String accessSameVariableMethod : accessSameVariableMethods)
//			{
//				Element e = findElementByName(accessSameVariableMethod);
//				element.addAccessSameVariableElement(e);
//			}
//	
//		}
		
		return element;
	}

	private List<SimilarAction> sortListByDegree(Map<String,SimilarAction> similarElementsM, int count)
	{
		ArrayList<SimilarAction> similarElements = new ArrayList<SimilarAction>();
		for(SimilarAction action : similarElementsM.values()){
			similarElements.add(action);
		}
		List<SimilarAction> result = new ArrayList<SimilarAction>();
		for (int i = 0; similarElements.size() > 0 && i < count; i++)
		{
			SimilarAction largest = similarElements.get(0);
			for (int index = 1; index < similarElements.size(); index++)
			{
				if (similarElements.get(index).getDegree() > largest.getDegree())
				{
					largest = similarElements.get(index);
				}
			}
			largest.setDegree(Math.round(largest.getDegree() * 100) / 100.0f);
			result.add(largest);
			similarElements.remove(largest);
		}
		return result;
	}

}

package cn.edu.fudan.se.fca;
import java.util.List;
import java.util.StringTokenizer;


public class FCAService
{
	public static void main(String[] args)
	{		 
		 String     context = "shui(doctor,play);wei(teacher, doctor, glasses);yang(master, play);px(teacher, doctor, play, glasses)";      
		 FCAService fcaService = new FCAService();
		 fcaService.buildConcepts(context);
		 
	}
	LatticePanel latticePanel;
	/**
	 * @param context the format reference to "shui(doctor,play);wei(teacher, doctor, glasses);yang(master, play);px(teacher, doctor, play, glasses)"
	 */
	public void buildConcepts(String context)
	{
		 Context       formalContext = new Context();  
         StringTokenizer objectExpression;
         String          partExpr;
         int             i;
         int             j;    
         String          objectName;         
         int             objectIndex;
         String          attributes;    
         StringTokenizer attributeExpression; 
         String          attributeName; 
         int             attributeIndex;
         for( objectExpression = new StringTokenizer( context, ";" ); objectExpression.hasMoreTokens(); ) {
                 partExpr = objectExpression.nextToken();
                 i = partExpr.lastIndexOf( '(' );
                 j = partExpr.lastIndexOf( ')' );
                 objectName = partExpr.substring( 0, i );
                 objectName = objectName.trim();
                 objectIndex = -1;                
                 if( objectName.length() > 0 ) 
                         objectIndex = formalContext.addObjectIfNotExisting( objectName );
                 if( j-i != 0 ) {
                         attributes = partExpr.substring( i+1, j );
                         for( attributeExpression = new StringTokenizer( attributes, "," ); attributeExpression.hasMoreTokens(); ) {
                                 attributeName = attributeExpression.nextToken();
                                 attributeName = attributeName.trim();
                                 attributeIndex = formalContext.addAttributeIfNotExisting( attributeName );
                                 if(attributeIndex == -1)
                                	 System.out.println(attributeName);
                                 if( objectIndex != -1 )
                                         formalContext.pairIsRelated( objectIndex, attributeIndex );
                         }
                 }
         }                     
         
         
         
         latticePanel = new LatticePanel(  formalContext );
         
	}
	
	public LatticeNode getLatticeNodeByID(String id)
	{
		return latticePanel.getNodeByID(id);
	}

	public LatticeNode getRootNode()
	{
		return latticePanel.getSupreNode();
		
	}
	
	public List<LatticeNode> getChildren(LatticeNode node)
	{
		return latticePanel.getChildren(node);
	}
	
	
}

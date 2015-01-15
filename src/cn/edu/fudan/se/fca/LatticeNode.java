package cn.edu.fudan.se.fca;

import java.util.UUID;

//
// 2001-07-09; Holger Zahnleiter
//
// Represents a node/Concept in a Formal Concept Lattice diagram.
//
  
public class LatticeNode
{     
        public static final String SUPRE_NODE_NAME = "SUPRE_NODE";
        public static final String INFI_NODE_NAME = "INFI_NODE";
        //---- Data Elements -------------------------------------------------
        double x = 0;
        double y = 0;
        String objectNames = ""; 
        
        boolean isSuper = false;
        boolean isInfi = false;
                    
        //---- Methods -------------------------------------------------------   
        
        public boolean isSuper()
		{
			return isSuper;
		}

		public void setSuper(boolean isSuper)
		{
			this.isSuper = isSuper;
		}

		public boolean isInfi()
		{
			return isInfi;
		}

		public void setInfi(boolean isInfi)
		{
			this.isInfi = isInfi;
		}


        public String getObjectNames()
		{
			return objectNames;
		}

		public void setObjectNames(String objectNames)
		{
			this.objectNames = objectNames;
		}

		public String getAttributeNames()
		{
			return attributeNames;
		}

		public void setAttributeNames(String attributeNames)
		{
			this.attributeNames = attributeNames;
		}

		public String getId()
		{
			return id;
		}

		String attributeNames = "";    
        String id = "";
        public LatticeNode()
        {
        	id = UUID.randomUUID().toString();
        }
        //---- Methods ------------------------------------------------------- 
        
        // 
        // When a Lattice get's calculated the positions are set to equidistant
        // values where the distance between direct naighbours is one pixel only.
        // This is done to simplify the calculation algorithm. The x-/y-positions
        // are directly produced by two nested loops.
        // This function scale these short distances up and moves each node
        // 40 pixels to the left and downwards.
        //    
        void adjustPosition( int x, int y )
        {              
                this.x = x*60+40;
                this.y = y*60+40;
        }
             
}
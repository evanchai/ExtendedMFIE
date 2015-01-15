package cn.edu.fudan.se.fca;

//
// 2001-07-10; Holger Zahnleiter 
//
// This class implements the relation (Incidence Relation I) which tells
// wheather an object o has a certain attribute a or not (oIa).
// For each object name only the index of the name within the name set
// is used to identify the object in this matrix. An attribute name is
// represented as an index in the attribute set, too.
//


public class AdjacenceMatrix
{
        
        //---- Data Elements -------------------------------------------------
        
        //
        // A maximum of MAX_DIMENSION objects and attributes are possible.
        //
        private final int         MAX_DIMENSION = 2000;
        private       Boolean[][] matrix = new Boolean[MAX_DIMENSION][MAX_DIMENSION];      
        
        //---- Methods -------------------------------------------------------
        
        //
        // Adds a pair of Object o and Attribute a to the relation I so that oIa.
        //
        public void setPair( int o, int a )
        {
        	try
			{
        		if( o < MAX_DIMENSION && a < MAX_DIMENSION ) 
                    matrix[o][a] = new Boolean( true );
			}
			catch (Exception e)
			{
				System.out.println("Error");
			}
                
                
        }
                                                     
        //
        // Removes a pair of Object o and Attribute a from the relation I.
        //
        public void unsetPair( int o, int a )
        {
                if( o < MAX_DIMENSION && a < MAX_DIMENSION )
                        matrix[o][a] = new Boolean( false );
        }
                                                                     
        //
        // Checks wheather an Object/Attribute pair is element of the relation I.
        //
        public Boolean isPairSet( int o, int a )
        {
                if( o < MAX_DIMENSION && a < MAX_DIMENSION ) {
                        return matrix[o][a];
                }
                return null;
        }        
          
        //
        // Returns all attrubutes A={a1,a2,...,an} where oIa for all a element A.
        //
        public IntegerSet getAllAttributesForObject( int o )
        {
                IntegerSet result = new IntegerSet();
                int        i;
                for( i=0; i<MAX_DIMENSION; i++ ) {
                        if( isPairSet( o, i ) != null && isPairSet( o, i ).booleanValue() == true  )
                                result.addElement( i );
                }            
                return result;
        } 
          
        //
        // Returns all objects O={o1,o2,...,on} where oIa for all o element O.
        //
        public IntegerSet getAllObjectsForAttribute( int a )
        {
                IntegerSet result = new IntegerSet();
                int        i;
                for( i=0; i<MAX_DIMENSION; i++ ) {
                        if( isPairSet( i, a ) != null && isPairSet( i, a ).booleanValue() == true )
                                result.addElement( i );
                }        
                return result;
        }    
          
        //
        // Returns all Attributes A={a1,a2,...,a3} which all given Objects
        // O={o1,o2,...,o3} do have in common. This is the operator know
        // from Formal Concept Analysis as Arrow Up.
        //
        public IntegerSet getAllCommonAttributes( IntegerSet objectSet )
        {
                IntegerSet result = new IntegerSet();        
                IntegerSet current;
                int        i;
                for( i=0; i<MAX_DIMENSION; i++ ) { 
                        if( objectSet.contains(i) ) {
                                current = new IntegerSet();
                                for( int j=0; j<MAX_DIMENSION; j++ ) {
                                        if( isPairSet( j, i ) != null &&
                                            isPairSet( j, i ).booleanValue() == true )
                                                current.addElement( j );       
                                }                                       
                                if( result.isEmpty() ) result = current;         
                                else result = result.getIntersection( current );
                        }
                }
                return result;   
        }
                                                                  
        //
        // Returns a set of Objects O={o1,o2,...,on} which all have the given
        // set of Attributes A={a1,a2,...,an} in common. This operator if
        // known from FCA as Arrow Down.
        //
        public IntegerSet getAllCommonObjects( IntegerSet attributeSet )
        {
                IntegerSet result = new IntegerSet();        
                IntegerSet current;
                int        i;
                for( i=0; i<MAX_DIMENSION; i++ ) { 
                        if( attributeSet.contains(i) ) {
                                current = new IntegerSet();
                                for( int j=0; j<MAX_DIMENSION; j++ ) {
                                        if( isPairSet( i, j ) != null &&
                                            isPairSet( i, j ).booleanValue() == true )
                                                current.addElement( j );       
                                }                                       
                                if( result.isEmpty() ) { 
                                        result = current;  
                                }
                                else
                                        result = result.getIntersection( current );
                        }
                }
                return result;
        }

}
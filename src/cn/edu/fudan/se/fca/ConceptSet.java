package cn.edu.fudan.se.fca;
//
// 2001-07-10; Holger Zahnleiter
//      
// A Formal Context contains several Formal Concepts. Since a Concept
// must exist only once per Context, this class serves as a container
// ensuring that equal Concepts are not added multiple times.
//


public class ConceptSet
{
        
        //---- Data Elements -------------------------------------------------
        private final int       MAX_ELEMENTS = 20;        
        private       int       nbElements = 0;
        private       Concept[] element = new Concept[MAX_ELEMENTS];  
                                                                              
        //---- Methods -------------------------------------------------------
                                                                              
        //
        // How many Concepts does this set contain?
        //
        public int getNumberOfConcepts()
        {
                return nbElements;
        }   
          
        //
        // Does this set (already) contain the givel Concept?
        //
        public boolean contains( Concept searchConcept )         
        {                           
                int i;
                for( i=0; i<nbElements; i++ )
                        if( element[i].equals(searchConcept) ) return true;
                return false;
        }   
          
        //
        // Return the Concept if it is already element of the set.
        //
        public Concept findConcept( Concept searchConcept )         
        {            
                int i;
                for( i=0; i<nbElements; i++ )
                        if( element[i].equals(searchConcept) ) return element[i];
                return null;
        }                          
          
        //
        // The algorithm which produces all Concepts may produce the
        // same Concept for any Attribute/Object used as starting point.
        // These Concepts are equal when watching the contained Objects/Attributes
        // but differ when watching for the inducing Objects/Attributes and
        // remuved Objects/Attributes respectively. If an equal Concept
        // is already element of the set, these "secondary" informations are
        // keept by merging it into the Concept already pert of the set.
        //
        public void addConcept( Concept NewConcept )
        {           
                Concept foundConcept = null;
                foundConcept = findConcept( NewConcept );
                if( foundConcept == null ) {
                        if( nbElements < MAX_ELEMENTS ) {
                                element[nbElements] = NewConcept;
                                nbElements++;      
                        }
                }   
                else {
                        foundConcept.mergeWith( NewConcept );
                }     
        }       
          
        //
        // Access the indexed Concept (if existing).
        //
        public Concept getConcept( int index )
        {
                if( index < nbElements ) return element[index];
                return null;
        }     
        
}
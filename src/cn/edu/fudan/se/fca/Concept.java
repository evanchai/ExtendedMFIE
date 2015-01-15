package cn.edu.fudan.se.fca;
//
// 2001-07-10; Holger Zahnleiter
//
// This class is a picture of what is called Formal Concept within
// Formal Concept Analysis. A Concept is a pair of sets. One set
// contains Objects (object names) and the other set contains all
// Attributes (attribute names) which all objects do have in common.
// On the other side the Object set contains all Objects which
// share the same Attributes.
//
// This class does not only contain the required sets of Attributes and
// Objects. The class also contains a set of "inducing" Attributes and
// Objects. Those sets are representing all Attributes and Objects
// which lead to the Concept when applying the operator which retrieves
// the Formal Concepts from the Formal Context. Obviously the sets are
// created when creating the Concepts. When drawing the Concept Lattice
// the induced Objects and Attributes are the lables of the points
// within the Line Diagram.
//
// There are another two sets: all "removed" Objects and Attributes.
// When producing the Concepts the four sets mentioned above are
// produced. After that process the "removed" sets are populated with all
// Objects contained in the concept but not contained in the set
// of inducing Objects. The "removed" Attribute set contains all
// Attributes element of the Concept but not part of the inducing
// Attribute set. Remember, when drawing the Line Diagram each Concept
// is represented by one point and labled with the set of "inducing"
// Attributes and Objects. One might recognise that some Attributes
// and Objects might not be part of the lable. But the concept does
// contain these Objects and Attributes which are missing! (The operator
// is extensive, which means that the generated set is growing, containing
// at least the same ammount of elements as the starting set.) 
// These Objects and Attributes are inherited by other Points in the Line
// Diagram. So the list of removed Attributes and Objects does represent
// the order of the Concepts!!! This is used when drawing the Line
// Diagram. 
//

public class Concept
{
                                       
        //---- Data Elements -------------------------------------------------
        
        //
        // CONCEPT
        //All Objects and Attributes represented by the Concept.
        //
        IntegerSet allObjects            = new IntegerSet();
        IntegerSet allAttributes         = new IntegerSet();   
        //
        // LABLES
        // All Objects and Attributes which lead to this Concept when
        // applying the Concept producing operator.
        //
        IntegerSet inducingObjects       = new IntegerSet();
        IntegerSet inducingAttributes    = new IntegerSet();  
        //
        // ORDER
        // All Objects and Attributes which are part of the concept
        // but not part of the inducing objects.
        //
        IntegerSet removedObjects        = new IntegerSet();
        IntegerSet removedAttributes     = new IntegerSet();
        //
        // The corresponding Point/Node in the Line Diagram. Set when drawing
        // the diagram.
        //
        int        associatedLatticeNode = -1; 
        		//
        // When calculating an Concept, this function is used to add an
        // Object which belongs to a Concept. Only the Concept producing
        // Operator is allowed to use this function. The set of removed
        // Object is prepared.                                        
        //
        // Since this function must be exclusively used by the function
        // which calculates the Concepts, this function should be declared
        // as "public". This classes should be part of one package, lets
        // say "FCA" and therefore be available for the Concept producing
        // function (package visibility). The same applies for some more
        // methods in this class.
        // 
        public void addObjects( int formalObject )
        {
                allObjects.addElement( formalObject ); 
                removedObjects.addElement( formalObject );
        }
        
        //
        // When calculating an Concept, this function is used to add an
        // Attribute which belongs to a Concept. Only the Concept producing
        // Operator is allowed to use this function. The set of removed
        // Attributes is prepared.
        //
        public void addAttribute( int formalAttribute )
        {
                allAttributes.addElement( formalAttribute );  
                removedAttributes.addElement( formalAttribute );
        }   
                     
        //
        // When calculating Concepts, the Object which induced this Concept
        // is added in the set containing the "inducing" Objects. This function
        // is allowed for the Concept producing operator only.         
        //
        public void addInducingObject( int formalObject )
        {
                inducingObjects.addElement( formalObject );  
        }
                   
        //
        // When calculating Concepts, the Attribute which induced this Concept
        // is added in the set containing the "inducing" Attributes. This function
        // is allowed for the Concept producing operator only.
        //
        public void addInducingAttribute( int formalAttribute )
        {
                inducingAttributes.addElement( formalAttribute );
        }   
                     
        //
        // When producing Concepts by starting from a single Object or Attribute,
        // any number of Objects or Attributes may lead to the same Concept.
        // In this case only one Concept must exist/is keept. This function merges
        // recently produced equal concepts to the first Concept by keeping the
        // original Objects/Attributes (which are the same for equal Concepts) and
        // adds the induced Objects/Attributes from another. Also the set of
        // removed Attributes/Objects is increased. Wheather two Concepts have to
        // be merged or not is determined by the class modelling a set of Concepts.
        // This class guarantees the uniqueness of a Concept within a Context.
        //  
        public void mergeWith( Concept inducedConcept )
        {        
                inducingObjects.addElements( inducedConcept.inducingObjects ); 
                inducingAttributes.addElements( inducedConcept.inducingAttributes );
                removedObjects.addElements( inducedConcept.removedObjects ); 
                removedAttributes.addElements( inducedConcept.removedAttributes );
        }
                                                   
        //
        // How many Objects does this Concept contain?
        //
        public int getNumberOfObjects()
        {
                return allObjects.getSize();      
        }    
          
        //
        // How many Attributes does this Concept contain?
        //
        public int getNumberOfAttributes()
        {
                return allAttributes.getSize();      
        }
                              
        // 
        // Checks, wheather two concepts are equal or not. Two Concepts are
        // equal if both Object sets are equal and both Attribute sets are equal.
        //
        public boolean equals( Concept otherConcept )
        {
                return (allObjects.equals( otherConcept.allObjects ) &&
                       allAttributes.equals( otherConcept.allAttributes )); 
        }
                        
}  
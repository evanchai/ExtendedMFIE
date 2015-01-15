package cn.edu.fudan.se.fca;
//
// 2001-07-10; Holger Zahnleiter
//              
// This class represents an (one-valued) Formal Context. It is
// a table where the columns represent Attributes (a1, a2, ..., an)
// and each row represents an Object (o1, o2, ..., on) having any
// number of the given Attributes. This is indicated by crosses.
// This table implements a relation between the Objects and Attributes
// which is calles Incidence Relation. A context is written:
// K=(O, A, I).
// 
// Technically this class contains two string sets: one for the
// Objects and one for the Attributes. All methods do operate with
// sets of integer numbers instead of sets of strings. Therefore
// each Object/Attribute is assigned to a integer number where the
// assigned value is the index/position within the string set.
// The relation is represented by a matrix where the x-cooridates
// are associated with the Attributes and the y-coordinates relate
// to the Objects.
//      
// Since this is just a demo, many importand methods are missing.
// One might want to merge Contexts or wants to calculate the
// implications. All that is missing. All classes belonging to that
// demo are very specific for the intended purposes and might be
// extended.
//

 
public class Context
{
        
        //---- Data Elements -------------------------------------------------
        StringSet       formalObjects = new StringSet();
        StringSet       formalAttributes = new StringSet();
        AdjacenceMatrix incidenceRelation = new AdjacenceMatrix();          
        
        //---- Methods -------------------------------------------------------
        
        //
        // Each Object (object name) must be added to the Context only once.
        // The string set does provide that.
        //
        public int addObjectIfNotExisting( String Name )
        {
                return formalObjects.addName( Name );
        }
             
        //
        // Each Attribute (attribute name) must be added to the Context only once.
        // The string set does provide that.
        //
        public int addAttributeIfNotExisting( String Name )
        {
                return formalAttributes.addName( Name );
        }
                                    
        //
        // The given pair of Object and Attribute has to be made an
        // element of the Incidence Relation (oIa). Like mentioned
        // above Objects and Attributes are given as integers not
        // as names.
        //
        public void pairIsRelated( int formalObject, int FormalAttribute )
        {
                incidenceRelation.setPair( formalObject, FormalAttribute );
        }
        
        //
        // Is the given Object and the given Attribute related?
        // oIa = true?
        //
        public boolean isPairRelated( int formalObject, int formalAttribute )
        {
                Boolean currentValue;                
                currentValue = incidenceRelation.isPairSet( formalObject, formalAttribute);
                if( null == currentValue ) return false;
                return currentValue.booleanValue();
        }
        
        //
        // When drawing the Context (as a table) the first column will hold
        // the object names. The longest object name is needed to calculate
        // the width of the first column.
        //
        public String getLongestObjectName()
        {
                return formalObjects.getLongestName();
        }
        
        //
        // Get access to all object names (needed to draw the Context).
        //
        public StringSet getAllObjects()
        {
                return formalObjects;
        }
          
        //
        // Get access to all attribute names (needed to draw the Context).
        //
        public StringSet getAllAttributes()
        {
                return formalAttributes;
        } 
           
        //
        // Constructs a Formal Concept Lattice (Line Diagram) which represents
        // this Formal Context. The function is small becaus it calls some
        // powerfull functions mentioned below.
        //
        public void buildLattice( LatticePanel lattice )
        {
                ConceptSet allConcepts;
                int        i;
                System.out.println( "--------------------------------------------------------" );
                System.out.println( "buildLattice()" );       
                System.out.println( "--------------------------------------------------------" );
                allConcepts = getAllConcepts();
                for( i=0; i<allConcepts.getNumberOfConcepts(); i++ )
                {                   
                        addNodeToLattice( lattice, allConcepts.getConcept( i ) );      
                }
                createEdges( lattice, allConcepts  );      
        }  
                     
        //
        // Calculates the concept for the given Object. Used by the method
        // which calculates all concepts.
        //
        private Concept getConceptForObject( int index )
        {                           
                Concept    result = new Concept();   
                int        i;      
                IntegerSet allAttributes;
                IntegerSet allObjects;
                allAttributes = incidenceRelation.getAllAttributesForObject( index ); 
                allObjects = incidenceRelation.getAllCommonAttributes( allAttributes );
                result.addObjects( index );
                for( i = 0; i<IntegerSet.MAX_ELEMENTS; i++ ) {
                        if( allAttributes.contains( i ) ) result.addAttribute( i );
                }
                for( i = 0; i<IntegerSet.MAX_ELEMENTS; i++ ) {
                        if( allObjects.contains( i ) ) result.addObjects( i );
                }
                return result;
        } 
        
        //
        // Calculates the concept for the given Attribute. Used by the method
        // which calculates all concepts.
        //
        private Concept getConceptForAttribute( int index )
        {                           
                Concept    result = new Concept();  
                int        i;       
                IntegerSet allAttributes;
                IntegerSet allObjects;
                allObjects = incidenceRelation.getAllObjectsForAttribute( index ); 
                allAttributes = incidenceRelation.getAllCommonObjects( allObjects );
                result.addAttribute( index );
                for( i = 0; i<IntegerSet.MAX_ELEMENTS; i++ ) {
                        if( allAttributes.contains( i ) ) result.addAttribute( i );
                }
                for( i = 0; i<IntegerSet.MAX_ELEMENTS; i++ ) {
                        if( allObjects.contains( i ) ) result.addObjects( i );
                }
                return result;
        }
            
        //
        // Calculates all the Concepts contained in the Context by calling the
        // function getConceptForAttribute() for each single Attribute and
        // applying each Object to getConceptForObject(). Some Objects/Attributes
        // may produce/induce the same Concept (see set for inducing Objects/Attributes).
        // This function is of central meaning when constructing a Line Diagram
        // from a Formal Context. So this function, like all other importand functions,
        // prints out information which should help keeping track of what is
        // going on and how the thing is working.  
        //
        // SUPREMUM       
        // This process automatically creates the Sup when: 1.) There is at least one
        // Attribute which all Object does have in common. 2.) There is at least one
        // Object which does not have any Attributes.
        //
        // INFIMUM
        // This process automatikally creates the Inf when: 1.) There is at least one
        // Attribute which is not assigned to any Object. 2.) There is at least one
        // Object which has all Attributes assigned to it.
        //
        public ConceptSet getAllConcepts()
        {
                ConceptSet result = new ConceptSet();
                Concept    currConcept;       
                int        i;
                System.out.println( "\n\t--------------------------------------------------------" );
                System.out.println( "\tgetAllConcepts()" );
                System.out.println( "\t--------------------------------------------------------" );
                StringSetIterator allObjs = formalObjects.getIterator();
                while( allObjs.hasMoreNames() ) {
                        currConcept = getConceptForObject( allObjs.getCurrentNameAsOrdinal() );
                        currConcept.inducingObjects.addElement( allObjs.getCurrentNameAsOrdinal() );
                        result.addConcept( currConcept );
                        allObjs.getNextName();
                }                 
                StringSetIterator allAttrs = formalAttributes.getIterator();
                while( allAttrs.hasMoreNames() ) {
                        currConcept = getConceptForAttribute( allAttrs.getCurrentNameAsOrdinal() );
                        currConcept.inducingAttributes.addElement( allAttrs.getCurrentNameAsOrdinal() );
                        result.addConcept( currConcept );
                        allAttrs.getNextName();
                }        
                for( i=0; i<result.getNumberOfConcepts(); i++ ) {
                        currConcept = result.getConcept( i );
                        currConcept.removedObjects.removeElements( currConcept.inducingObjects );
                        currConcept.removedAttributes.removeElements( currConcept.inducingAttributes );
                } 
                for( i=0; i<result.getNumberOfConcepts(); i++ ) {
                        System.out.println( "\t" + conceptToString( result.getConcept( i ) ) );
                }               
                System.out.println();
                return result;
        }                 
          
        //
        // Constructs the representation which represents the given Concept as a string.
        // Since the (technical) Concepts do not contain names, the string sets which
        // are storing attribute names and object names do provide a function which
        // translates a given Object/Attribute list (as integer set) into a list
        // of symbolic names (separated by comma).
        //
        public String conceptToString( Concept concept )
        {       
                String result;
                result = "Concept=({" + formalObjects.getNameList( concept.allObjects ) + "}; {" +
                          formalAttributes.getNameList( concept.allAttributes ) + "})";   
                result += "\n\t\tinduced by ({" + formalObjects.getNameList( concept.inducingObjects ) + "}; {" +
                          formalAttributes.getNameList( concept.inducingAttributes ) + "}";  
                result += ")\n\t\tremoved: ({" + formalObjects.getNameList( concept.removedObjects ) + "}; {" +
                          formalAttributes.getNameList( concept.removedAttributes ) + "})";
                return result;
        }                   
          
        //
        // This function is needed when creating the Formal Concept Lattice
        // (Line Diagram). A node is created for the given Concept, where
        // the object name lable and the attribute name lable are derived
        // from the "inducing" sets which are part of the given Concept.
        // Each node which is created gets documented by printig out a
        // message.
        //
        private void addNodeToLattice( LatticePanel lattice, Concept currConcept )
        {       
                int nodeIdx;
                nodeIdx = lattice.addNode(
                        formalObjects.getNameList( currConcept.inducingObjects ),
                        formalAttributes.getNameList( currConcept.inducingAttributes )
                );        
                currConcept.associatedLatticeNode = nodeIdx;
                System.out.println( 
                        "Create node ({" + formalObjects.getNameList( currConcept.inducingObjects ) +
                        "}; {" + formalAttributes.getNameList( currConcept.inducingAttributes )+ "})"
                );
                                                        
        }                      
          
        //
        // After all node have been created this function is called to connect
        // all the nodes where the connections depend on the order given by
        // the Concepts. In some cases the process which creates all Concepts
        // also creates Infimum and/or Supremum. In some cases it does not.
        // In this case Inf and/or Sup have to be created artificially.
        //
        private void createEdges( LatticePanel lattice, ConceptSet allConcepts )
        {        
                Concept    outerConcept;
                Concept    innerConcept;
                boolean    hasRemovedAttributes; 
                boolean    hasRemovedObjects;
                IntegerSet intersec;    
                IntegerSet outerAttribs;
                IntegerSet outerObjects;
                IntegerSet innerAttribs;
                IntegerSet innerObjects;
                Concept    infimum = null;
                Concept    supremum = null;         
                int        i;                       
                IntegerSet fromSet = new IntegerSet();
                IntegerSet toSet = new IntegerSet();
                IntegerSet nodesToDraw;
                IntegerSet nextNodes = null;
                int        yLevel = 0;
                System.out.println( "\n\t--------------------------------------------------------" );
                System.out.println( "\tcreateEdges()" );
                System.out.println( "\t--------------------------------------------------------" );
                //
                // Search for the Sup and Inf. If they had not been created automatically
                // supremum and infimum will keept the value null.
                //
                for( i=0; i<allConcepts.getNumberOfConcepts(); i++ ) {
                        outerConcept = allConcepts.getConcept( i ); 
                        if( outerConcept.allObjects.getSize() == formalObjects.getNumberOfNames()
                            || outerConcept.allAttributes.isEmpty() ) {
                                supremum = outerConcept;
                        }  
                        if( outerConcept.allAttributes.getSize() == formalAttributes.getNumberOfNames()
                            || outerConcept.allObjects.isEmpty() ) {
                                infimum = outerConcept;
                        }                                                     
                }                                                           
                //
                // For all nodes (Concepts) create an edge exceppt for the Inf and Sub
                // (in case they have been created automatically).
                //      
                for( i=0; i<allConcepts.getNumberOfConcepts(); i++ ) {
                        outerConcept = allConcepts.getConcept( i );  
                        if( outerConcept == infimum || outerConcept == supremum ) continue;
                        outerObjects = outerConcept.removedObjects;
                        outerAttribs = outerConcept.removedAttributes;                                
                        hasRemovedAttributes = !outerConcept.removedAttributes.isEmpty();       
                        hasRemovedObjects = !outerConcept.removedObjects.isEmpty();  
                        if( !hasRemovedAttributes && !hasRemovedObjects ) continue;
                        for( int j=0; j<allConcepts.getNumberOfConcepts(); j++ ) { 
                                if( j==i ) continue;  
                                innerConcept = allConcepts.getConcept( j ); 
                                if( innerConcept == infimum || innerConcept == supremum ) continue;
                                innerObjects = innerConcept.inducingObjects;
                                innerAttribs = innerConcept.inducingAttributes;
                                if( hasRemovedAttributes ) {   
                                        intersec = outerAttribs.getIntersection( innerAttribs ); 
                                        if( !intersec.isEmpty() ) {   
                                                lattice.addEdge(
                                                        innerConcept.associatedLatticeNode,
                                                        outerConcept.associatedLatticeNode  
                                                );
                                        }
                                }
                                if( hasRemovedObjects ) { 
                                        intersec = outerObjects.getIntersection( innerObjects );
                                        if( !intersec.isEmpty() ) {
                                                lattice.addEdge(
                                                        outerConcept.associatedLatticeNode,
                                                        innerConcept.associatedLatticeNode
                                                );
                                        }
                                }
                        }
                }     
                //
                // Find out which node is a source for an edge and which
                // node is an target for an edge. All nodes which are no
                // targest must have the Sup as ancessor. All nodes which
                // are not source of an edge must have Inf as successor.
                //
                for( i=0; i<lattice.getNumberOfEdges(); i++ ) {
                        fromSet.addElement( lattice.getEdge( i ).from );
                        toSet.addElement( lattice.getEdge( i ).to );        
                }       
                //
                // Create Inf and Sup if not created automatically. Print
                // Sup and Inf for user information.
                //
                System.out.print( "\tsupremum " );                              
                if( supremum==null ) {
                        supremum = new Concept();
                        supremum.associatedLatticeNode = lattice.addNode(LatticeNode.SUPRE_NODE_NAME,LatticeNode.SUPRE_NODE_NAME);    
                        System.out.print( "artificially created: " );
                        System.out.print( conceptToString( supremum ) );  
                }  
                else {
                		
                        System.out.print( "automatically created: " + conceptToString( supremum ) );
                }
                
                System.out.print( "\n\tinfimum " );
                if( infimum==null ) {
                        infimum = new Concept();
                        infimum.associatedLatticeNode = lattice.addNode(LatticeNode.INFI_NODE_NAME,LatticeNode.INFI_NODE_NAME );
                        System.out.print( "artificially  created: " );
                        System.out.println( conceptToString( infimum ) );
                }
                else {
                        System.out.println( "automatically created: " + conceptToString( infimum ) );
                }
                
                //
                // Make up all edges from/to Sup and Inf which have not been drawn
                // before.
                // 
                for( i=0; i<lattice.getNumberOfNodes(); i++ ) { 
                        if( i==supremum.associatedLatticeNode || i==infimum.associatedLatticeNode )
                                continue;
                        if( !fromSet.contains( i ) )
                                lattice.addEdge( i, infimum.associatedLatticeNode );
                        if( !toSet.contains( i ) )
                                lattice.addEdge( supremum.associatedLatticeNode, i );
                }   
                // 
                // Adjust the position of all nodes. Start at Sup which is the
                // highes node in the graph (some kind of root). Then draw all
                // the ancessors of the root and so on.
                //               
                //Microser: get concept by lattice's nodes, and obtain the relationship by lattice's edges.
                nodesToDraw = new IntegerSet( supremum.associatedLatticeNode );
                while( true ) {      
                        nextNodes = new IntegerSet();  
                        int XLevel = 0;                                                  
                        for( i = 0; i < lattice.getNumberOfNodes(); i++ ) {
                                if( nodesToDraw.contains( i ) ) { 
                                        lattice.getNode( i ).adjustPosition( XLevel, yLevel );   
                                        nextNodes.addElements(
                                                lattice.findEdgeDestinations( i )
                                        );  
                                        XLevel++;
                                }        
                        }
                        if( nodesToDraw.contains( infimum.associatedLatticeNode ) ) break;
                        nodesToDraw = nextNodes;
                        yLevel++;
                }
        }           
                 
}
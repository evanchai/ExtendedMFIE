package cn.edu.fudan.se.fca;
//
// 2001-07-10; Holger Zahnleiter
//
// All set related operations are performed by using this integer set
// class. One might have used the class BitSet which is part of the
// JDK. A number is element of the set when it's corresponding entry
// in the array equals 'true'. If the corresponding array entry is
// set 'false' the number is not element of the set.
//
                     
           
public class IntegerSet
{                                              
        
        //---- Data Elements -------------------------------------------------
        public  final static int       MAX_ELEMENTS = 4000;
        private              boolean[] element      = new boolean[MAX_ELEMENTS];  
        
        //---- Construction --------------------------------------------------
        
        //
        // Create an empty set.
        //
        IntegerSet()
        {
                int i;
                for( i=0; i<MAX_ELEMENTS; i++ )
                        element[i] = false;
        }        
                 
        //
        // Create a set including only one element.
        //
        IntegerSet( int initialValue )
        {
                addElement( initialValue );
        }
          
        //
        // Creates a set and copies the given set.
        //
        IntegerSet( IntegerSet initialSet )
        {
                addElements( initialSet );
        }         
          
        //
        // Creates a set which includes all elements from the range
        // [lo; hi].
        //
        IntegerSet( int lo, int hi )
        {
                addAllElementsWithinRange( lo, hi );
        }
 
        //---- Methods -------------------------------------------------------
        
        //
        // Checks if an element is valid and the set is able to handle this
        // element. It's a internal, technical function.
        //
        private boolean isValidIndex( int index )
        {
                return (index>=0 && index<MAX_ELEMENTS);
        }
          
        //
        // Add a single element to the set.
        //
        public void addElement( int newElement )
        {  
                if( isValidIndex( newElement ) )
                        this.element[newElement] = true;
        }      
          
        //
        // Adds a set of elements.
        //
        public void addElements( IntegerSet newElements )
        {
                for( int i=0; i<MAX_ELEMENTS; i++ ) {
                        if( newElements.element[i] ) element[i] = true;
                }
        }   
          
        //
        // Add all ements from the range [lo; hi].
        //  
        public void addAllElementsWithinRange( int lo, int hi )
        {            
                int i;
                int tmp;
                if( !isValidIndex( lo ) || !isValidIndex( hi ) ) return;
                if( lo > hi ) {
                        tmp = lo;
                        lo = hi;
                        hi = tmp;
                }
                for( i=lo; i<=hi; i++ ) element[i] = true;
        }
        
        //
        // Removes a single element from the set.
        //           
        public void removeElement( int element )
        {
                if( isValidIndex( element ) ) this.element[element] = false;
        }  
          
        //
        // Removes all elements which are included in the other set.
        //
        public IntegerSet removeElements( IntegerSet elements )
        {                      
                int        i;
                IntegerSet result = new IntegerSet();
                for( i=0; i<MAX_ELEMENTS; i++ ) {
                        if( elements.element[i] ) {
                                element[i] = false;
                                result.element[i] = true;
                        }
                }        
                return result;
        }    
          
        //
        // Checks if the given number is element of the set.
        //
        public boolean contains( int element )
        {
                if( isValidIndex( element ) ) return this.element[element]; 
                return false;
        }
          
        //
        // Checks if all given numbers are elements of the set.
        //
        public boolean contains( IntegerSet otherSet )
        {             
                int i;
                for( i=0; i<MAX_ELEMENTS; i++ ) {
                        if( otherSet.element[i] && element[i] != otherSet.element[i] )
                                return false; 
                }
                return true;
        }
                        
        //
        // UNION operator:
        // Creates a set which equals the UNION of this set and an other set.
        //
        public IntegerSet getUnion( IntegerSet otherSet )
        {
                int        i;  
                IntegerSet result = new IntegerSet();
                for( i=0; i<MAX_ELEMENTS; i++ ) {
                        result.element[i] = this.element[i] || otherSet.element[i];  
                }
                return result;
        }     
          
        //
        // INTERSECTION operator:
        // Creates a set which equals the INTERSECTION of this set and the
        // other set.
        //
        public IntegerSet getIntersection( IntegerSet otherSet )
        {
                int        i;
                IntegerSet result = new IntegerSet();  
                for( i=0; i<MAX_ELEMENTS; i++ ) {
                        result.element[i] = this.element[i] && otherSet.element[i];
                }                                      
                return result;
        }                               
          
        //
        // DIFFERENCE operator:
        // Creates a set which includes all elements of this set
        // which are not included in the other set.
        //
        public IntegerSet getDifference( IntegerSet otherSet )
        {
                IntegerSet result = new IntegerSet();
                for( int i=0; i<MAX_ELEMENTS; i++ ) { 
                        result.element[i] = this.element[i]  != otherSet.element[i];
                }
                return result;
        }
          
        //
        // True, if the set is empty.
        //
        public boolean isEmpty()
        {
                int i;
                for( i=0; i<MAX_ELEMENTS; i++ ) {
                        if( element[i] == true ) return false;
                }
                return true;
        }   
           
        //
        // Returns the number of elements included in this set.
        //
        public int getSize()
        {
                int i;                                   
                int result = 0;
                for( i=0; i<MAX_ELEMENTS; i++ ) {
                        if( element[i] == true ) result++;
                }
                return result;
        }
          
        // 
        // True, if both sets contain the same elements (if both
        // sets are equal).
        //
        public boolean equals( IntegerSet otherSet )
        {
                for( int i=0; i<MAX_ELEMENTS; i++ )
                        if( element[i] != otherSet.element[i] )
                                return false;
                return true;
        }        
                
}
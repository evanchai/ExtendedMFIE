package cn.edu.fudan.se.fca;
//
// 2001-07-09; Holger Zahnleiter
// Stores a set of (unique) strings (Object and Attribute names).
//
       

public class StringSet
{
        
        //---- Data Elements -------------------------------------------------
        private final int      MAX_NAMES   = 2000;
        private       int      nbNames     = 0;
        private       String[] name        = new String[MAX_NAMES];
        private       String   longestName = "";
                      
        //---- Methods -------------------------------------------------------
        
        //
        // Howmay names does the set contain?
        //
        public int getNumberOfNames()
        {
                return nbNames;
        }
          
        //
        // Checks whether a name is in the set or not. Returns the ordinal
        // number (index) of the name if part of the set.
        //
        public int findName( String searchName )
        {
                int currentNameIndex;
                for( currentNameIndex=0; currentNameIndex<nbNames; currentNameIndex++ )
                        if( name[currentNameIndex].equals(searchName) ) 
                                return currentNameIndex;
                return -1;
        }     
          
        //
        // Returns a comma separated list of all names determined by
        // the given integer set. All corresponding names will be part
        // of the list.
        //
        public String getNameList( IntegerSet nameIndexes )
        {                     
                String result = "";
                int    i;
                for( i=0; i<nbNames; i++ )
                        if( nameIndexes.contains( i ) )
                                result += (name[i] + ", ");        
                if( result != "" ) result=result.substring( 0, result.length()-2 );
                return result;
        }
          
        //
        // Add a new name to the set. Disregard name if already contained.
        //     
        public int addName( String newName )
        {
                int currentNameIndex;
                currentNameIndex = findName( newName );
                if( currentNameIndex < 0 ) {
                        if( nbNames < MAX_NAMES ) {                                
                                name[nbNames] = new String(newName);
                                if( newName.length() > longestName.length() )
                                        longestName = newName;
                                return nbNames++;
                        }
                }
                return currentNameIndex;
        }        
          
        //
        // Look up for the name by index (ordional).
        //
        public String getName( int Index )
        {
                if( Index < nbNames ) return name[Index];
                return "";
        }
           
        //
        // The program needs to know the longest name to calculate the
        // Object Name column width when displaying the Formal Context.
        //
        public String getLongestName()
        {
                return longestName;
        }  
          
        //
        // Returns an iterator, which allows to iterate thru all
        // set elements.
        //      
        public StringSetIterator getIterator()
        {
                return new StringSetIterator()
                {       
                        //---- Data Elements -----------------
                        private int index = 0;
                        
                        //---- Methods -----------------------
                        public boolean hasMoreNames()
                        {
                                return (index < nbNames);
                        }
                        
                        public String getNextName()
                        {
                                if( index < nbNames ) return name[index++];
                                return "";
                        }
                        
                        public String getCurrentName()
                        {
                                if( index < nbNames ) return name[index];
                                return "";
                        }                                               
                        
                        public int getCurrentNameAsOrdinal()
                        { 
                                if( index < nbNames ) return index;
                                return -1;
                        }
                };
        }      
        
}
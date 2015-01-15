package cn.edu.fudan.se.fca;
//
// 2001-07-29; Holger Zahnleiter 
// StringSet uses this interface when returning an iterator.
// The interface is implemented by the anonymous class returned
// by getIterator().
//


interface StringSetIterator
{      
        //---- Methods -------------------------------------------------------
        public boolean hasMoreNames();
        public String getNextName();
        public String getCurrentName();
        public int getCurrentNameAsOrdinal();
}
        
package comparator.impl;
import java.util.Comparator;

import comparator.INumberAwareStringComparator;

public class NumberAwareStringComparator implements Comparator<String>, INumberAwareStringComparator{
     /* (non-Javadoc)
	 * @see comparator.impl.INumberAwareStringComparator#compare(java.lang.String, java.lang.String)
	 */
    @Override
	public int compare(String s1, String s2) {

            String[] s1Parts = s1.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            String[] s2Parts = s2.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

            int i = 0;
            while(i < s1Parts.length && i < s2Parts.length){

                //if parts are the same
                if(s1Parts[i].compareTo(s2Parts[i]) == 0){
                    ++i;
                }else{
                    try{

                        int intS1 = Integer.parseInt(s1Parts[i]);
                        int intS2 = Integer.parseInt(s2Parts[i]);

                        //if the parse works

                        int diff = intS1 - intS2; 
                        if(diff == 0){
                            ++i;
                        }else{
                            return diff;
                        }
                    }catch(Exception ex){
                        return s1.compareTo(s2);
                    }
                }//end else
            }//end while

            //Handle if one string is a prefix of the other.
            // nothing comes before something.
            if(s1.length() < s2.length()){
                return -1;
            }else if(s1.length() > s2.length()){
                return 1;
            }else{
                return 0;
            }
        }
}
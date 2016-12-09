package com.kcj.animationfriend.util;

import java.text.CollationKey;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Comparator;

import com.kcj.animationfriend.bean.Download;

public class PinyinComparatorDl implements Comparator<Download> {

	private RuleBasedCollator collator;  
	  
    public PinyinComparatorDl() {  
        collator = (RuleBasedCollator) Collator.getInstance(java.util.Locale.CHINA);  
    }  
	
	public int compare(Download o1, Download o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
//		CollationKey c1 = collator.getCollationKey(o1.getFileName());  
//        CollationKey c2 = collator.getCollationKey(o2.getFileName());  
//        return collator.compare(((CollationKey) c1).getSourceString(),  ((CollationKey) c2).getSourceString()); 
	}
}

package hu.zsolt.scamcheck;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

public class TextFormatter {

	public TextFormatter() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String setTextToGREEN(String textToColor){
		
		
			   final SpannableStringBuilder sb = new SpannableStringBuilder(textToColor);
			   
			   final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.GREEN); 			   
			   final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); 
			   final StyleSpan bsu = new StyleSpan(android.graphics.Typeface.ITALIC); 
			   
			   sb.setSpan(fcs, 0, textToColor.length()-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			   sb.setSpan(bss, 0, textToColor.length()-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			   sb.setSpan(bsu, 0, textToColor.length()-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

			   return sb.toString();
			  
			
		
	}
	
	public String setTextToRED(String textToColor){
		
		
		   final SpannableStringBuilder sb = new SpannableStringBuilder(textToColor);
		   
		   final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.GREEN); 			   
		   final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); 
		   final StyleSpan bsu = new StyleSpan(android.graphics.Typeface.ITALIC); 
		   
		   sb.setSpan(fcs, 0, textToColor.length()-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   sb.setSpan(bss, 0, textToColor.length()-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   sb.setSpan(bsu, 0, textToColor.length()-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		   return sb.toString();
		  
		
	
	}

}

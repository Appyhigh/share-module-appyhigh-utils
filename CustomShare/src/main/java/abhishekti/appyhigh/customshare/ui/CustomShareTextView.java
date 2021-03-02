package abhishekti.appyhigh.customshare.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import abhishekti.appyhigh.customshare.R;

/**
 * Created by Abhishek Tiwari on 24-02-2021.
 */
public class    CustomShareTextView extends androidx.appcompat.widget.AppCompatTextView {

    public CustomShareTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomShareTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomShareTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs){
        String fontname = "poppins_medium.ttf";
        if(attrs!=null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomShareTextView);
            fontname = typedArray.getString(R.styleable.CustomShareTextView_typeface);
            typedArray.recycle();
        }

        try{
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontname);
            setTypeface(typeface);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

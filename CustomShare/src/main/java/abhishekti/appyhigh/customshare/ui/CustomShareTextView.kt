package abhishekti.appyhigh.customshare.ui

import abhishekti.appyhigh.customshare.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
class CustomShareTextView: AppCompatTextView {

    constructor(context: Context): super(context){
        initView(null)
    }

    constructor(context: Context, @Nullable attributeSet: AttributeSet): super(context, attributeSet){
        initView(attributeSet)
    }

    constructor(context: Context, @Nullable attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr){
        initView(attributeSet)
    }

    fun initView(attributeSet: AttributeSet?){
        var fontName: String = "poppins_medium.ttf"
        if(attributeSet==null){
            val typedArray: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomShareTextView)
            fontName = typedArray.getString(R.styleable.CustomShareTextView_typeface) ?: "poppins_medium.ttf"
            typedArray.recycle()
        }

        try {
            val typeface: Typeface = Typeface.createFromAsset(context.assets, "fonts/${fontName}")
            setTypeface(typeface)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}
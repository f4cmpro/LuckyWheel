package com.example.luckywheel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat

class PieView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr) {
    private var icon : Int
    private var text : String?
    private var pieBg : Int

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LuckyWheel, 0, 0).apply {
            try {
                icon = getInt(R.styleable.PieView_icon, 0)
                text = getString(R.styleable.PieView_text)
                pieBg = getInt(R.styleable.PieView_icon, R.color.color_9E9E9E)
            } finally {
                recycle()
            }
        }
    }

    private val piePaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.FILL
        color =  ContextCompat.getColor(context, pieBg)
    }

    private val textPaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.FILL_AND_STROKE
        color =  Color.BLACK
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


    }
}

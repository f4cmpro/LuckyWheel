package com.example.luckywheel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import java.util.*

class LuckyWheel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private var pieNumber : Int = 0
    private var recf : RectF = RectF()
    private var random = Random()
    private val colors = arrayListOf(
        R.color.purple_700,
        R.color.teal_200,
        R.color.color_43A047,
    )

    private val circlePaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.FILL_AND_STROKE
        color =  ContextCompat.getColor(context, R.color.color_9E9E9E)
    }

    private val angelPaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.FILL_AND_STROKE
        color =  ContextCompat.getColor(context, R.color.teal_200)
    }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LuckyWheel,
            0, 0).apply {
            try {
                pieNumber = getInt(R.styleable.LuckyWheel_pie_number, 0)
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (widthMeasureSpec > 0 && heightMeasureSpec > 0) {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        } else {
            setMeasuredDimension(MIN_WIDTH, MIN_HEIGHT)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recf = RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawCircle(width / 2f, height / 2f, width / 2f, circlePaint)
            val radius = (360.0/pieNumber).toFloat()
            Log.d("tagggg", "$radius")
            for(i in 0 until  pieNumber){
                angelPaint.apply {
                    color = if(i > colors.size - 1){
                        val count =  i/colors.size
                        ContextCompat.getColor(context, colors[i - colors.size*count])
                    }else{
                        ContextCompat.getColor(context, colors[i])
                    }
                    drawArc(recf, -(45f + i*radius), radius, true, this)
                }
            }
        }
    }

    companion object {
        const val MIN_WIDTH = 1000
        const val MIN_HEIGHT = 1000
    }
}
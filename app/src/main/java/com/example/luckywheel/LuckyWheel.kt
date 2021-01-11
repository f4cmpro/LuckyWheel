package com.example.luckywheel

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import java.util.*
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin


class LuckyWheel @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private var pieNumber : Int = 0
    private var recf : RectF = RectF()
    private var recfBitmap : RectF = RectF()
    private val random = Random()
    private var rotationPos : Float = 0f
    private val colors = arrayListOf(
            R.color.color_03A9F4,
            R.color.color_007AC1,
            R.color.color_67DAFF,
    )

    private val icons = arrayListOf(
            R.drawable.ic_calabash,
            R.drawable.ic_chicken,
            R.drawable.ic_crab,
            R.drawable.ic_deer,
            R.drawable.ic_fish,
            R.drawable.ic_shrimp,
    )

    private var center : Int = 0

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

    private val centerPaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.STROKE
        strokeWidth = Utils.convertDpToPixel(5f, context)
    }

    private val borderWheelPaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.STROKE
        strokeWidth = Utils.convertDpToPixel(5f, context)
        color =  ContextCompat.getColor(context, R.color.color_01579B)

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
        recf = RectF(0f + borderWheelPaint.strokeWidth, 0f + borderWheelPaint.strokeWidth,
            width.toFloat() - borderWheelPaint.strokeWidth, height.toFloat() - borderWheelPaint.strokeWidth)
        center = width/2
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            val sweepAngle = (360.0/pieNumber).toFloat()
            for(i in 0 until  pieNumber){
                val tmpAngel =  -(45f + i*sweepAngle)
                angelPaint.apply {
                    color = if(i > colors.size - 1){
                        val count =  i/colors.size
                        ContextCompat.getColor(context, colors[i - colors.size * count])
                    }else{
                        ContextCompat.getColor(context, colors[i])
                    }
                    drawArc(recf, tmpAngel, sweepAngle, true, this)
                }
                ContextCompat.getDrawable(context, icons[i])?.let { drawable ->
                    drawImage(this, Utils.drawableToBitmap(drawable), tmpAngel)
                }
            }
            drawCenterImage(this)
            drawCircle(width / 2f, height / 2f, (width / 2f) - borderWheelPaint.strokeWidth, borderWheelPaint)
        }

    }

    private fun drawCenterImage(canvas: Canvas) {
        for(i in 0 until  colors.size){
            centerPaint.apply {
                color = ContextCompat.getColor(context, colors[i])
                val radius = width/32f - i*strokeWidth
                canvas.drawCircle(width / 2f, height / 2f, radius, this)
            }
        }
    }

    private fun drawImage(canvas: Canvas, bitmap: Bitmap,  tmpAngle: Float){
        val imgWidth: Int = (width/ 2 / pieNumber)

        val angle = ((tmpAngle + 360f / pieNumber / 2) * Math.PI / 180)

        val x = (center + width / 4 * cos(angle)).toFloat()
        val y = (center + width / 4 * sin(angle)).toFloat()

        val rectF = RectF(x - imgWidth / 2, y - imgWidth / 2,
                x + imgWidth / 2, y + imgWidth / 2)
        canvas.drawBitmap(bitmap, null, rectF, null)
    }


    fun spinLuckyWheel(){
        val nextRotationPos = rotationPos + random.nextInt(1440) + 360f*5
        val animator = ObjectAnimator.ofFloat(this, ROTATION, rotationPos, nextRotationPos)
        rotationPos = nextRotationPos

        val finalAnimator = AnimatorSet()
        finalAnimator.playTogether(animator)
        finalAnimator.interpolator = LinearOutSlowInInterpolator()
        finalAnimator.duration = 5000
        finalAnimator.start()
    }

    companion object {
        const val MIN_WIDTH = 1000
        const val MIN_HEIGHT = 1000
    }
}
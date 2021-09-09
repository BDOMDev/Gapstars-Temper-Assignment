package com.oshan.gapstars_temper_assesment.ui.customView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import com.oshan.gapstars_temper_assesment.R

class PriceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var mRect: Rect = Rect()
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PriceView)

    private val mColor = typedArray.getColor(R.styleable.PriceView_android_color, Color.BLACK)

    val fillPaint = Paint()
    val borderPaint = Paint()
    val borderPath = Path()
    val fillPath = Path()

    private val rect = RectF()
    init {

        fillPath.addRoundRect(rect, 16f, 16f, Path.Direction.CW)

// Add the inner rounded rectangle.
        val innerRect = RectF(mRect)
        innerRect.inset(2f, 3f)
        if (innerRect.width() > 0 && innerRect.height() > 0) {
            borderPath.addRoundRect(innerRect, 16f, 16f, Path.Direction.CW)
        }

// Using the EVEN_ODD fill type will result in a filled space between the two rounded rectangles we created.
        borderPath.setFillType(Path.FillType.EVEN_ODD)

        val vectorPath = Path()
        vectorPath.moveTo(6.5f, 79.99f)
        vectorPath.lineTo(37.21f, 50.5f)
        vectorPath.lineTo(6.5f, 19.79f)
        vectorPath.lineTo(18.79f, 7.5f)
        vectorPath.lineTo(49.5f, 38.21f)
        vectorPath.lineTo(80.21f, 7.5f)
        vectorPath.lineTo(92.5f, 19.79f)
        vectorPath.lineTo(61.79f, 50.5f)
        vectorPath.lineTo(92.5f, 79.99f)
        vectorPath.lineTo(80.21f, 93.5f)
        vectorPath.lineTo(49.5f, 62.79f)
        vectorPath.lineTo(18.79f, 93.5f)
        vectorPath.close()

        val width = 1000
        val height = 1000

        // Calculate a transformation scale between [0, 0, 100, 100] and [0, 0, width, height].
        val scaleX = width / 100.0f
        val scaleY = height / 100.0f

        // Create the transformation matrix.
        val drawMatrix = Matrix()
        drawMatrix.setScale(scaleX, scaleY)

        // Now transform the vector path.
        vectorPath.transform(drawMatrix)

        fillPaint.style = Paint.Style.FILL
        fillPaint.color = mColor
        fillPaint.isAntiAlias = true
        fillPaint.isDither = true

        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = 2f
        borderPaint.color = mColor
        borderPaint.isAntiAlias = true
        borderPaint.isDither = true
    }

    override fun onDraw(canvas: Canvas) {
// First draw the fill path.
        canvas.drawPath(fillPath, fillPaint)
// Then overlap this with the border path.
        canvas.drawPath(borderPath, borderPaint)
    }


}
package com.example.will.mysignature

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MyView : View {
    private var lines: LinkedList<Line>
    private var recycles: LinkedList<Line>
    private var lineWidth: Float = 10f
    private var lineColor: Int = Color.argb(255, 0, 0, 255)
    private var mainActivity: MainActivity

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setBackgroundColor(Color.rgb(255, 250, 153))
        lines = LinkedList()
        recycles = LinkedList()
        mainActivity = context as MainActivity
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()

        for (line in lines) {
            paint.color = line.color
            paint.strokeWidth = line.width

            for (i in 1 until line.points.size) {
                val p0 = line.points[i - 1]
                val p1 = line.points[i]
                canvas?.drawLine(p0.x, p0.y, p1.x, p1.y, paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    addNewLine()
                }
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {
                    addNewPoint(event)
                }
            }
        }

        invalidate()

        return true
    }

    private fun addNewPoint(event: MotionEvent) {
        val point = Point(event.x, event.y)
        lines.last.points.add(point)
    }

    private fun addNewLine() {
        lines.add(Line(lineWidth, lineColor, LinkedList()))
        updateCount()
        recycles.clear()
    }

    fun clear() {
        lines.clear()
        updateCount()
        invalidate()
    }

    fun undo() {
        if (lines.size > 0) {
            recycles.add(lines.removeLast())
            updateCount()
            invalidate()
        }
    }

    fun redo() {
        if (recycles.size > 0) {
            lines.add(recycles.removeLast())
            updateCount()
            invalidate()
        }
    }

    fun updateCount() {
        mainActivity.countView.text = lines.size.toString()
    }

    fun setLineWidth(width: Int) {
        lineWidth = width.toFloat()
        invalidate()
    }

    fun setColor(color: Int) {
        lineColor = color
        invalidate()
    }
}

data class Line(val width: Float, var color: Int, val points: LinkedList<Point>)
data class Point(val x: Float, val y: Float)
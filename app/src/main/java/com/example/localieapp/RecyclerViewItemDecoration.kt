import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecoration(
    context: Context,
    private val horizontalDividerResId: Int,
    private val verticalDividerResId: Int
) : RecyclerView.ItemDecoration() {

    private var horizontalDivider: Drawable = ContextCompat.getDrawable(context, horizontalDividerResId)!!
    private var verticalDivider: Drawable = ContextCompat.getDrawable(context, verticalDividerResId)!!

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val childCount = parent.childCount
        val rowCount = parent.layoutManager?.childCount ?: 0

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            // Draw horizontal divider
            val dividerTop = child.bottom + params.bottomMargin
//            val dividerTop = child.top - params.topMargin - horizontalDivider.intrinsicHeight
            val dividerBottom = dividerTop + horizontalDivider.intrinsicHeight
//            val dividerBottom = child.bottom + params.bottomMargin
            horizontalDivider.setBounds(0, dividerTop, parent.width, dividerBottom)
//            horizontalDivider.draw(c)
            c.save()
            c.clipRect(0, dividerTop, parent.width, dividerBottom)
            horizontalDivider.draw(c)
            c.restore()
        }

        for (i in 0 until rowCount) {
            val rowChild = parent.getChildAt(i)
            val rowParams = rowChild.layoutParams as RecyclerView.LayoutParams

            // Draw vertical divider
            val dividerLeft = rowChild.right + rowParams.rightMargin
            val dividerRight = dividerLeft + verticalDivider.intrinsicWidth
//            verticalDivider.setBounds(dividerLeft, rowChild.top, dividerRight, rowChild.bottom)
//            verticalDivider.draw(c)
            verticalDivider.setBounds(dividerLeft, 0, dividerRight, parent.height)
            c.save()
            c.clipRect(dividerLeft, 0, dividerRight, parent.height)
            verticalDivider.draw(c)
            c.restore()
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // Offset the items to create space for the dividers
        outRect.set(
            verticalDivider.intrinsicWidth,
            horizontalDivider.intrinsicHeight,
            verticalDivider.intrinsicWidth,
            horizontalDivider.intrinsicHeight
        )
    }
}

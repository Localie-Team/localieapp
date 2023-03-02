import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.localieapp.R


class InnerRecyclerViewAdapter(
    context: Context,
    private val child: HashMap<String, List<String>>,
    groupPosition: Int,
    groupname: String
) :
    RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder>() {
    var context: Context
    var groupPosition: Int
    var groupname: String

    init {
        this.context = context
        this.groupPosition = groupPosition
        this.groupname = groupname
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var cardView: CardView

        init {
            name = itemView.findViewById(R.id.deals_text_child)
            cardView = itemView.findViewById(R.id.deals_material_card_view)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_expand_item_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val childText = getChild(groupPosition, position) as String
        holder.name.text = childText
        holder.cardView.setOnClickListener(View.OnClickListener {
            fun onClick(view: View?) {
                Toast.makeText(context, "Clicked on $childText", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return child.size
    }

    fun getChild(groupPosition: Int, childPosititon: Int): Any {

        // This will return the child
        return child[groupname]!![childPosititon]
    }
}
package tech.tucano.madlevel3task2


import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import tech.tucano.madlevel3task2.databinding.ItemPortalBinding


class PortalAdapter(private val portals: List<Portal>, val clickListener: (Portal) -> Unit):
    RecyclerView.Adapter<PortalAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemPortalBinding.bind(itemView)

        fun databind(portal: Portal, clickListener: (Portal) -> Unit){
            binding.tvTitle.text = portal.title
            binding.tvUrl.text = portal.url

            itemView.setOnClickListener{
                clickListener(portal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_portal, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return portals.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(portals[position], clickListener)
    }

}
package br.com.jujuhealth.features.main.attendance.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.jujuhealth.R
import br.com.jujuhealth.extension.setMarkLevel
import br.com.jujuhealth.features.main.attendance.viewholder.UrineLossViewHolder
import kotlinx.android.synthetic.main.item_urine_loss_dialog.view.*

class UrineLossAdapter(
    private val context: Context,
    private val elements: ArrayList<Int>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_urine_loss_dialog, parent, false)
        return UrineLossViewHolder(view)
    }

    override fun getItemCount() = elements?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UrineLossViewHolder) {
            elements?.let{
                when (it[position]) {
                    0 -> {}
                    1 -> {
                        holder.layout.urine_loss_text.setMarkLevel(context.getString(
                            R.string.urine_loss_text_item,
                            context.getString(R.string.urine_loss_low)
                        ))
                    }
                    2 -> {
                        holder.layout.urine_loss_text.setMarkLevel(context.getString(
                            R.string.urine_loss_text_item,
                            context.getString(R.string.urine_loss_medium)
                        ))
                    }
                    3 -> {
                        holder.layout.urine_loss_text.setMarkLevel(context.getString(
                            R.string.urine_loss_text_item,
                            context.getString(R.string.urine_loss_big)
                        ))
                    }
                }
            }
        }
    }

}
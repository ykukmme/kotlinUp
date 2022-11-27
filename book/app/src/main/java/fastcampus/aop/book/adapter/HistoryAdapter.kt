package fastcampus.aop.book.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fastcampus.aop.book.databinding.ItemHistoryBinding
import fastcampus.aop.book.model.History

class HistoryAdapter(val historyDeleteClickedListener: (String) -> Unit) :
    ListAdapter<History, HistoryAdapter.HistoryItemViewHolder>(diffUtil) {

    inner class HistoryItemViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(HistoryModel: History) {
            binding.historyTextView.text = HistoryModel.keyword

            binding.historyDeleteButton.setOnClickListener {
                historyDeleteClickedListener(HistoryModel.keyword.orEmpty())
            }

            binding.root.setOnClickListener {
                HistoryModel.keyword.orEmpty()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        return HistoryItemViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.keyword == newItem.keyword
            }

        }
    }
}
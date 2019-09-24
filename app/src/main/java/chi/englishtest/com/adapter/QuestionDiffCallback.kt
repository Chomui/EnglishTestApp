package chi.englishtest.com.adapter

import androidx.recyclerview.widget.DiffUtil
import chi.englishtest.com.data.db.entity.Question

class QuestionDiffCallback : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem == newItem
    }

}
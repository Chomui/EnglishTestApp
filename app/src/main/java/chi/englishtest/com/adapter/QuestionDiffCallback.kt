package chi.englishtest.com.adapter

import androidx.recyclerview.widget.DiffUtil
import chi.englishtest.com.data.db.QuestionWithAnswers
import chi.englishtest.com.data.db.entity.Question

class QuestionDiffCallback : DiffUtil.ItemCallback<QuestionWithAnswers>() {
    override fun areItemsTheSame(
        oldItem: QuestionWithAnswers,
        newItem: QuestionWithAnswers
    ): Boolean {
        return oldItem.question.id == newItem.question.id
    }

    override fun areContentsTheSame(
        oldItem: QuestionWithAnswers,
        newItem: QuestionWithAnswers
    ): Boolean {
        return oldItem == newItem
    }

}
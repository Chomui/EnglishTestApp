package chi.englishtest.com.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import chi.englishtest.com.R
import chi.englishtest.com.data.db.QuestionWithAnswers
import chi.englishtest.com.data.db.entity.Question
import kotlinx.android.synthetic.main.recycler_question.view.*

class QuestionAdapter(private val onQuestionClickGlobalListener: OnQuestionClickListener) :
    ListAdapter<QuestionWithAnswers, QuestionAdapter.QuestionItemViewHolder>(QuestionDiffCallback()) {

    interface OnQuestionClickListener {
        fun onQuestionClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_question, parent, false)
        return QuestionItemViewHolder(view, onQuestionClickGlobalListener)
    }

    override fun onBindViewHolder(holder: QuestionItemViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bindTo(data)
        }
    }

    class QuestionItemViewHolder(
        itemView: View,
        var onQuestionLocalListener: OnQuestionClickListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var questionText: TextView = itemView.findViewById(R.id.textViewQuestion)
        var questionImage: ImageView = itemView.findViewById(R.id.imageViewQuestion)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onQuestionLocalListener.onQuestionClick(adapterPosition)
        }

        fun bindTo(data: QuestionWithAnswers) {
            questionText.text = data.question.question
            if (data.question.userChoice != -1) {
                questionImage.setImageResource(R.drawable.drawable_checkmark)
            } else {
                questionImage.setImageResource(R.drawable.drawable_x)
            }
        }
    }
}
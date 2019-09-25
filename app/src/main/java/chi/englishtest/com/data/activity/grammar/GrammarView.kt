package chi.englishtest.com.data.activity.grammar

import chi.englishtest.com.data.activity.BaseView
import chi.englishtest.com.data.db.entity.Question

interface GrammarView : BaseView {
    fun openQuestionFragment()
}
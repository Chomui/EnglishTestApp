package chi.englishtest.com.data.activity.grammar

import chi.englishtest.com.data.activity.BasePresenter
import chi.englishtest.com.data.db.entity.Question

interface GrammarPresenter : BasePresenter<GrammarView> {
    fun getTest(id: Int)
}
package chi.englishtest.com.data.activity.grammar

import chi.englishtest.com.data.activity.BasePresenter

interface GrammarPresenter : BasePresenter<GrammarView> {
    fun getTest(id: Int)
}
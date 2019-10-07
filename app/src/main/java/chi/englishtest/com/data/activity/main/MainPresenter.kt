package chi.englishtest.com.data.activity.main

import android.content.Context
import chi.englishtest.com.data.activity.BasePresenter

interface MainPresenter : BasePresenter<MainView> {
    fun testsIsEmpty()
    fun updateDbForGrammarTest(context: Context, testID: Int)
}
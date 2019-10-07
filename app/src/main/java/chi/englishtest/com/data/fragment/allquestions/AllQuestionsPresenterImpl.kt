package chi.englishtest.com.data.fragment.allquestions

import chi.englishtest.com.data.fragment.BasePresenterImpl
import chi.englishtest.com.network.Injection

class AllQuestionsPresenterImpl(private val injection: Injection) :
    BasePresenterImpl<AllQuestionsView>(injection), AllQuestionsPresenter {

}
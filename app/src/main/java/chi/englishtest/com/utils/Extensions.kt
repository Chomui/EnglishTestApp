package chi.englishtest.com.utils

import chi.englishtest.com.data.db.QuestionWithAnswers
import chi.englishtest.com.data.db.entity.Answer
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.db.entity.Test
import chi.englishtest.com.data.sharedPref.SharedManager
import chi.englishtest.com.model.net.login.LogInResponse
import chi.englishtest.com.model.net.question.QuestionResponse
import chi.englishtest.com.model.net.test.TestResponse

fun QuestionResponse.answersToUiModel(): List<Answer> {
    val list: MutableList<Answer> = ArrayList<Answer>()
    val answers = this.answers
    for (i in 0 until this.answers.size) {
        list.add(Answer(answers[i].id, answers[i].questionId, answers[i].answer))
    }
    return list
}

fun LogInResponse.cacheUserData(email: String, pass: String) {
    SharedManager.isUserAuthorized = true
    SharedManager.accessToken = this.authenticationTokens.first().token
    SharedManager.userEmail = email
    SharedManager.userPassword = pass
    SharedManager.userId = this.authenticationTokens.first().userId
    SharedManager.isTeacher = this.isTeacher
}

fun List<TestResponse>.testsToEntityModels(): List<Test> {
    val list: MutableList<Test> = ArrayList<Test>()
    for (test in this) {
        list.add(Test(test.id, test.name, test.description))
    }
    return list
}

fun List<QuestionResponse>.toQuestionWithAnswers(): List<QuestionWithAnswers> {
    val questionWithAnswers = ArrayList<QuestionWithAnswers>()
    for (question in this) {
        questionWithAnswers.add(
            QuestionWithAnswers(
                Question(
                    question.id,
                    question.question,
                    question.testId,
                    -1,
                    0
                ), question.answersToUiModel()
            )
        )
    }
    return questionWithAnswers
}

fun List<QuestionResponse>.questionToEntityModels(): List<Question> {
    val questions = ArrayList<Question>()
    for(question in this) {
        questions.add(
            Question(
                question.id,
                question.question,
                question.testId,
                -1,
                0
            )
        )
    }
    return questions
}
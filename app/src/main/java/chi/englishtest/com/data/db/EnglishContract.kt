package chi.englishtest.com.data.db

import android.provider.BaseColumns

class EnglishContract private constructor() {

    class EnglishTest : BaseColumns {
        companion object {
            const val TABLE_NAME = "test"
            const val NAME = "name"
            const val DESCRIPTION = "description"
        }
    }

    class EnglishQuestion : BaseColumns {
        companion object {
            const val TABLE_NAME = "question"
            const val QUESTION = "question"
            const val TEST_ID = "test_id"
            const val USER_CHOICE = "user_choice"
            const val NOT_SENT = "not_sent"
        }
    }

    class EnglishAnswer : BaseColumns {
        companion object {
            const val TABLE_NAME = "answer"
            const val QUESTION_ID = "question_id"
            const val ANSWER = "answer"
        }
    }
}
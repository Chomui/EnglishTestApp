package chi.englishtest.com.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import chi.englishtest.com.data.db.entity.Answer
import chi.englishtest.com.data.db.entity.Question
import chi.englishtest.com.data.db.entity.Test

@Database(entities = [Test::class, Question::class, Answer::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao
}
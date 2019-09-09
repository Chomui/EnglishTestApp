package chi.englishtest.com.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import chi.englishtest.com.R

abstract class BaseActivity<T: BasePresenter<V>, V: BaseView> : AppCompatActivity(), BaseView {

    lateinit var presenter: T

    abstract fun injectRepository(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = injectRepository()
    }

    override fun updateUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

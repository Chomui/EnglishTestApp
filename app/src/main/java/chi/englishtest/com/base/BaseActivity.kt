package chi.englishtest.com.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import chi.englishtest.com.R

abstract class BaseActivity<T : BasePresenter<V>, V : BaseView> : AppCompatActivity(), BaseView {

    lateinit var presenter: T

    abstract fun provideLayout(): Int
    abstract fun injectRepository(): T
    abstract fun buttonOnClickListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())

        presenter = injectRepository()
        presenter.bindView(this as V)

        buttonOnClickListener()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        presenter.unbindView()
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

package chi.englishtest.com.data.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import chi.englishtest.com.utils.LoadingDialog

abstract class BaseActivity<T : BasePresenter<V>, V : BaseView> : AppCompatActivity(),
    BaseView {

    val presenter: T by lazy { injectRepository() }

    abstract fun provideLayout(): Int
    abstract fun injectRepository(): T
    abstract fun buttonOnClickListener()

    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())

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

    override fun showAlertDialog(message: CharSequence, isNeedNegativeButton: Boolean) {
        buildAlertDialog(message.toString(), isNeedNegativeButton)
    }

    override fun startLoadingDialog() {
        runOnUiThread {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog.start(supportFragmentManager)
            }
        }
    }

    override fun stopLoadingDialog() {
        runOnUiThread {
            if (loadingDialog != null && !isFinishing) {
                loadingDialog!!.dismissAllowingStateLoss()
                loadingDialog = null
            }
        }
    }

    private fun buildAlertDialog(message: String, isNeedNegativeButton: Boolean) {
        val builder = BaseAlertDialog.Builder(
            this,
            android.R.color.holo_purple
        )
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                onAlertDialogPositiveClick(dialog, message)
            }
            .setCancelable(false)

        if (isNeedNegativeButton) {
            builder.setNegativeButton("Cancel") { dialog, _ ->
                onAlertDialogNegativeClick(dialog)
            }
        }
        builder.show()
    }

    override fun onAlertDialogPositiveClick(dialog: DialogInterface, message: String) {
        dialog.dismiss()
    }

    override fun onAlertDialogNegativeClick(dialog: DialogInterface) {
        dialog.dismiss()
    }
}

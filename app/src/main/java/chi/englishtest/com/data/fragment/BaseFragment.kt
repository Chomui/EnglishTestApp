package chi.englishtest.com.data.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import chi.englishtest.com.R
import chi.englishtest.com.data.activity.BaseActivity
import chi.englishtest.com.data.activity.BaseAlertDialog

abstract class BaseFragment<T : BasePresenter<V>, V : BaseView> : Fragment(), BaseView {
    val presenter: T by lazy { injectRepository() }

    abstract fun provideLayout(): Int
    abstract fun injectRepository(): T
    abstract fun buttonOnClickListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.bindView(this as V)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(provideLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonOnClickListener()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun startLoadingDialog() {
        if (activity != null) {
            (activity as BaseActivity<*, *>).startLoadingDialog()
        }
    }

    override fun stopLoadingDialog() {
        if (activity != null) {
            (activity as BaseActivity<*, *>).stopLoadingDialog()
        }
    }

    override fun showAlertDialog(message: CharSequence, isNeedNegativeButton: Boolean) {
        buildAlertDialog(message.toString(), isNeedNegativeButton)
    }


    private fun buildAlertDialog(message: String, isNeedNegativeButton: Boolean) {
        if (activity != null) {
            val builder = BaseAlertDialog.Builder(activity!!, android.R.color.holo_purple)
                .setMessage(message)
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                    onAlertDialogPositiveClick(dialog, message)
                }
                .setCancelable(false)

            if (isNeedNegativeButton) {
                builder.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                    onAlertDialogNegativeClick(dialog)
                }
            }
            builder.show()
        }
    }

    override fun onAlertDialogPositiveClick(dialog: DialogInterface, message: String) {
        dialog.dismiss()
    }

    override fun onAlertDialogNegativeClick(dialog: DialogInterface) {
        dialog.dismiss()
    }
}
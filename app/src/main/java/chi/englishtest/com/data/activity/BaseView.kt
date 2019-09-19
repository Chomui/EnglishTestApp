package chi.englishtest.com.data.activity

import android.content.DialogInterface

interface BaseView {
    fun startLoadingDialog()
    fun stopLoadingDialog()
    fun showAlertDialog(message: CharSequence, isNeedNegativeButton: Boolean = false)
    fun onAlertDialogPositiveClick(dialog: DialogInterface, message: String)
    fun onAlertDialogNegativeClick(dialog: DialogInterface)
}
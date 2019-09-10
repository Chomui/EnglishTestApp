package chi.englishtest.com.base

interface BaseView {
    fun startLoadingDialog()
    fun finishLoadingDialog()
    fun showAlertDialog()
    fun onAlertDialogPositiveClick()
    fun onAlertDialogNegativeClick()
}
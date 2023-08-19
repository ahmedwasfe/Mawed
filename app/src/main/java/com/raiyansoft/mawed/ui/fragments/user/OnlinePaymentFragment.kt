//package com.raiyansoft.misabeeh.ui.fragment.cart
//
//import android.app.Dialog
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.webkit.WebView
//import android.webkit.WebViewClient
//import android.widget.Button
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.OnBackPressedCallback
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.google.android.material.snackbar.Snackbar
//import com.raiyansoft.misabeeh.R
//import com.raiyansoft.misabeeh.databinding.FragmentOnlinePaymentBinding
//import com.raiyansoft.misabeeh.ui.activity.MainActivity
//import com.raiyansoft.misabeeh.ui.fragment.main.HomeFragment
//import com.raiyansoft.misabeeh.ui.fragment.profile.ProfileFragment
//import com.raiyansoft.misabeeh.ui.viewmodel.OnlinePaymentViewModel
//import com.raiyansoft.misabeeh.util.Constant
//import kotlinx.android.synthetic.main.fragment_online_payment.*
//import java.util.*
//
//class OnlinePaymentFragment : Fragment() {
//
//    private lateinit var mBinding: FragmentOnlinePaymentBinding
//    var paymentID = ""
//    private var type = 0    // 1 -> checkout - 2 -> packages - 3 -> tameez
//    private var paymentURL = ""
//    private var appLanguage = "ar"
//
//    private val viewModel by lazy {
//        ViewModelProvider(this)[OnlinePaymentViewModel::class.java]
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val callback: OnBackPressedCallback =
//            object : OnBackPressedCallback(true /* enabled by default */) {
//                override fun handleOnBackPressed() {
//                    showCancelDialog()
//                }
//            }
//
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        mBinding = FragmentOnlinePaymentBinding.inflate(inflater, container, false).apply {
//            executePendingBindings()
//        }
//        return mBinding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        appLanguage = Constant.getSharedPreferences(requireContext()).getString(Constant.LANG, "ar")
//            .toString()
//
//        (requireActivity() as MainActivity).mBinding.bottomBarCard.visibility = View.GONE
//        (requireActivity() as MainActivity).mBinding.addProductBtn.visibility = View.GONE
//
//        setLocale(Locale(appLanguage))
//        ((requireActivity()) as MainActivity).fixLocale()
//
//        closePaymentBtn.setOnClickListener { showCancelDialog() }
//
//        paymentURL = arguments?.getString("paymentURL").toString()
//        paymentID = arguments?.getString("paymentID").toString()
//        val receivedType = arguments?.getInt("type", 0)
//
//        if (receivedType == null)
//            nullType()
//        else {
//            if (receivedType == 0)
//                nullType()
//            else type = receivedType
//        }
//
//        webView.settings.setJavaScriptEnabled(true)
//        webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                Log.v("onlinePaymentStatus70", url.toString())
//                if (url.toString().contains("api/callback/")) {
//                    if (url.toString().contains("api/callback/success")) {
//                        successContinuation()
//                    } else {
//                        failureContinuation()
//                    }
//                }
//                view?.loadUrl(url.toString())
//                return true
//            }
//        }
//        webView.loadUrl(paymentURL)
//    }
//
//    private fun nullType() {
//        failureContinuation()
//        Toast.makeText(
//            requireContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT
//        ).show()
//    }
//
//    fun showCancelDialog() {
//        val cancelOrderDialog = Dialog(requireContext())
//        cancelOrderDialog.setContentView(R.layout.dialog_update_or_no)
//        val btnAccept = cancelOrderDialog.findViewById<Button>(R.id.btnUpdate)
//        val btnCancel = cancelOrderDialog.findViewById<Button>(R.id.btnCancel)
//        val messageTV = cancelOrderDialog.findViewById<TextView>(R.id.textView6)
//
//        btnAccept.text = getString(R.string.yes)
//        btnCancel.text = getString(R.string.no)
//
//        btnAccept.setOnClickListener {
//            failureContinuation()
//            cancelOrderDialog.dismiss()
//        }
//        btnCancel.setOnClickListener {
//            cancelOrderDialog.dismiss()
//        }
//        messageTV.text = getString(R.string.cancel_payment_question)
//        cancelOrderDialog.show()
//    }
//
//    private fun successContinuation() {
//        viewModel.updatePaymentStatus(paymentID, 1, type)
//        viewModel.dataPaymentStatus.observe(viewLifecycleOwner) { response ->
//            if (response.status && response.code == 200) {
//                setLocale(Locale(appLanguage))
//                ((requireActivity()) as MainActivity).fixLocale()
//                Toast.makeText(
//                    requireContext(), getString(R.string.paid_successfully), Toast.LENGTH_SHORT
//                ).show()
//                openAd()
//            } else {
//                Snackbar.make(requireView(), response.message, Snackbar.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun openAd() {
//        requireActivity().supportFragmentManager.beginTransaction()
//            .add(R.id.frame_container, ProfileFragment()).commit()
//    }
//
//    private fun failureContinuation() {
//        viewModel.updatePaymentStatus(paymentID, 0, type)
//        viewModel.dataPaymentStatus.observe(viewLifecycleOwner) { response ->
//            if (response.status && response.code == 200) {
//                Toast.makeText(
//                    requireContext(), getString(R.string.payment_failed), Toast.LENGTH_SHORT
//                ).show()
//                setLocale(Locale(appLanguage))
//                ((requireActivity()) as MainActivity).fixLocale()
//                requireActivity().supportFragmentManager.beginTransaction()
//                    .replace(R.id.frame_container, HomeFragment()).commit()
//            } else {
//                Snackbar.make(requireView(), response.message, Snackbar.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    fun setLocale(locale: Locale) {
//        var mContext = context
//        val resources = mContext?.getResources()
//        val configuration = resources?.getConfiguration()
//        Locale.setDefault(locale)
//        configuration?.setLocale(locale)
//        if (Build.VERSION.SDK_INT >= 25) {
//            mContext = context?.getApplicationContext()?.createConfigurationContext(configuration!!)
//            mContext = context?.createConfigurationContext(configuration!!)
//        }
//
//        mContext?.getResources()?.updateConfiguration(
//            configuration,
//            resources?.getDisplayMetrics()
//        )
//    }
//
//    override fun onStop() {
//        super.onStop()
//        if ((requireActivity() as MainActivity).isMarket)
//            (requireActivity() as MainActivity).mBinding.addProductBtn.visibility = View.VISIBLE
//        (requireActivity() as MainActivity).mBinding.bottomBarCard.visibility = View.VISIBLE
//    }
//}
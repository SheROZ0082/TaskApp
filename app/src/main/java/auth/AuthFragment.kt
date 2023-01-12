package auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.taskapp2.extencions.showTast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import space.lobanovi.taskapp.R
import space.lobanovi.taskapp.databinding.FragmentAuthBinding
import space.lobanovi.taskapp.databinding.FragmentDashboardBinding
import java.util.concurrent.TimeUnit


class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private var auth = FirebaseAuth.getInstance()
    private var correctCode  = ""
    private val callbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }




        override fun onVerificationFailed(p0: FirebaseException) {
            showTast(p0.message.toString())
            Log.e("ololo", "onVerificationFailed: " + p0.message.toString(),)
        }


        override fun onCodeSent(
            verificationCodea: String,
            p1: PhoneAuthProvider.ForceResendingToken

        ) {

            super.onCodeSent(verificationCodea, p1)

            correctCode = verificationCodea

            binding.acceptContainer.isVisible = true
            binding.authContainer.isVisible = false


        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container,false)
        return  binding.root




            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inAuth.countryCode.setOnCountryChangeListener { binding.inAuth.etNumber.setText("+"+binding.inAuth.countryCode.selectedCountryCode) }


        initListeners()

    }

    private fun initListeners() {
        binding.inAuth.btnSend.setOnClickListener {
            if (binding.inAuth.etNumber.text!!.isNotEmpty()) {
                sendPhone()
                //gotoFB

                Toast.makeText(requireContext(), "отправка", Toast.LENGTH_SHORT).show()

            } else {
                binding.inAuth.etNumber.error = "Введите номер телефона"
            }
        }
            binding.inAccept.btnOk.setOnClickListener {
                val credential =
                    PhoneAuthProvider.getCredential(correctCode, binding.inAccept.etCode.text.toString())
                signInWithPhoneAuthCredential(credential)

                findNavController().navigateUp()

            }






            }

    private fun sendPhone() {

        auth.setLanguageCode("Ru")
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(binding.inAuth.etNumber.text.toString())     // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())   .setCallbacks(callbacks)              // Activity (for callback binding)





                    // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

}

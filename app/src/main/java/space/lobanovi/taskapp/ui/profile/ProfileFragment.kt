package space.lobanovi.taskapp.ui.profile

import android.R.attr.previewImage
import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.taskapp2.extencions.loadImage
import space.lobanovi.taskapp.databinding.FragmentProfile2Binding
import space.lobanovi.taskapp.utils.Preferences


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfile2Binding

    var mGetContent: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->

        binding.ivProfile.setImageURI(uri)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfile2Binding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
savName()

    }

    private fun savName() {
        binding.editName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                context?.let { Preferences(it).saveNames(s.toString()) };
                //prefs.saveNames(s.toString());
            }
        })
            binding.editName.setText(context?.let
            { Preferences(it).getName() })

    }

    private fun initListener() {
        binding.ivProfile.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            mGetContent.launch(intent.toString())
        }
    }

    }



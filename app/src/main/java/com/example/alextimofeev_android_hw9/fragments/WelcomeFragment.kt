package com.example.alextimofeev_android_hw9.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.alextimofeev_android_hw9.R
import com.example.alextimofeev_android_hw9.databinding.FragmentWelcomeBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import org.intellij.lang.annotations.Language
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startGame.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_quizFragment)
        }

        binding.birthdayButton.setOnClickListener {
            addBirthday()
        }

        binding.selectLanguage.setOnClickListener {
            val curLocale=resources.configuration.locale
            val choosenLanguage: String
            if(curLocale.language=="en"){
                choosenLanguage="ru"
            }else{
                choosenLanguage="en"
            }
            changeLocale(choosenLanguage)
        }

    }

    //Функция изменения языка приложения
    private fun changeLocale(language: String ){
        val locale = Locale(language)
        Locale.setDefault(locale);
        val config = Configuration();
        config.locale = locale;
        resources.updateConfiguration(config, resources.displayMetrics)
        requireActivity().recreate()
    }



    private fun addBirthday(){
        val constraints=CalendarConstraints.Builder()
            .setOpenAt(calendar.timeInMillis)
            .build()

        val dateFormat = SimpleDateFormat(getString(R.string.format_of_date))

        val dateDialog = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(constraints)
            .setTitleText(getString(R.string.picker_title))
            .build()

        dateDialog.addOnPositiveButtonClickListener {
            calendar.timeInMillis = it
            Snackbar.make(binding.birthdayButton, dateFormat.format(calendar.time), Snackbar.LENGTH_SHORT).show()
        }

        dateDialog.show(parentFragmentManager, "DatePicker")
    }

}
package com.example.alextimofeev_android_hw9.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.navigation.fragment.findNavController
import com.example.alextimofeev_android_hw9.R
import com.example.alextimofeev_android_hw9.databinding.FragmentQuizBinding
import com.example.alextimofeev_android_hw9.quiz.QuizStorage


class QuizFragment : Fragment() {
    // Создаем nullable переменную для binding
    private var _binding: FragmentQuizBinding? = null

    //Геттер для переменной
    private val binding get() = _binding!!


    // Создаем View для фрагмента из файла разметки. Обязательное правило при работе с фрагментами.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentQuizBinding.inflate(inflater,container, false)
        return binding.root
    }


    //Вызывается системой для уничтожения созданной View фрагмента и освобождения памяти
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Заполняем опросник
        fillAllQuestions()

        //Делаем все элементы опросника нивидимыми, кроме кнопки назад
        makeElementsInvisible()

        //Изменение видимости элементов
        fadeQuestions(binding.question1)


        binding.question1.checkChange = {
            if(binding.question2.visibility == View.INVISIBLE)
                fadeQuestions(binding.question2)
        }

        binding.question2.checkChange= {
            if(binding.question3.visibility == View.INVISIBLE)
                fadeQuestions(binding.question3)
        }

        binding.question3.checkChange={
            if(binding.buttonContinue.visibility == View.INVISIBLE)
                moveButton(binding.buttonContinue)
        }


        binding.buttonContinue.setOnClickListener {
            val questionList=QuizStorage.createArray()
            questionList.add(QuizStorage.quest1)
            questionList.add(QuizStorage.quest2)
            questionList.add(QuizStorage.quest3)

            val numberQuestions = questionList.size
            val correctAnswers = countCorrectAnswers()

            val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(correctAnswers,numberQuestions)
            findNavController().navigate(action)
        }

        //Возврат назад
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_welcomeFragment)
        }
    }


    //Функция, обеспечивающая невидимость элементов
    private fun makeElementsInvisible(){
        binding.question1.visibility = View.INVISIBLE
        binding.question2.visibility = View.INVISIBLE
        binding.question3.visibility = View.INVISIBLE
        binding.question1.alpha = 0f
        binding.question2.alpha = 0f
        binding.question3.alpha = 0f
        binding.buttonContinue.visibility = View.INVISIBLE
        binding.buttonContinue.translationY = 100f
    }


    //Функция, изменяющая видимость элементов
    private fun fadeQuestions(view: View){
        view.visibility = View.VISIBLE
        view.animate().apply {
            duration = 800
            alpha(1.0f)
        }.start()
    }
    //Функция, смещающая кнопку Continue
    private fun moveButton(view: View){
        view.visibility = View.VISIBLE
        view.animate().apply {
            duration = 500
            translationY(0f)
            interpolator=AccelerateDecelerateInterpolator()
        }.start()
    }


    //Заполнение всего опросника
    private fun fillAllQuestions(){
        binding.question1.addAllData(QuizStorage.quest1)
        binding.question2.addAllData(QuizStorage.quest2)
        binding.question3.addAllData(QuizStorage.quest3)
    }


    //Функция подсчета правильных ответов
    private fun countCorrectAnswers():Int{
        var count=0
        if (binding.question1.isCorrectAnswer()) count++
        if (binding.question2.isCorrectAnswer()) count++
        if (binding.question3.isCorrectAnswer()) count++
        return count
    }


}
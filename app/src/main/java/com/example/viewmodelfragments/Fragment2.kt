package com.example.viewmodelfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import org.w3c.dom.Text

class Fragment2 : Fragment() {

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_2, container, false)

        val createButton = root.findViewById<Button>(R.id.button_create)
        val code_textEntry = root.findViewById<EditText>(R.id.enter_code)
        val descr_textEntry = root.findViewById<EditText>(R.id.enter_description)
        val term_spinner = root.findViewById<Spinner>(R.id.enter_term)
        val mark_textEntry = root.findViewById<EditText>(R.id.enter_mark)
        val wded_switch = root.findViewById<Switch>(R.id.wded).apply {
            isChecked = mark_textEntry.text.toString() == "WD"
            val listener = mark_textEntry.keyListener
            val prevMark = mark_textEntry.text.toString()
            setOnCheckedChangeListener { _, _ ->
                if (isChecked) {
                    mark_textEntry.keyListener = null
                    mark_textEntry.setText("WD")
                } else {
                    mark_textEntry.keyListener = listener
                    mark_textEntry.setText(prevMark)
                }
            }
        }

        val cancelButton = root.findViewById<Button>(R.id.button_cancel)

        createButton.setOnClickListener {
            val course_entry : MutableList<String>
            if (wded_switch.isChecked.toString() == "true" &&
                !viewModel.courseExists(code_textEntry.text.toString()) &&
                code_textEntry.text.toString() != "" &&
                term_spinner.selectedItem.toString() != "" ) {
                course_entry = mutableListOf(
                    code_textEntry.text.toString(),
                    descr_textEntry.text.toString(),
                    term_spinner.selectedItem.toString(),
                    "WD")
                viewModel.addCourse(course_entry)
                findNavController().navigate(R.id.fragment1)
            } else if (mark_textEntry.text.toString() != "" &&
                (0..100).contains(mark_textEntry.text.toString().toInt()) &&
                !viewModel.courseExists(code_textEntry.text.toString()) &&
                code_textEntry.text.toString() != "" &&
                term_spinner.selectedItem.toString() != "") {
                course_entry = mutableListOf(
                    code_textEntry.text.toString(),
                    descr_textEntry.text.toString(),
                    term_spinner.selectedItem.toString(),
                    mark_textEntry.text.toString()
                    )
                viewModel.addCourse(course_entry)
                findNavController().navigate(R.id.fragment1)
            }
        }

        cancelButton.setOnClickListener {
            findNavController().navigate(R.id.fragment1)
        }
        return root
    }
}
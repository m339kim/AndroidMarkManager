package com.example.viewmodelfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class Fragment3 : Fragment() {

   private val viewModel: MainActivityViewModel by activityViewModels()

   private val terms_by_order =
      listOf("F20", "W21", "S21",
         "F21", "W22", "S22",
         "F22", "W23", "S23",
         "F23", "W24", "S24",
         "F24", "W25")

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      val root = inflater.inflate(R.layout.fragment_3, container, false)

      val origCourse = mutableListOf<String>(
         requireArguments().getString("code").toString(),
         requireArguments().getString("descr").toString(),
         requireArguments().getString("term").toString(),
         requireArguments().getString("mark").toString()
      )

      val edit_fragment_code = root.findViewById<TextView>(R.id.edit_fragment_code)
      edit_fragment_code.text = origCourse[0]

      val edited_descr = root.findViewById<EditText>(R.id.edited_descr)
      edited_descr.setText(origCourse[1])

      val edited_term = root.findViewById<Spinner>(R.id.edited_term)
      edited_term.setSelection(terms_by_order.indexOf(origCourse[2]))

      val edited_mark = root.findViewById<EditText>(R.id.edited_mark)
      edited_mark.setText(origCourse[3])
      val new_wded = root.findViewById<Switch>(R.id.new_wded).apply {
         isChecked = edited_mark.text.toString() == "WD"
         val listener = edited_mark.keyListener
         val prevMark = edited_mark.text.toString()
         setOnCheckedChangeListener { _, _ ->
            if (isChecked) {
               edited_mark.keyListener = null
               edited_mark.setText("WD")
            } else {
               edited_mark.keyListener = listener
               edited_mark.setText(prevMark)
            }
         }
      }

      root.findViewById<Button>(R.id.edit_frag_cancel_button).setOnClickListener {
         findNavController().navigate(R.id.fragment1)
      }

      root.findViewById<Button>(R.id.edit_frag_submit_button).setOnClickListener {
         if (edited_mark.text.toString() != "" &&
            edited_term.selectedItem.toString() != "") {
            if (edited_mark.text.toString() == "WD") {
               viewModel.editCourse(origCourse, mutableListOf(
                  origCourse[0],
                  edited_descr.text.toString(),
                  edited_term.selectedItem.toString(),
                  "WD"
               ))
               findNavController().navigate(R.id.fragment1)
            } else if ((0..100).contains(edited_mark.text.toString().toInt())) {
               viewModel.editCourse(origCourse, mutableListOf(
                  origCourse[0],
                  edited_descr.text.toString(),
                  edited_term.selectedItem.toString(),
                  edited_mark.text.toString()
               ))
               findNavController().navigate(R.id.fragment1)
            }
         }
      }
      return root
   }
}
package com.example.viewmodelfragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.NonDisposableHandle.parent

class Fragment1 : Fragment() {

    private val viewModel: MainActivityViewModel by activityViewModels()

    private fun getColour(mark: String) : Int {
        if (mark == "WD") { return Color.parseColor("#2f4f4f") }
        else {
            val intMark = mark.toInt()
            println(intMark)
            if ((0..49).contains(intMark)) { return Color.parseColor("#f08080") }
            else if ((50..59).contains(intMark)) { return Color.parseColor("#add8e6") }
            else if ((60..90).contains(intMark)) { return Color.parseColor("#90ee90") }
            else if ((91..95).contains(intMark)) { return Color.parseColor("#c0c0c0") }
            else {
                return Color.parseColor("#ffd700")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var root = inflater.inflate(R.layout.fragment_1, container, false)

        val linlay = root.findViewById<LinearLayout>(R.id.linearLayoutScroll)

        viewModel.displayedCourses.observe(viewLifecycleOwner) {
            linlay.removeAllViews()
            for (crs in it) {
                val singleCourse = layoutInflater.inflate(R.layout.single_course, container, false)
                val button_edit = singleCourse.findViewById<ImageButton>(R.id.button_edit).setOnClickListener {
                    findNavController().navigate(R.id.fragment3,
                        Bundle().apply
                     {      putString("code", crs[0])
                            putString("descr", crs[1])
                            putString("term", crs[2])
                            putString("mark", crs[3])})
                }
                val button_delete = singleCourse.findViewById<ImageButton>(R.id.button_delete).setOnClickListener {
                    viewModel.delCourse(crs)
                }
                singleCourse.findViewById<TextView>(R.id.this_code).text = crs[0]
                singleCourse.findViewById<TextView>(R.id.this_descr).text = crs[1]
                singleCourse.findViewById<TextView>(R.id.this_term).text = crs[2]
                singleCourse.findViewById<TextView>(R.id.this_mark).text = crs[3]
                singleCourse.setBackgroundColor(getColour(crs[3]))
                linlay.addView(singleCourse)
            }
        }

        root.findViewById<Spinner>(R.id.sort_spinner).apply {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
                    viewModel.sortCourses(p2)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        root.findViewById<Spinner>(R.id.filter_spinner).apply {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
                    viewModel.filterCourses(p2)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        root.findViewById<FloatingActionButton>(R.id.create_button_float).setOnClickListener {
            findNavController().navigate(R.id.fragment2)
        }

        return root
    }
}
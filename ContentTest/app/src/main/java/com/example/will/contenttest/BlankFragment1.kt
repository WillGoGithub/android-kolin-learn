package com.example.will.contenttest

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class BlankFragment1 : Fragment() {
    private lateinit var txt: TextView
    private lateinit var btn: Button
    private var mainView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_blank_fragment1, container, false)
            txt = (mainView as View).findViewById(R.id.f1Txt)
            btn = (mainView as View).findViewById(R.id.f1Btn)
            btn.setOnClickListener { showLottry() }
        }
        return mainView
    }

    private fun showLottry() {
        var lottery = Math.random() * 49 + 1
        txt.text = lottery.toString()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}

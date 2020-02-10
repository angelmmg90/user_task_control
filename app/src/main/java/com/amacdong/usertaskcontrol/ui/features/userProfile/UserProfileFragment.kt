package com.amacdong.usertaskcontrol.ui.features.userProfile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.amacdong.data.model.UserModel

import com.amacdong.usertaskcontrol.R
import com.amacdong.usertaskcontrol.data.Session
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_profile_items.*
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.android.synthetic.main.item_logout.*

/**
 * A simple [Fragment] subclass.
 */
class UserProfileFragment : Fragment(), UserProfileContract.View{


    companion object {
        const val TAG = "UserProfileFrament"
        fun newInstance(): UserProfileFragment = UserProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        initializeEvents()
    }

    override fun initializeEvents() {
        incCloseSession.setOnClickListener {
            logout()
        }
    }

    override fun initializeViews() {
        Session.getUserObject(context!!)?.let {
            var userWithSession = Gson().fromJson(it, UserModel::class.java)
            tvUserName.text = userWithSession.name
            tvUserDetail.text = userWithSession.role
        }
    }

    override fun logout() {
        Session.logoutFromProfile(context!!)
        val loginScreen = UserProfileFragmentDirections.logoutAction()
        findNavController().navigate(loginScreen)
    }


}

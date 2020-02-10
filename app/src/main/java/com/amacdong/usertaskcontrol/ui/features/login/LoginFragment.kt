package com.amacdong.usertaskcontrol.ui.features.login


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.amacdong.data.model.RoleType
import com.amacdong.usertaskcontrol.R
import com.amacdong.usertaskcontrol.data.Session
import com.github.razir.progressbutton.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), LoginContract.View {


    private val viewModel: LoginViewModel by currentScope.viewModel(this)

    companion object {
        const val TAG = "LoginFragment"
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))

        initializeEvents()
        initializeViews()
    }


    override fun initializeEvents() {
        tvBtnLogin.setOnClickListener {
            showProgressDialog()
            viewModel.signIn(
                edtLoginID.text.toString(),
                edtLoginPassword.text.toString()
            )
        }
    }

    override fun initializeViews() {
        bindProgressButton(tvBtnLogin)

        tvBtnLogin.attachTextChangeAnimator {

            fadeOutMills = 150 // current text fade out time in milliseconds. default 150
            fadeInMills = 150 // current text fade in time in milliseconds. default 150

            useCurrentTextColor = false // by default is true. handling text color based on the current color settings

            textColor = Color.WHITE // override button text color with single color
            textColorRes = R.color.white // override button text color with single color resource
        }
    }

    override fun updateUi(model: LoginViewModel.UiModel) = when (model) {
        is LoginViewModel.UiModel.GoToSystem -> {
            inflateMenu()
            accessToSystem()
        }
        LoginViewModel.UiModel.ErrorSignIn -> {
            errorSignIn()
        }
    }

    override fun inflateMenu(){
        var bottomNavMenu = this.requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavMenu)

        if(Session.getUserRoleSaved(this.requireActivity()) == RoleType.admin.role){
            bottomNavMenu.menu.clear()
            bottomNavMenu.inflateMenu(R.menu.admin_bottom_nav_menu)
        }else{
            bottomNavMenu.menu.clear()
            bottomNavMenu.inflateMenu(R.menu.technical_bottom_nav_menu)
        }
    }

    override fun accessToSystem() {
        val mainScreen = LoginFragmentDirections.accessSystemAction()
        findNavController().navigate(mainScreen)
    }

    override fun errorSignIn() {
        val shake = loadAnimation(this.requireContext(), R.anim.shake_error)
        edtLoginID.startAnimation(shake)
        edtLoginPassword.startAnimation(shake)
        dismissProgressDialog()
    }

    override fun showProgressDialog() {
        tvBtnLogin.showProgress {
            buttonTextRes = R.string.loading_sign_in // text resource to show next to progress

            // progress drawable gravity relative to button text
            // possible values GRAVITY_TEXT_START, GRAVITY_TEXT_END and GRAVITY_CENTER
            gravity = DrawableButton.GRAVITY_TEXT_END  // default value is GRAVITY_TEXT_END

            textMarginPx = 30 //margin between text and progress in pixels. default 10dp

            progressColor = Color.WHITE  // progress color int
            progressColorRes = R.color.white  // progress color resource
            progressColors = intArrayOf(Color.WHITE, Color.BLACK)  // progress colors list
        }
    }

    override fun dismissProgressDialog() {
        tvBtnLogin.hideProgress(R.string.btn_login)
    }


}

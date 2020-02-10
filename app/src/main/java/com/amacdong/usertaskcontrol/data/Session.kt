package com.amacdong.usertaskcontrol.data

import android.content.Context
import com.amacdong.data.model.UserModel
import com.amacdong.usertaskcontrol.common.PreferencesManager

class Session {
    companion object {
        private const val SESSION_SAVED = "pref.session.saved"
        private const val USER_ROLE = "pref.role.user"
        private const val USER_OBJECT = "pref.user.object"

        fun close(ctx: Context) = save(ctx, true)

        fun getSessionSaved(ctx: Context) = PreferencesManager.restoreBoolean(
            ctx,
            SESSION_SAVED
        )

        fun saveSession(ctx: Context, value: Boolean?) = PreferencesManager.save(
            ctx,
            SESSION_SAVED,
            value ?: false
        )

        fun getUserRoleSaved(ctx: Context) = PreferencesManager.restoreString(
            ctx,
            USER_ROLE
        )

        fun saveUserRole(ctx: Context, value: String?) = PreferencesManager.save(
            ctx,
            USER_ROLE,
            if(value.isNullOrEmpty()) "" else value
        )

        fun getUserObject(ctx: Context) = PreferencesManager.restoreString(
            ctx,
            USER_OBJECT
        )

        fun saveUserObject(ctx: Context, value: String?) = PreferencesManager.save(
            ctx,
            USER_OBJECT,
            if(value.isNullOrEmpty()) "" else value
        )

        fun logoutFromProfile(ctx: Context) {
            this.close(ctx)
        }

        private fun save(ctx: Context, forLogOut: Boolean) {
            saveSession(ctx, if (forLogOut) false else getSessionSaved(ctx))
            saveUserRole(ctx, if (forLogOut) "" else getUserRoleSaved(ctx))
            saveUserObject(ctx, if (forLogOut) "" else getUserObject(ctx))
        }
    }
}
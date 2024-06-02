package com.example.financemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.HashMap;

public class SessionManagement {

    private SharedPreferences mSharedPreference;
    //Редактор для общих предпочтений
    private SharedPreferences.Editor mEditor;
    //контекст
    private Context mContext;
    //Общий предварительный режим
    int PRIVATE_MODE;
    //Общее имя префикса
    private static final String PREF_NAME = "SharedPrefTraining";
    //Общие ключи настроек
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWOrD = "password";
    public static final String KEY_ID_USER = "id_user";
    public static final String KEY_USERNAME= "username";
    public SessionManagement(Context mContext) {
        this.mContext = mContext;
        mSharedPreference = this.mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreference.edit();
    }

    public void createLoginSession(String id, String email, String username, String password){ //чтобы сохранить сеанс
// Сохранение значения входа в систему как TRUE
        mEditor.putBoolean(IS_LOGIN, true);
// Хранение электронной почты
        mEditor.putString(KEY_EMAIL, email);
// Сохранение пароля
        mEditor.putString(KEY_PASSWOrD, password);
        mEditor.putString(KEY_ID_USER, id);

// Сохранение пароля
        mEditor.putString(KEY_USERNAME, username);
        mEditor.commit();
    }
    public HashMap<String, String> getUserInformation(){
        HashMap<String, String> user = new HashMap<String, String>();
// электронная почта пользователя
        user.put(KEY_EMAIL, mSharedPreference.getString(KEY_EMAIL, null));
// пароль пользователя
        user.put(KEY_PASSWOrD, mSharedPreference.getString(KEY_PASSWOrD, null));
        user.put(KEY_ID_USER, mSharedPreference.getString(KEY_ID_USER, null));
        user.put(KEY_USERNAME, mSharedPreference.getString(KEY_USERNAME, null));

// вернуть пользователя
        return user;
    }public boolean isLoggedIn(){
        return mSharedPreference.getBoolean(IS_LOGIN, false);
    }
    public void checkIsLogin() {
// Проверьте статус входа в систему
        if (!isLoggedIn()) {
// пользователь не вошел в систему
            Intent i = new Intent(mContext, Login.class);// Закрытие всех мероприятий
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
// Добавьте новый флаг, чтобы начать новое действие
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }
    public void logoutUser(){
// Очистка всех данных из общих настроек
        mEditor.clear();
        mEditor.commit();
// После выхода из системы перенаправьте пользователя на выполнение других действий
        Intent i = new Intent(mContext, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }
    public  void delete(){
        mEditor.clear();
        mEditor.commit();
    }

}


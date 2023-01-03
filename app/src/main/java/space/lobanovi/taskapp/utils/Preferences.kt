package space.lobanovi.taskapp.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Preferences( context: Context) {

    private val sharedPref: SharedPreferences =  context.getSharedPreferences("prefences", MODE_PRIVATE )

    fun isBoardingShowed(): Boolean{
        return sharedPref.getBoolean("board" , false)
    }



    fun setBoardingSwowed(isShow:Boolean){
        sharedPref.edit().putBoolean("board", isShow).apply()

    }
    fun saveNames(toString: String) {
        sharedPref.edit().putString("name", null).apply()
    }

    fun getName(): String? {
        return sharedPref.getString("name", null)
    }

    fun saveImage(url:String){
        sharedPref.edit().putString("name",url).apply()
    }
    fun getImage():String{
        return sharedPref.getString("name", "").toString()
    }
}

package com.example.app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_create_new_user.*

class CreateNewUserActivity : AppCompatActivity() {

    lateinit var viewModel: CreateNewUserViewModel

//В теле функции onCreate базовая логика приложения
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_user)

    //получение id
        val user_id = intent.getStringExtra("user_id")
        initViewModel()
        createUserObservable()

    //получение списка юзеров
        if(user_id != null) {
            loadUserData(user_id)
        }
    //создание пользователя
        createButton.setOnClickListener {
            createUser(user_id)
        }
    //удаление пользователя
        deleteButton.setOnClickListener {
            deleteUser(user_id)
        }
    }
//проверка id при удалении
    private fun deleteUser(user_id: String?) {
        viewModel.getDeleteUserObservable().observe(this, Observer <UserResponse?>{
            if(it == null) {
                Toast.makeText(this@CreateNewUserActivity, "Не удалось удалить пользователя...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@CreateNewUserActivity, "Пользователь успешно удален...", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    //диалоговое окно при удалении
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Удалить пользователя")
        builder.setMessage("При нажатии на кнопку вы удалить пользователя, вы уверены?")
        builder.setNegativeButton("Отмена") { dialogInterface, i ->
        }
        builder.setPositiveButton("Да") { dialogInterface, i ->

            viewModel.deleteUser(user_id)
        }
        builder.show()
    }
    //загрузка профиля
    private fun loadUserData(user_id: String?) {
        viewModel.getLoadUserObservable().observe(this, Observer <UserResponse?>{
            if(it != null) {
                editTextName.setText(it.data?.name)
                editTextEmail.setText(it.data?.email)
                createButton.setText("Обновить")
                deleteButton.visibility =  View.VISIBLE
            }
        })
        viewModel.getUserData(user_id)
    }
    //создание пользователя или обновление
    private fun createUser(user_id: String?){
        val user = User("", editTextName.text.toString(), editTextEmail.text.toString(), "Active", "Male")

        if(user_id == null)
            viewModel.createUser(user)
        //диалоговое окно при обновлении
        else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Обновить данные")
            builder.setMessage("При нажатии на кнопку вы поменяете данные пользователя, вы уверены?")
            builder.setNegativeButton("Отмена") { dialogInterface, i ->
            }
            builder.setPositiveButton("Да") { dialogInterface, i ->

                viewModel.updateUser(user_id, user)
            }
            builder.show()
        }
    }

    // проверка id при создании
    private fun createUserObservable() {
        viewModel.getCreateNewUserObservable().observe(this, Observer <UserResponse?>{
            if(it == null) {
                Toast.makeText(this@CreateNewUserActivity, "Не удалось создать/обновить нового пользователя...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@CreateNewUserActivity, "Пользователь успешно создан/обновлен...", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreateNewUserViewModel::class.java)
    }
}
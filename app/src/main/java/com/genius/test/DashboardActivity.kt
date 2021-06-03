package com.genius.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pexelsapi.retrofit.ApiInterface
import com.example.pexelsapi.retrofit.AppClient
import com.example.pexelsapi.retrofit.response.PhotosItem
import com.example.pexelsapi.retrofit.response.SearchListResponse
import com.genius.test.LoginViewModel.LoginViewModel
import com.genius.test.adapter.ImageAdapter
import com.genius.test.common.InfiniteScrollListener

import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity()  {

    private lateinit var mAuth: FirebaseAuth

    private val appClient = AppClient.getClient(this@DashboardActivity)
    private val apiInterface = appClient.create(ApiInterface::class.java)


    lateinit var imageAdapter: ImageAdapter

    lateinit var layoutManager: LinearLayoutManager

    lateinit var searchedListRV:RecyclerView


    var searchedText: String?= ""


    var photosList: ArrayList<PhotosItem> = ArrayList()

    var currentPage = 1


    lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)



        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser

        var drawer: DrawerLayout = findViewById(R.id.drawer_layout);

        drawer.openDrawer(GravityCompat.START);


        var mNavigationView: NavigationView = findViewById(R.id.nav_view);

        mNavigationView.bringToFront();


        var headerLayout = mNavigationView.inflateHeaderView(R.layout.header);

        var name_txt: TextView = headerLayout.findViewById(R.id.name_txt)

        var email_txt: TextView = headerLayout.findViewById(R.id.email_txt)

        var image: de.hdodenhof.circleimageview.CircleImageView =
            headerLayout.findViewById(R.id.profile_image)

        var sign_out_btn: Button = headerLayout.findViewById(R.id.sign_out_btn)

        name_txt.text = currentUser?.displayName
        email_txt.text = currentUser?.email


        image.load(currentUser?.photoUrl)

        loginViewModel.insertData(this, name_txt.text.toString(), email_txt.text.toString())

        Toast.makeText(this@DashboardActivity, "Data inserted successfull", Toast.LENGTH_LONG).show()



        sign_out_btn.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }


        searchedListRV = findViewById(R.id.searchedListRV)
        layoutManager= GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false)
        searchedListRV.setHasFixedSize(true) // use this setting to improve performance


        searchedText = "ocean"

        setCurrentItem()

        searchedListRV?.clearOnScrollListeners()
        searchedListRV?.addOnScrollListener(InfiniteScrollListener({ setCurrentItem() }, layoutManager))

    }


    private fun setCurrentItem(){
        try {

            val observable = apiInterface.getSearchList(query=searchedText , per_page=80,page=1)
            observable.enqueue(object: Callback<SearchListResponse> {
                override fun onResponse(
                        call: Call<SearchListResponse>,
                        response: Response<SearchListResponse>
                ) {

                    if (response.isSuccessful){
                        if (!response.body()?.photos.isNullOrEmpty()){
                            photosList.clear()
                            response.body()?.photos?.let { photosList.addAll(it) }
                            imageAdapter = ImageAdapter(
                                    this@DashboardActivity,
                                    photosList,  object: ImageAdapter.SetOnClickListener{
                                override fun itemClicked(
                                        position: Int,
                                        photosItem: PhotosItem
                                ) {

                                }

                                    }
                            )

                            searchedListRV.adapter = imageAdapter
                            listChecker()

                        }
                    }
                }

                override fun onFailure(call: Call<SearchListResponse>, t: Throwable) {

                    t.printStackTrace()
                }

            })
        }catch (e:Exception){

            e.printStackTrace()
        }
    }

    fun listChecker(){
        if (photosList.isNullOrEmpty()){
            searchedListRV.visibility = View.GONE
        }else{
            searchedListRV.visibility = View.VISIBLE
        }
    }


    }




